package cn.dream.chapter8.loader;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class WebappClassLoader extends URLClassLoader implements Reloader {
    private String jarPath;
    private boolean delegate;

    /**
     * the list of JARS, in the order they should be searched
     * for locally loaded classes or resources.
     */
    private JarFile[] jarFiles = new JarFile[0];

    /**
     * the list of JARS, in the order they should be searched
     * for locally loaded classes or resources.
     */
    private File[] jarRealFiles = new File[0];

    /**
     * the list of repositories, in the order they should be searched
     * for locally loaded classes or resources.
     */
    private String[] repositories = new String[0];

    /**
     * Repositories translated as path in the work directory (for Jasper
     * originally), but which is used to generate fake URLs should getURLs be
     * called.
     */
    protected File[] files = new File[0];


    /**
     * Associated directory context giving access to the resources in this webapp.
     */
    private DirContext resources = null;


    private Map<String, ResourceEntry> resourceEntries = new HashMap<>();

    public WebappClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public WebappClassLoader(URL[] urls) {
        super(urls);
    }

    public WebappClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public void addRepository(String repository) {
        if (repository.startsWith("/WEB-INF/lib") ||
                repository.startsWith("WEB-INF/classes")) {
            return;
        }

        try {

            URL url = new URL(repository);
            addURL(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public void setDelegate(boolean delegate) {
        this.delegate = delegate;
    }

    public boolean getDelegate() {
        return this.delegate;
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            clazz = findClassInternal(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found internal, Try to find in super");
        } catch (RuntimeException e) {
            throw e;
        }
        if (clazz == null) {
            try {
                super.findClass(name);
                System.out.println("Loaded class " + name + " from super");
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException(name, e);
            }
        }
        return clazz;

    }

    protected Class<?> findClassInternal(String name) throws ClassNotFoundException {
        if (!isValid()) {
            throw new ClassNotFoundException(name);
        }

        String tempPath = name.replace(".", "/");
        String classPath = tempPath + ".class";
        ResourceEntry entry = findResourceInternal(name, classPath);
        if (entry == null || entry.binaryContent == null) {
            throw new ClassNotFoundException(name);
        }
        CodeSource codeSource = new CodeSource(entry.codeBase, entry.certificates);

        Class<?> clazz = entry.loadedClass;
        if (clazz != null) {
            return clazz;
        }

        synchronized (this) {
            if (entry.loadedClass == null) {
                clazz = defineClass(name, entry.binaryContent, 0, entry.binaryContent.length, codeSource);
            } else {
                clazz = entry.loadedClass;
            }
        }
        return clazz;
    }

    private boolean isValid() {

        return true;
    }

    /**
     * Find specified resource in local repositories
     *
     * @param name
     * @param classPath
     * @return
     */
    private ResourceEntry findResourceInternal(String name, String classPath) {
        ResourceEntry entry = resourceEntries.get(name);
        if (entry != null) {
            return entry;
        }

        int contentLength = -1;
        InputStream binaryStream = null;
        int jarFilesLength = jarFiles.length;
        int repositoriesLength = repositories.length;
        Vector<URL> result = new Vector<>();
        //Looking at the repositories
        for (int i = 0; i < repositories.length; i++) {
            try {

                String fullPath = repositories[i] + name;
                //Note: Not getting an exception here means the resource was found
                resources.lookup(fullPath);
                result.add(getURL(new File(files[i], name)));
            } catch (NamingException e) {

            }
        }

        //Looking at the JAR files
        for (int i = 0; i < jarFilesLength; i++) {
            JarEntry jarEntry = jarFiles[i].getJarEntry(name);
            if (jarEntry != null) {
                try {
                    String jarFakeUrl = getURL(jarRealFiles[i]).toString();
                    jarFakeUrl = "jar:" + jarFakeUrl + "!/" + name;
                    entry.source = new URL(jarFakeUrl);
                } catch (MalformedURLException e) {
                    //Ignore
                }
                contentLength = (int)jarEntry.getSize();
                try {
                    entry.manifest = jarFiles[i].getManifest();
                    binaryStream = jarFiles[i].getInputStream(jarEntry);
                } catch (IOException e) {
                    return null;
                }
            }
            if (binaryStream != null) {

                byte[] binaryContent = new byte[contentLength];

                try {
                    int pos = 0;
                    while (true) {
                        int n = binaryStream.read(binaryContent, pos,
                                binaryContent.length - pos);
                        if (n <= 0)
                            break;
                        pos += n;
                    }
                    binaryStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                entry.binaryContent = binaryContent;

                // The certificates are only available after the JarEntry
                // associated input stream has been fully read
                if (jarEntry != null) {
                    entry.certificates = jarEntry.getCertificates();
                }

            }

            // Add the entry in the local resource repository
            synchronized (resourceEntries) {
                // Ensures that all the threads which may be in a race to load
                // a particular class all end up with the same ResourceEntry
                // instance
                ResourceEntry entry2 = (ResourceEntry) resourceEntries.get(name);
                if (entry2 == null) {
                    resourceEntries.put(name, entry);
                } else {
                    entry = entry2;
                }
            }

        }

        return entry;


    }

    private URL getURL(File file) {
        URL url = null;
        try {
            File canonicalFile = file.getCanonicalFile();
            url = canonicalFile.toURI().toURL();
        } catch (IOException e) {
            //Ignore
        }
        return url;
    }
}
