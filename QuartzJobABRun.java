package OneSchedulerManyJobs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzJobABRun {

	public static void main(String[] args) throws SchedulerException {
		
		JobDetail jobA =JobBuilder.newJob(QuartzJobA.class).usingJobData("nameA","JobA").build();
		
		Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("k","k1").startNow().
				withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(5)).build();
		
	    JobDetail jobB =JobBuilder.newJob(QuartzJobB.class).usingJobData("nameB","JobB").build();
		
		Trigger triggerB = TriggerBuilder.newTrigger().withIdentity("k2","k3").startNow().
				withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(5)).build();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
	    scheduler.scheduleJob(jobA, triggerA);
        scheduler.scheduleJob(jobB, triggerB);

        scheduler.start();
				
	
	}

}
