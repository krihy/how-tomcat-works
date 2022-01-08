package cn.dream.chapter8.life;

public interface LifeCycle {

    String START_EVENT = "start";
    String BEFORE_START_EVENT = "before_tart";
    String AFTER_START_EVENT = "after_start";
    String STOP_EVENT = "stop";
    String BEFORE_STOP_EVENT = "before_stop";
    String AFTER_STOP_EVENT = "after_stop";

    void start() throws LifeCycleException;

    void stop() throws LifeCycleException;

    void addLifeCycleListener(LifeCycleListener listener);

    void removeLifeCycleListener(LifeCycleListener listener);

    LifeCycleListener[] findLifeCycleListeners();
}
