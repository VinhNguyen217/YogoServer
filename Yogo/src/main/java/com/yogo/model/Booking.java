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

    @Override
    public String toString() {
        return "Booking{" +
                "serviceId='" + serviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", latStartPoint=" + latStartPoint +
                ", lonStartPoint=" + lonStartPoint +
                ", latEndPoint=" + latEndPoint +
                ", lonEndPoint=" + lonEndPoint +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", finishAt=" + finishAt +
                '}';
    }
}
