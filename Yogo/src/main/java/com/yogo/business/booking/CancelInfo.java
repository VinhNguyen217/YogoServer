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
public class CancelInfo {
    private String bookingId;
    private String status = Status.CANCEL.name();
    private String cancelBy;
}
