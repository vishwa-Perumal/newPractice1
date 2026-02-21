package QuartzTrans1;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PendingTransactionJob implements Job {

    private static final String FETCH_PENDING_SQL =
            "SELECT id, amount FROM transactions WHERE status = 'PENDING'";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("---- Quartz Job Started ----");

        try (Connection conn = DataBaseTranscation1.getConnection();
             PreparedStatement ps = conn.prepareStatement(FETCH_PENDING_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long txId = rs.getLong("id");
                double amount = rs.getDouble("amount");

                // Idempotent READ — no update
                System.out.println(
                        "Processing TX_ID=" + txId + " | Amount=" + amount);
            }

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }

        System.out.println("---- Quartz Job Finished ----");
    }
}

