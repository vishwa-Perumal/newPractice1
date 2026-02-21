package QuartzTest1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Quartz2 implements Job {

		public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("hello this is from job ");
			
		}

}
