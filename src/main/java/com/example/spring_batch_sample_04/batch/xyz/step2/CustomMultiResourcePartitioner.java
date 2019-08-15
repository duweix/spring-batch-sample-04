package com.example.spring_batch_sample_04.batch.xyz.step2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.FileSystemResource;

import com.example.spring_batch_sample_04.batch.xyz.entity.MultiProcMgrEntity;

public class CustomMultiResourcePartitioner implements Partitioner {

    private List<MultiProcMgrEntity> entityList;

    public List<MultiProcMgrEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<MultiProcMgrEntity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<Integer> multiProcNoList = entityList.stream().map(entity -> entity.getMultiProcNo()).distinct().collect(Collectors.toList());
        final Map<String, ExecutionContext> map = new HashMap<>();

        int i = 0;
        for (Integer multiProcNo : multiProcNoList) {
            List<MultiProcMgrEntity> multiProcNoEntityList = entityList.stream().filter(entity -> entity.getMultiProcNo() == multiProcNo).collect(Collectors.toList());
            // @formatter:off
            List<String> resourcePathes = multiProcNoEntityList.stream()
                .map(entity -> "E:\\prefecture-csv-files\\" + entity.getProcTarget() + ".csv")
                .filter(path -> new FileSystemResource(path).exists())
                .collect(Collectors.toList());
            // @formatter:on
            if (resourcePathes.size() > 0) {
                ExecutionContext context = new ExecutionContext();
                context.put("resourcePathes", resourcePathes);
                map.put("PARTITION_KEY_" + i++, context);
            }
        }

        return map;
    }

}
