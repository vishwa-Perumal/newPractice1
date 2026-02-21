package QuartzTrans1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzFintechRunner {

    public static void main(String[] args) throws Exception {

        JobDetail jobDetail = JobBuilder
                .newJob(PendingTransactionJob.class)
                .withIdentity("pendingTxJob", "fintech")
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("pendingTxTrigger", "fintech")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                                .repeatForever()
                )
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
