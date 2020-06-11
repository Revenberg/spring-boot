package info.revenberg.loader.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Reader implements ItemReader<Long> {

	@Autowired
    private JobExplorer jobExplorer;
    @Autowired
    JobRepository jobRepository;
    //@Autowired
    //private JobLauncher jobLauncher;
    @Autowired 
	JobOperator jobOperator;
	
	private static Long lastID = 0L;

	@Override
	public synchronized Long read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {	
				try {			
					
					List<JobExecution> runningJobInstances = new ArrayList<JobExecution>();
					List<String> jobNames = jobExplorer.getJobNames();
					for (String jobName : jobNames) {
					Set<JobExecution> jobExecutions
							= jobExplorer.findRunningJobExecutions(jobName);
					runningJobInstances.addAll(jobExecutions);
					}

					List<JobInstance> jobInstances = jobExplorer.getJobInstances("job",0,100);// this will get one latest job from the database
					if(CollectionUtils.isNotEmpty(jobInstances)){						
					   JobInstance jobInstance =  jobInstances.get(0);
					   System.out.println(jobInstance);					
					   List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
					   if(CollectionUtils.isNotEmpty(jobExecutions)){
						   for(JobExecution execution: jobExecutions){
							   // If the job status is STARTED then update the status to FAILED and restart the job using JobOperator.java
							   if(execution.getStatus().equals(BatchStatus.STARTED)){ 
								System.out.println(execution);					
								   execution.setEndTime(new Date());
								   execution.setStatus(BatchStatus.FAILED);                               
								   execution.setExitStatus(ExitStatus.FAILED);                               
								   jobRepository.update(execution);
								   jobOperator.restart(execution.getId());
							   }
						   }
					   }
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}





				
				
		String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(lastID) + "/next";

		RestTemplate restTemplate = new RestTemplate();

		Long id = restTemplate.getForObject(uri, Long.class);
		if (id == null) {
			return null;
		}
		if (lastID == id) {
			return read();
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(Long.toString(id));
		lastID = id;
		
		return lastID;

		/*
		 * RestResponsePage pages = restTemplate.getForObject(uri,
		 * RestResponsePage.class);
		 * 
		 * List v = pages.getContent(); LinkedHashMap c = (LinkedHashMap) v.get(0);
		 * System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!! a !!!!!!!!!!!!!!"); System.out.println(c);
		 * System.out.println(c.getClass());
		 * System.out.println(c.getClass().getSimpleName());
		 * System.out.println(c.size()); System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
		 */

		/*
		 * if (c.isPresent()) { System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!! a !!!!!!!!!!!!!!"); System.out.println(c.get());
		 * System.out.println(c.get().getClass());
		 * System.out.println(c.get().getClass().getName());
		 * System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!!!!! b !!!!!!!!!!!"); return (Vers) c.get(); }
		 */

		// Vers vers = result.get(0);
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// System.out.println(vers.getId());
		// System.out.println(vers.getLocation());

		/*
		 * if (!list.isEmpty()) { String element = list.get(0); list.remove(0); return
		 * new Vers(element); }
		 */

	}

}