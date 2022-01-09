package cn.dream.chapter8.loader;

import java.net.URL;
import java.security.cert.Certificate;
import java.util.jar.Manifest;

public class ResourceEntry {

    /**
     * the "last modified" time of the origin file at the time this class
     * was loaded, in milliseconds since the epoch.
     */
    public long lastModified = -1;

    /**
     * Binary content of the resource.
     */
    public byte[] binaryContent = null;

    /**
     * loaded class.
     */
    public Class<?> loadedClass = null;


    /**
     * URL source from where the object was loaded
     */
    public URL source = null;

    /**
     * URL of the codebase from where the object war loaded.
     */
    public URL codeBase = null;

    /**
     * Manifest (if the resource was loaded from a JAR).
     */
    Manifest manifest = null;

    /**
     * Certificate (if the resource was loaded from a JAR).
     */
    Certificate[] certificates = null;


}
