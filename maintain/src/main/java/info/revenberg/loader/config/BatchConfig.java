package info.revenberg.loader.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import info.revenberg.domain.Line;
import info.revenberg.domain.Vers;
//import info.revenberg.domain.line.FindLinesInImage;
import info.revenberg.loader.listener.JobCompletionListener;
import info.revenberg.loader.objects.DataObject;
//import info.revenberg.loader.objects.DataObject;
import info.revenberg.loader.step.Processor;
import info.revenberg.loader.step.Reader;
import info.revenberg.loader.step.Writer;

@Configuration
@EnableScheduling
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Scheduled(cron = "0/1 * * * * ?")
    @Bean
    public Job job() {
		return jobBuilderFactory.get("job")				
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
	}

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(step1())
				.end()				
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor(){
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Long, DataObject > chunk(1)
				.reader(new Reader())							
				.processor(new Processor())
				.writer(new Writer())
				.taskExecutor(taskExecutor())
				.allowStartIfComplete(true)
				.startLimit(1)
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}