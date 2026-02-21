package QuartzPassData;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDataJob1Run {

	public static void main(String[] args) throws SchedulerException {
		System.out.println("this is job run");
		JobDetail job=JobBuilder.newJob(QuartzDatajob1.class).
				usingJobData("name", "vishwa").build();
		
		Trigger trigger =TriggerBuilder.newTrigger().withIdentity("g1", "g2").startNow().build();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job,trigger);

	}

}
