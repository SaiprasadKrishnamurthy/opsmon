package org.sai.opsmon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saipkri on 25/11/16.
 */
public abstract class BaseReadinessCheck implements ReadinessCheck {

    private List<ReadinessCheckOutputSubscriber> outputSubscribers = new ArrayList<>();

    public abstract ReadinessCheckResult performCheck(ReadinessCheckExecutionContext executionContext);

    @Override
    public void check(final ReadinessCheckConfig config, final ReadinessCheckExecutionContext executionContext) {
        long start = System.currentTimeMillis();
        ReadinessCheckResult result = performCheck(executionContext);
        long end = System.currentTimeMillis();
        result.setCheckId(executionContext.getId());
        result.setStartedTimestamp(start);
        result.setCompletedTimestamp(end);
        result.setExecutionTimeInSeconds((end - start)/1000);
        result.setOutputChannelTypes(config.getOutputChannels());
        result.setHaCheckFailureSeverityType(config.getFailureSeverity());
        outputSubscribers.forEach(subscriber -> subscriber.onResult(executionContext, result));
    }

    @Override
    public void registerOutputListeners(final List<ReadinessCheckOutputSubscriber> outputListeners) {
        outputSubscribers.addAll(outputListeners);
    }

    protected void sleep(final int seconds) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
