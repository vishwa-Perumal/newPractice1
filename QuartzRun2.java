package QuartzTest1;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzRun2 {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		JobDetail job = JobBuilder.newJob(Quartz2.class).build();
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("k1", "v1").
				startAt(DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND)).
				withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job,trigger);
	

	}

}
