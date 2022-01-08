package cn.dream.chapter7.life;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LifeCycleSupport {

    private final LifeCycle lifeCycle;
    private final List<LifeCycleListener> listeners = new ArrayList<>(0);

    public LifeCycleSupport(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public void addLifeCycleListener(LifeCycleListener listener) {
        listeners.add(listener);
    }

    public void removeLifeCycleListener(LifeCycleListener listener) {
        listeners.remove(listener);
    }

    public LifeCycleListener[] findLifeCycleListener() {
        LifeCycleListener[] interested = new LifeCycleListener[listeners.size()];
        return listeners.toArray(interested);
    }

    public void fireLifeCycleEvent(String type, Object data) {
        LifeCycleEvent event = new LifeCycleEvent(lifeCycle, type, data);
        List<LifeCycleListener> interested = new ArrayList<>(listeners.size());
        synchronized (listeners) {
            interested.addAll(listeners);
        }
        for (LifeCycleListener listener : interested) {
            listener.lifeCycleEvent(event);
        }
    }

}
