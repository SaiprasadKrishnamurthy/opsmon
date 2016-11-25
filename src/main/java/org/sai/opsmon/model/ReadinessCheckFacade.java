package org.sai.opsmon.model;

import java.util.Optional;

/**
 * Created by saipkri on 25/11/16.
 */
public interface ReadinessCheckFacade {
    void loadConfigsInMemory();

    void run(ReadinessCheckExecutionPhaseType phaseType, Optional<ReadinessCheckOutputSubscriber> subscriber);

    void run();
}
