package com.bank.service;

import com.bank.model.Data;
import com.bank.grpccommon.AnalyticsServerGrpc;
import com.bank.grpccommon.GRPCAnalyticsRequest;
import com.bank.grpccommon.GRPCData;
import com.bank.grpccommon.MeasurementType;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.List;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class GRPCAnalyticsService extends AnalyticsServerGrpc.AnalyticsServerImplBase {
    private final DataService dataService;

    @Override
    public void askForData(GRPCAnalyticsRequest request, StreamObserver<GRPCData> responseObserver) {
        List<Data> data = dataService.getWithBatch(request.getBatchSize());
        for (Data d : data) {
            GRPCData dataRequest = GRPCData.newBuilder()
                    .setSensorId(d.getSensorId())
                    .setTimestamp(Timestamp.newBuilder()
                            .setSeconds(d.getTimestamp().toEpochSecond(ZoneOffset.UTC))
                            .build())
                    .setMeasurementType(MeasurementType.valueOf(d.getMeasurementType().name()))
                    .setMeasurement(d.getMeasurement())
                    .build();
            responseObserver.onNext(dataRequest);
        }
        log.info("batch was sent ");
        responseObserver.onCompleted();

    }
}
