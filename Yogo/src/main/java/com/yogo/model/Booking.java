package com.yogo.model;

import com.yogo.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@With
@Table(name = "booking")
public class Booking extends BaseModel {

    private String serviceId;

    private String userId;

    private Double latStartPoint;

    private Double lonStartPoint;

    private Double latEndPoint;

    private Double lonEndPoint;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime finishAt;
}
