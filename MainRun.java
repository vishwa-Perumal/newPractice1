package QuartzTest1;

import org.quartz.Trigger;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MainRun {

	public static void main(String[] args) throws SchedulerException {
		
		JobDetail job=JobBuilder.newJob(QuartzJob1.class).withIdentity("a1" ,"a2").build();
		
		Trigger trigger =TriggerBuilder.newTrigger().withIdentity("b1", "b2").startNow().
				withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5).repeatForever()
				).build();
		
		    Scheduler scheduler =new StdSchedulerFactory().getScheduler();

		    scheduler.start();
		    scheduler.scheduleJob(job,trigger);

	}

}
