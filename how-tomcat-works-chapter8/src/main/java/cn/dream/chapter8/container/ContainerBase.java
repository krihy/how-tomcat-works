package cn.dream.chapter8.container;

import cn.dream.chapter8.connect.parameter.Request;
import cn.dream.chapter8.connect.parameter.Response;
import cn.dream.chapter8.loader.Loader;

import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public abstract class ContainerBase extends LifeCycleBase implements Container, Pipeline {

    private Loader loader;
    protected Mapper mapper;
    protected final Map<String, Mapper> mappers = new HashMap<>();
    protected final Map<String, Container> children = new HashMap<>();

    protected Pipeline pipeline = new SimplePipeline(this);
    protected String name;
    protected Container parent;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {
        this.parent = container;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {

    }

    @Override
    public void addChild(Container child) {

        if (child.getName() == null) {
            throw new IllegalArgumentException("add Child container name is empty");
        }
        synchronized (children) {
            children.putIfAbsent(child.getName(), child);
        }
        //set parent container
        child.setParent((Container) this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return children.get(name);
    }

    @Override
    public Container[] findChildren() {
        Container[] containers = new Container[children.size()];
        return children.values().toArray(containers);
    }

    @Override
    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    @Override
    public boolean removeValve(Valve valve) {
        return pipeline.removeValve(valve);
    }

    @Override
    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    @Override
    public void removeChild(Container child) {
        children.remove(child.getName());
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container map(Request request, boolean update) {

        Mapper mapper = findMapper(request.getRequest().getProtocol());
        if (mapper == null) {
            return (null);
        }
        return mapper.map(request, update);
    }

    @Override
    public void addMapper(Mapper mapper) {
        if (mappers.get(mapper.getProtocol()) != null) {
            throw new IllegalArgumentException("addMapper: Protocol" +
                    mapper.getProtocol() + " is not unique");
        }
        mapper.setContainer((Container) this);
        synchronized (mappers) {
            mappers.put(mapper.getProtocol(), mapper);
        }
        if (mappers.size() > 1) {
            this.mapper = mapper;
        } else {
            this.mapper = null;
        }
    }

    @Override
    public void invoke(Request request, Response response) throws ServletException {
        pipeline.invoke(request, response);
    }

    @Override
    public Loader getLoader() {
        return loader;
    }

    @Override
    public void setLoader(Loader loader) {

        this.loader = loader;
    }

    @Override
    protected void startInternal() {
       // System.out.println("container " + getName() + " " + LifeCycle.START_EVENT);
        //start all mappers
        for (Mapper mapper : mappers.values()) {
            if (mapper instanceof LifeCycle) {
                ((LifeCycle) mapper).start();
            }
        }

        //start all children contains
        for (Container container : children.values()) {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).start();

            }
        }
        //start pipeline
        if (pipeline instanceof LifeCycle) {
            ((LifeCycle) pipeline).start();
        }

        if (loader != null && (loader instanceof LifeCycle)) {
            ((LifeCycle) loader).start();
        }

    }

    @Override
    protected void stopInternal() {
        //stop all mappers
        for (Mapper mapper : mappers.values()) {
            if (mapper instanceof LifeCycle) {
                ((LifeCycle) mapper).stop();
            }
        }

        //stop all children contains
        for (Container container : children.values()) {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
        //stop pipeline
        if (pipeline instanceof LifeCycle) {
            ((LifeCycle) pipeline).stop();
        }


        if (loader != null && (loader instanceof LifeCycle)) {
            ((LifeCycle) loader).stop();
        }
    }

    protected void addDefaultMapper(String mapperClass) {
        if (mapperClass == null) {
            return;
        }
        if (mappers.size() > 1) {
            return;
        }

        try {
            Class<?> clazz = Class.forName(mapperClass);
            Mapper mapper = (Mapper) clazz.newInstance();

            mapper.setProtocol("HTTP/1.1");
            addMapper(mapper);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Mapper findMapper(String protocol) {
        if (mapper != null) {
            return mapper;
        }
        synchronized (mappers) {
            return mappers.get(protocol);
        }
    }
}
