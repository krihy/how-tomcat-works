package cn.dream.chapter4;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存 RequestString 和 RequestBody 中参数
 */
public class ParameterMap extends HashMap {
    private boolean locked;

    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap() {
    }

    public ParameterMap(Map m) {
        super(m);
    }

    /**
     * 加锁状态不能修改内部参数
     */

    @Override
    public Object put(Object key, Object value) {
        isLockedVerify();
        return super.put(key, value);
    }

    @Override
    public void putAll(Map m) {
        isLockedVerify();
        super.putAll(m);
    }

    @Override
    public Object remove(Object key) {
        isLockedVerify();
        return super.remove(key);
    }

    @Override
    public void clear() {
        isLockedVerify();
        super.clear();
    }

    @Override
    public boolean remove(Object key, Object value) {
        isLockedVerify();
        return super.remove(key, value);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    private void isLockedVerify() {
        if (locked) {
            throw new RuntimeException("Parameters is locked ");
        }

    }


}
