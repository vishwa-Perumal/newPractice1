package OneSchedulerManyJobs;

import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJobA implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String joba=context.getJobDetail().getJobDataMap().getString("nameA");
	
		System.out.println("this is" + joba+" "  + LocalDateTime.now());
	}

}
