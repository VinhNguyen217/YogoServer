package com.yogo.business.track;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class TrackSendInfo {
    private String to;
    private Double lat;    // Vĩ độ
    private Double lon;   // Kinh độ
}
