package org.sai.opsmon.subscriber;

import org.sai.opsmon.model.ReadinessCheckResult;
import org.sai.opsmon.model.OutputAdapter;
import org.sai.opsmon.model.ReadinessCheckExecutionContext;
import org.sai.opsmon.model.ReadinessCheckOutputSubscriber;
import org.sai.opsmon.output.SQLLiteDBOutputAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by saipkri on 25/11/16.
 */
public class DefaultReadinessCheckOutputSubscriber implements ReadinessCheckOutputSubscriber {

    // TODO Manage via Spring DI.
    private List<OutputAdapter> outputAdapters = Arrays.asList(new SQLLiteDBOutputAdapter());

    @Override
    public void onResult(final ReadinessCheckExecutionContext executionContext, final ReadinessCheckResult result) {
        System.out.println("Yay! --> " + executionContext + " ---> " + result);
        // Call OutputAdapter to branch calls send the output to the respective channels.
        // Send to the appropriate channel.
        outputAdapters.forEach(adapter -> adapter.write(executionContext, result));
    }
}
