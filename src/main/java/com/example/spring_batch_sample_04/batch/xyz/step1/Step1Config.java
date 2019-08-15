package com.example.spring_batch_sample_04.batch.xyz.step1;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.spring_batch_sample_04.batch.xyz.entity.MultiProcMgrEntity;

@Configuration
public class Step1Config {

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step step1(PlatformTransactionManager transactionManager) {
        // @formatter:off
        return steps.get("step1")
                .transactionManager(transactionManager)
                .<MultiProcMgrEntity, MultiProcMgrEntity>chunk(100)
                .reader(step1Reader(null))
                .writer(step1Writer())
                .listener(promotionListener())
                .build();
        // @formatter:on
    }

    @Bean
    public JdbcCursorItemReader<MultiProcMgrEntity> step1Reader(DataSource dataSource) {
        // @formatter:off
        return new JdbcCursorItemReaderBuilder<MultiProcMgrEntity>()
                .name("step1Reader")
                .dataSource(dataSource)
                .sql("select * from MULTI_PROC_MGR")
                .beanRowMapper(MultiProcMgrEntity.class)
                .build();
        // @formatter:on
    }

    @Bean
    public ItemWriter<MultiProcMgrEntity> step1Writer() {
        return new ItemWriter<MultiProcMgrEntity>() {
            private StepExecution stepExecution;

            @Override
            public void write(List<? extends MultiProcMgrEntity> items) throws Exception {
                ExecutionContext stepExecutionContext = this.stepExecution.getExecutionContext();
                @SuppressWarnings("unchecked")
                List<MultiProcMgrEntity> entityList = (List<MultiProcMgrEntity>) stepExecutionContext.get("entityList");
                if (entityList == null) {
                    entityList = new ArrayList<>();
                    stepExecutionContext.put("entityList", entityList);
                }
                entityList.addAll(items);
            }

            @BeforeStep
            public void beforeStep(StepExecution stepExecution) {
                this.stepExecution = stepExecution;
            }
        };
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] { "entityList" });
        return listener;
    }
}
