package com.example.spring_batch_sample_04.batch.xyz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class BatchXyzConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Bean
    public Job xyzJob(JobRepository jobRepository, Step step1, Step step2) {
        // @formatter:off
        return jobs.get("xyzJob")
                .repository(jobRepository)
                .start(step1)
                .next(step2)
                .build();
        // @formatter:on
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(asyncTaskExecutor());
        return jobLauncher;
    }

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        return asyncTaskExecutor;
    }
}
