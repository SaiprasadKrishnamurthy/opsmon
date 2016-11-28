package org.sai.opsmon;

import org.sai.opsmon.model.ReadinessCheckExecutionPhaseType;
import org.sai.opsmon.runner.DefaultReadinessCheckFacade;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by saipkri on 25/11/16.
 */
public class Scratchpad {

    public static void main(String[] args) {
        DefaultReadinessCheckFacade facade = new DefaultReadinessCheckFacade();
        facade.loadConfigsInMemory();

        // No callback.
        Runnable r1 = () -> facade.run(ReadinessCheckExecutionPhaseType.FAILOVER, Optional.empty());

//        Runnable r2 = () -> facade.run(ReadinessCheckExecutionPhaseType.REGISTRATION, Optional.of((context, result) -> System.out.println("I got the result for: " + result)));

        ExecutorService ex = Executors.newFixedThreadPool(10);

        for(int i=0; i<1; i++) {
            ex.submit(r1);
//            ex.submit(r2);
        }
        ex.shutdown();
    }
}
