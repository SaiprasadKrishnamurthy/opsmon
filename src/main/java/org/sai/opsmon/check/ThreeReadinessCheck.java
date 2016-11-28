package org.sai.opsmon.check;

import org.sai.opsmon.model.BaseReadinessCheck;
import org.sai.opsmon.model.ReadinessCheckResult;
import org.sai.opsmon.model.ReadinessCheckExecutionContext;

/**
 * Created by saipkri on 25/11/16.
 */
public class ThreeReadinessCheck extends BaseReadinessCheck {

    @Override
    public ReadinessCheckResult performCheck(final ReadinessCheckExecutionContext executionContext) {
        sleep(4);
        return new ReadinessCheckResult();
    }

    @Override
    public String id() {
        return "test_three";
    }
}
