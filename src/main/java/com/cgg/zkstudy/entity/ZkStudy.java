package com.cgg.zkstudy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ZkStudy {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "active_time")
    private Date activeTime;

    @Column(name = "status")
    private String status;
}
