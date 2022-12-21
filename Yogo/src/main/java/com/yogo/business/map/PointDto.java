package com.yogo.business.map;

import com.yogo.model.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class PointDto {
    private Coordinates location;
    private String name;
}
