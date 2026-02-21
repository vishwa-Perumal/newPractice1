package OneSchedulerManyJobs;

import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJobB implements  Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	
		String jobb=context.getJobDetail().getJobDataMap().getString("nameB");
		System.out.println("this is" + jobb+" "  + LocalDateTime.now());
		
	}

}
