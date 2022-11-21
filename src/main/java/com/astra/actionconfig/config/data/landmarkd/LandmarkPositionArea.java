package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Point2F;
import lombok.Data;

@Data
public class LandmarkPositionArea {
    public String axis = "Y";
    public String id = "";
    public Point2F point;
    public String position = "";
}
