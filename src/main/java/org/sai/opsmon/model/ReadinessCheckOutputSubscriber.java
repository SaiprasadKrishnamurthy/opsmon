package org.sai.opsmon.model;

/**
 * Created by saipkri on 25/11/16.
 */
public interface ReadinessCheckOutputSubscriber {
    void onResult(ReadinessCheckExecutionContext executionContext, ReadinessCheckResult result);
}
