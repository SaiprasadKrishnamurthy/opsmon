package org.sai.opsmon.model;

import java.util.List;

/**
 * Created by saipkri on 25/11/16.
 */
public interface ReadinessCheck {
    void check(ReadinessCheckConfig config, ReadinessCheckExecutionContext executionContext);

    String id();

    void registerOutputListeners(List<ReadinessCheckOutputSubscriber> outputListeners);
}
