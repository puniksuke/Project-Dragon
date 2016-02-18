package org.osrspvp.util;

public class StopWatch {
    private long time = System.currentTimeMillis();

    public StopWatch headStart(long startAt) {
        time = System.currentTimeMillis() - startAt;
        return this;
    }

    public StopWatch reset(long i) {
        time = i;
        return this;
    }

    public StopWatch reset() {
        time = System.currentTimeMillis();
        return this;
    }

    public long elapsed() {
        return System.currentTimeMillis() - time;
    }

    public boolean elapsed(long time) {
        return elapsed() >= time;
    }

    public long getTime() {
        return time;
    }

    public StopWatch() {
        time = 0;
    }
}
