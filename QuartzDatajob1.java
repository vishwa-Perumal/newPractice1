package QuartzPassData;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzDatajob1 implements Job  {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		 String name= context.getJobDetail().getJobDataMap().getString("name");
		 System.out.println(name);
		
	}

}
