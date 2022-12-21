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

    private Integer serviceId;

    private Integer userId;

    private Double startPoint;

    private Double endPoint;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime finishAt;
}
