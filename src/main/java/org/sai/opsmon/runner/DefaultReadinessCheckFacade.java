package org.sai.opsmon.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sai.opsmon.model.*;
import org.sai.opsmon.subscriber.DefaultReadinessCheckOutputSubscriber;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Created by saipkri on 25/11/16.
 */
public class DefaultReadinessCheckFacade implements ReadinessCheckFacade {

    private final CopyOnWriteArraySet<ReadinessCheckConfig> configs = new CopyOnWriteArraySet<>();
    private static final ObjectMapper JSON_PARSER = new ObjectMapper();

    // Perhaps manage it via Spring DI.
    private final ReadinessCheckOutputSubscriber readinessCheckOutputSubscriber = new DefaultReadinessCheckOutputSubscriber();

    @Override
    public void loadConfigsInMemory() {
        try {
            // LOADING FROM FILE WOULD BE FINE FOR NOW.
            List readConfigs = JSON_PARSER.readValue(Paths.get("config.json").toFile(), List.class);
            readConfigs.forEach(_config -> configs.add(JSON_PARSER.convertValue(_config, ReadinessCheckConfig.class)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run(final ReadinessCheckExecutionPhaseType phaseType, final Optional<ReadinessCheckOutputSubscriber> subscriber) {
        // Fresh ExecutionContext per request.
        ReadinessCheckExecutionContext executionContext = new ReadinessCheckExecutionContext();
        List<ReadinessCheckConfig> applicableTests = applicableTests(phaseType);
        executionContext.setTotalChecks(applicableTests.size());

        applicableTests.parallelStream()
                .forEach(applicableTest -> {
                    try {
                        ReadinessCheck readinessCheck = (ReadinessCheck) Class.forName(applicableTest.getFullyQualifiedClassName()).newInstance();
                        readinessCheck.registerOutputListeners(subscriber.isPresent() ? Arrays.asList(readinessCheckOutputSubscriber, subscriber.get()) : Arrays.asList(readinessCheckOutputSubscriber));
                        readinessCheck.check(applicableTest, executionContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private List<ReadinessCheckConfig> applicableTests(final ReadinessCheckExecutionPhaseType phaseType) {
        return configs.stream()
                .filter(haReadinessCheckConfig -> Arrays.asList(haReadinessCheckConfig.getExecutionPhases()).contains(phaseType) || phaseType == ReadinessCheckExecutionPhaseType.ALL)
                .filter(ReadinessCheckConfig::isEnabled)
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        run(ReadinessCheckExecutionPhaseType.ALL, Optional.empty());
    }
}
