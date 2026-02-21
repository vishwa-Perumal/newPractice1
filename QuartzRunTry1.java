package QuartzRetry1;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzRunTry1 {

	public static void main(String[] args) throws SchedulerException {
		
		JobDetail job =JobBuilder.newJob(QuartzRetry1.QuartzRetyr1Job1.class).withIdentity("key1","val1").build();
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("key2","val2").startNow().
				withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).
						withRepeatCount(2)).build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		
		
	}

}
