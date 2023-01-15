package com.yogo.business.booking;

import com.yogo.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class BookingInfoDto {
    private String id;

    private Double latStartPoint;

    private Double lonStartPoint;

    private Double latEndPoint;

    private Double lonEndPoint;

    private Double totalPrice;

    private String status;

    private String createdAt;

    @Override
    public String toString() {
        return "BookingInfoDto{" +
                "id='" + id + '\'' +
                ", latStartPoint=" + latStartPoint +
                ", lonStartPoint=" + lonStartPoint +
                ", latEndPoint=" + latEndPoint +
                ", lonEndPoint=" + lonEndPoint +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
