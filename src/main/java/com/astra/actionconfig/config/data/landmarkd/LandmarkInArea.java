package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.Point2F;
import lombok.Data;

@Data
public class LandmarkInArea {
    public String id = "";
    public Point2F[] area = new Point2F[4];
    public String areaId = "";
    public Point2F imageSize; // x=1920,y=1080
    public Landmark landmark;
    public Warning warning;
}
