package com.maestre.correobatch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.mail.SimpleMailMessage;


@Configuration
@Import(DataSourceConfiguration.class)
public class BatchConfiguration {
	@Autowired
	JobRepository jobRepository;

	@Bean
	public FlatFileItemReader<EmailData> reader() {
		return new FlatFileItemReaderBuilder<EmailData>().name("emailItemReader")
				.resource(new ClassPathResource("sample-data.csv")).delimited().names("email", "asunto", "cuerpo")
				.targetType(EmailData.class).build();
	}

	@Bean
	public MailProcessor processor() {
		return new MailProcessor();
	}

	@Bean
	public SendMail writer() {
		return new SendMail();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
	return new SimpleAsyncTaskExecutor("spring_batch");
	}
	@Bean
	public Step step1(TaskExecutor taskExecutor, JobRepository jobRepository,
	JdbcTransactionManager transactionManager) {
	Step step = new StepBuilder("step1", jobRepository)
	.<EmailData, SimpleMailMessage> chunk(5, transactionManager)
	.reader(reader())
	.processor(processor())
	.writer(writer())
	.taskExecutor(taskExecutor)
	.build();
	return step;
	}
	@Bean
	public Job job(JobRepository jobRepository, Step step) {
	Job job = new JobBuilder("job", jobRepository).start(step).build();
	return job;
	}
}
