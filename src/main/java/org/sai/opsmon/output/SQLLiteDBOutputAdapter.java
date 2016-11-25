package org.sai.opsmon.output;

import org.sai.opsmon.model.ReadinessCheckResult;
import org.sai.opsmon.model.OutputAdapter;
import org.sai.opsmon.model.ReadinessCheckExecutionContext;
import org.sai.opsmon.model.ResultOutputChannelType;

import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by saipkri on 25/11/16.
 */
public class SQLLiteDBOutputAdapter implements OutputAdapter {

    private final Lock lock = new ReentrantLock();


    @Override
    public boolean canAccept(final ReadinessCheckResult result) {
        return Arrays.asList(result.getOutputChannelTypes()).contains(ResultOutputChannelType.DATABASE);
    }

    @Override
    public void write(final ReadinessCheckExecutionContext executionContext, final ReadinessCheckResult result) {
        // TODO USE SPRING TO GET RID OF ALL THESE BOILERPLATE PLUMBING (COWBOY) CODE!
        if (canAccept(result)) {
            Connection c = null;
            Statement stmt = null;
            PreparedStatement pstmt = null;
            try {
                lock.lock();
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:readinessChecks.db");
                stmt = c.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS READINESS_CHECK_RESULTS " +
                        "(id TEXT PRIMARY KEY  NOT NULL," +
                        " checkId           TEXT    NOT NULL, " +
                        " message           TEXT    NOT NULL, " +
                        " remarks           TEXT    NOT NULL, " +
                        " impactedPhases           TEXT    NOT NULL, " +
                        " haCheckFailureSeverity           TEXT    NOT NULL, " +
                        " startedTimestamp            long     NOT NULL, " +
                        " completedTimestamp            long     NOT NULL, " +
                        " executionTimeInSeconds            long     NOT NULL, " +
                        " success        INT)";
                stmt.executeUpdate(sql);

                String sqlInsert = "INSERT INTO READINESS_CHECK_RESULTS(id, checkId,message,remarks,impactedPhases,haCheckFailureSeverity,startedTimestamp,completedTimestamp,executionTimeInSeconds,success) VALUES (?,?,?,?,?,?,?,?,?,?)";
                pstmt = c.prepareStatement(sqlInsert);
                pstmt.setString(1, result.getId());
                pstmt.setString(2, result.getCheckId());
                pstmt.setString(3, result.getMessage());
                pstmt.setString(4, result.getRemarks());
                pstmt.setString(5, Arrays.deepToString(result.getImpactedPhases()));
                pstmt.setString(6, result.getHaCheckFailureSeverityType().toString());
                pstmt.setLong(7, result.getStartedTimestamp());
                pstmt.setLong(8, result.getCompletedTimestamp());
                pstmt.setLong(9, result.getExecutionTimeInSeconds());
                pstmt.setBoolean(10, result.isSuccess());

                pstmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            } finally {
                try {
                    stmt.close();
                    pstmt.close();
                    c.close();
                    lock.unlock();
                } catch (SQLException ignored) {
                }
            }
            System.out.println("Opened database successfully");
        }
    }
}
