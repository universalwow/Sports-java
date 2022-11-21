package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.ColorState;
import com.astra.actionconfig.config.data.Point3F;
import lombok.Data;

@Data
public class LandmarkPosition {
    public String landmarkType = "";//关节点
    public ColorState color;
    public Point3F position;
    public Boolean selected = false;
}
