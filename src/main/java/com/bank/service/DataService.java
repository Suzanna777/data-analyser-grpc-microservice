package com.bank.service;

import com.bank.model.Data;

import java.util.List;

public interface DataService {

    void handle(Data data);
    List<Data> getWithBatch(long batchSize);

}
