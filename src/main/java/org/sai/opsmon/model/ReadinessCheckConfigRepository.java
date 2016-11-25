package org.sai.opsmon.model;

/**
 * Created by saipkri on 25/11/16.
 */
public interface ReadinessCheckConfigRepository {
    void create(ReadinessCheckConfig config);

    ReadinessCheckConfig read(String id);

    void update(ReadinessCheckConfig config);

    void delete(String id);
}
