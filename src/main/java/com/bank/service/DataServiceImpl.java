package com.bank.service;

import com.bank.model.Data;
import com.bank.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

    private final DataRepository dataRepository;

    @Override
    public void handle(Data data) {
        log.info("Data object {} was saved", data);
        dataRepository.save(data);
    }

    @Override
    public List<Data> getWithBatch(long batchSize) {
       List<Data> data = dataRepository.findAllWithOffset(batchSize);
       if (data.size() > 0){
           dataRepository.incrementOffset(Long.min(batchSize, data.size() ) );
       }
        return data;
    }
}
