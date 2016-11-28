package org.sai.opsmon.check;

import org.sai.opsmon.model.BaseReadinessCheck;
import org.sai.opsmon.model.ReadinessCheckExecutionContext;
import org.sai.opsmon.model.ReadinessCheckResult;

/**
 * Created by saipkri on 25/11/16.
 */
public class FourReadinessCheck extends BaseReadinessCheck {

    @Override
    public ReadinessCheckResult performCheck(final ReadinessCheckExecutionContext executionContext) {
        sleep(5);
        return new ReadinessCheckResult();
    }

    @Override
    public String id() {
        return "test_four";
    }
}
