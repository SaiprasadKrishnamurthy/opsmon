package org.sai.opsmon.model;

import lombok.Data;

import java.util.UUID;

/**
 * Created by saipkri on 25/11/16.
 */
@Data
public class ReadinessCheckExecutionContext {
    private final String id = UUID.randomUUID().toString();
    private int totalChecks;
    private ResultOutputChannelType[] outputChannels;
}
