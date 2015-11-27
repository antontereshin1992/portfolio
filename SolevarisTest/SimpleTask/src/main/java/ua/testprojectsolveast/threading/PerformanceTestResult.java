package ua.testprojectsolveast.threading;

/**
 * Stores the result of a performance test.
 * All values are in milliseconds.
 */
public class PerformanceTestResult {
    private final Double TOTAL_TIME;
    private final Double MIN_TIME;
    private final Double MAX_TIME;

    public PerformanceTestResult(Double totalTime, Double minTime, Double maxTime) {
        this.TOTAL_TIME = totalTime;
        this.MIN_TIME = minTime;
        this.MAX_TIME = maxTime;
    }

    /**
     * How long the longest single execution took.
     */
    public Double getMaxTime() {
        return MAX_TIME;
    }

    /**
     * How long the shortest single execution took.
     */
    public Double getMinTime() {
        return MIN_TIME;
    }

    /**
     * How long the whole performance test took in total.
     */
    public Double getTotalTime() {
        return TOTAL_TIME;
    }

    @Override
    public String toString() {
        return "\n" +
                "Total Time = " + getTotalTime() + " msec\n" +
                "Min Time = " + getMinTime() + " msec\n" +
                "Max Time = " + getMaxTime() + " msec\n";
    }
}
