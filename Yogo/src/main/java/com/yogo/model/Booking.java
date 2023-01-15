package com.yogo.model;

import com.yogo.business.booking.BookingInfoDto;
import com.yogo.enums.Status;
import com.yogo.utils.TimeUtils;
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

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime finishAt;

    public BookingInfoDto convert() {
        return new BookingInfoDto().withId(this.getId())
                .withLatStartPoint(this.latStartPoint)
                .withLonStartPoint(this.lonStartPoint)
                .withLatEndPoint(this.latEndPoint)
                .withLonEndPoint(this.lonEndPoint)
                .withTotalPrice(this.totalPrice)
                .withStatus(this.status.name())
                .withNotes(this.notes)
                .withCreatedAt(TimeUtils.convertToStr(this.getCreatedAt()));
    }

}
