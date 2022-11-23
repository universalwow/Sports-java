package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.ColorState;
import com.astra.actionconfig.config.data.Point3F;
import lombok.Data;

@Data
public class Landmark {
    public LandmarkType landmarkType;//关节点
    public ColorState color = new ColorState();
    public Point3F position;
    public Boolean selected = false;


    public Landmark(LandmarkType landmarkType, Point3F position) {
        this.landmarkType = landmarkType;
        this.position = position;
    }
}
