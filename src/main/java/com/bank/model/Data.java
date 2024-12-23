package com.bank.model;

import com.bank.grpccommon.GRPCData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "data")
@NoArgsConstructor
@Getter
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sensorId;
    private LocalDateTime timestamp;
    private double measurement;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private MeasurementType measurementType;
    public enum MeasurementType{
        TEMPERATURE,
        VOLTAGE,
        POWER
    }
    public Data(GRPCData data){
        this.id = data.getId();
        this.sensorId = getSensorId();
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(data.getTimestamp().getSeconds(), data.getTimestamp().getNanos() ), ZoneId.systemDefault() );
        this.measurement = data.getMeasurement();
        this.measurementType = MeasurementType.valueOf(data.getMeasurementType().name());
    }
}
