package com.perfect.lock.annotation;

public class PerfectLockParam {
    private String name;
    private long maxWait;
    private long maxHold;

    public PerfectLockParam(String name, long maxWait, long maxHold) {
        this.name = name;
        this.maxWait = maxWait;
        this.maxHold = maxHold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getMaxHold() {
        return maxHold;
    }

    public void setMaxHold(long maxHold) {
        this.maxHold = maxHold;
    }

    @Override
    public String toString() {
        return "PerfectLockParam{" +
                "name='" + name + '\'' +
                ", maxWait=" + maxWait +
                ", maxHold=" + maxHold +
                '}';
    }
}
