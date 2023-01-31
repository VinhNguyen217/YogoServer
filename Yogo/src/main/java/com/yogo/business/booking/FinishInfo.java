package com.yogo.business.booking;

import com.yogo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class FinishInfo {
    private String bookingId;
    private String status = Status.FINISH.name();
}
