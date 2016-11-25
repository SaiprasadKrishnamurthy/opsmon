package org.sai.opsmon.model;

import lombok.Data;

import java.util.UUID;

/**
 * Created by saipkri on 16/11/16.
 */
@Data
public class ReadinessCheckResult {
    private String id = UUID.randomUUID().toString();
    private String checkId;
    private long startedTimestamp;
    private long completedTimestamp;
    private long executionTimeInSeconds;
    private boolean success;
    private String message = "";
    private String remarks = "";
    private ReadinessCheckExecutionPhaseType[] impactedPhases;
    private ResultOutputChannelType[] outputChannelTypes;
    private ReadinessCheckFailureSeverityType haCheckFailureSeverityType;
}
