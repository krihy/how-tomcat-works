package cn.dream.chapter8.life;


public interface LifeCycleListener {

    /**
     * this method is invoked when an event the listener is
     * interested is fired
     *
     * @param event the event source
     */
    void lifeCycleEvent(LifeCycleEvent event);
}
