package cn.dream.chapter7.life;


public interface LifeCycleListener {

    /**
     * this method is invoked when an event the listener is
     * interested is fired
     *
     * @param event the event source
     */
    void lifeCycleEvent(LifeCycleEvent event);
}
