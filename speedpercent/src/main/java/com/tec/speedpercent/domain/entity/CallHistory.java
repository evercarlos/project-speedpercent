package com.tec.speedpercent.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "call_history")
public class CallHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "register_date")
    private LocalDateTime date;

    private String endpoint;

    @Column(name = "parameter_json")
    private String parameterJson;

    private double response;

    private String error;
}
