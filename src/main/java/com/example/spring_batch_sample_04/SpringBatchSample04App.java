package com.example.spring_batch_sample_04;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.spring_batch_sample_04.batch.xyz.BatchXyzConfig;

@Configuration
@EnableBatchProcessing(modular = true)
public class SpringBatchSample04App {

    public static void main(String... args) throws Exception {
        new SpringBatchSample04App().run();
    }

    public void run() throws Exception {
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-context-config/application-context.xml")) {
            JobLauncher jobLauncher = appCtx.getBean("jobLauncher", JobLauncher.class);
            Job job = appCtx.getBean("xyzJob", Job.class);

            jobLauncher.run(job, new JobParametersBuilder().toJobParameters());

            System.in.read();
        }
    }

    @Bean
    public GenericApplicationContextFactory batchXyz() {
        return new GenericApplicationContextFactory(BatchXyzConfig.class);
    }

}
