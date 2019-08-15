package com.example.spring_batch_sample_04.batch.xyz.entity;

import lombok.Data;

@Data
public class MultiProcMgrEntity {
    private Integer id;
    private Integer reqId;
    private Integer multiProcNo;
    private String procTarget;
    private String procType;
    private String states;
    private String errorInfo;
}
