package cn.dream.chapter7.life;

public abstract class LifeCycleBase implements LifeCycle {

    protected LifeCycleSupport lifeCycleSupport = new LifeCycleSupport(this);
    private boolean started;

    @Override
    public void start() throws LifeCycleException {
        if (started) {
            throw new LifeCycleException("this component has been started");
        }
        System.out.println(getClass().getName() + " " + LifeCycle.BEFORE_START_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.BEFORE_START_EVENT, (Object) null);

        System.out.println(getClass().getName() + " " + LifeCycle.START_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.START_EVENT, (Object) null);
        this.started = true;
        startInternal();

        System.out.println(getClass().getName() + " " + LifeCycle.AFTER_START_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.AFTER_START_EVENT, (Object) null);
    }

    @Override
    public void stop() throws LifeCycleException {
        if (!started) {
            throw new LifeCycleException("this component hasn't started ");
        }
        System.out.println(getClass().getName() + " " + LifeCycle.BEFORE_STOP_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.BEFORE_STOP_EVENT, (Object) null);

        System.out.println(getClass().getName() + " " + LifeCycle.STOP_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.STOP_EVENT, (Object) null);
        this.started = false;
        stopInternal();

        System.out.println(getClass().getName() + " " + LifeCycle.AFTER_STOP_EVENT);
        lifeCycleSupport.fireLifeCycleEvent(LifeCycle.AFTER_STOP_EVENT, (Object) null);
    }

    @Override
    public void addLifeCycleListener(LifeCycleListener listener) {
        lifeCycleSupport.addLifeCycleListener(listener);
    }

    @Override
    public void removeLifeCycleListener(LifeCycleListener listener) {
        lifeCycleSupport.addLifeCycleListener(listener);
    }

    @Override
    public LifeCycleListener[] findLifeCycleListeners() {
        return lifeCycleSupport.findLifeCycleListener();
    }

    protected abstract void startInternal();

    protected abstract void stopInternal();
}
