package org.sai.opsmon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by saipkri on 25/11/16.
 */
@Data
@EqualsAndHashCode(of = "id")
public class ReadinessCheckConfig {
    private String id;
    private String fullyQualifiedClassName;
    private String description;
    private ReadinessCheckExecutionPhaseType[] executionPhases;
    private boolean enabled;
    private ResultOutputChannelType[] outputChannels;
    private ReadinessCheckFailureSeverityType failureSeverity;
}
