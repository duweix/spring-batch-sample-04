package com.example.spring_batch_sample_04.batch.xyz.step2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.validation.BindException;

import com.example.spring_batch_sample_04.batch.xyz.entity.MultiProcMgrEntity;
import com.example.spring_batch_sample_04.batch.xyz.entity.PrefectureCsvDataEntity;

@Configuration
public class Step2Config {

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step step2(TaskExecutor asyncTaskExecutor) {
        // @formatter:off
        return steps.get("step2")
                .partitioner("slaveStepPartitioner", partitioner(null))
                .step(slaveStep())
                .taskExecutor(asyncTaskExecutor)
                .build();
        // @formatter:on
    }

    @Bean
    @StepScope
    public Partitioner partitioner(@Value("#{jobExecutionContext[entityList]}") List<MultiProcMgrEntity> entityList) {
        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
        partitioner.setEntityList(entityList);
        return partitioner;
    }

    @Bean
    public Step slaveStep() {
        // @formatter:off
        return steps.get("slaveStep")
                .<PrefectureCsvDataEntity, PrefectureCsvDataEntity>chunk(1)
                .reader(slaveStepMultiResourceReader(null))
                .writer(slaveStepWriter())
                .build();
        // @formatter:on
    }

    @Bean
    @StepScope
    public MultiResourceItemReader<PrefectureCsvDataEntity> slaveStepMultiResourceReader(@Value("#{stepExecutionContext[resourcePathes]}") List<String> resourcePathes) {
        Resource[] resources = resourcePathes.stream().map(path -> new FileSystemResource(path)).collect(Collectors.toList()).toArray(new Resource[0]);
        // @formatter:off
        MultiResourceItemReader<PrefectureCsvDataEntity> reader =
                new MultiResourceItemReaderBuilder<PrefectureCsvDataEntity>()
                .name("slaveStepMultiResourceReader")
                .delegate(slaveStepReader())
                .resources(resources)
                .build();
        // @formatter:on
        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PrefectureCsvDataEntity> slaveStepReader() {
        DefaultLineMapper<PrefectureCsvDataEntity> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new FieldSetMapper<PrefectureCsvDataEntity>() {

            @Override
            public PrefectureCsvDataEntity mapFieldSet(FieldSet fieldSet) throws BindException {
                PrefectureCsvDataEntity entity = new PrefectureCsvDataEntity();
                entity.setField1(fieldSet.readString(0));
                entity.setField2(fieldSet.readString(1));
                entity.setField3(fieldSet.readString(2));
                return entity;
            }

        });

        FlatFileItemReader<PrefectureCsvDataEntity> reader = new FlatFileItemReader<>();
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<PrefectureCsvDataEntity> slaveStepWriter() {
        return new ItemWriter<PrefectureCsvDataEntity>() {

            @Override
            public void write(List<? extends PrefectureCsvDataEntity> items) throws Exception {
                items.stream().forEach(System.out::println);
            }
        };
    }
}
