package com.astra.actionconfig.config.data;

import com.astra.actionconfig.config.data.landmarkd.CoordinateAxis;
import lombok.Data;


@Data
public class ObjectPositionPoint {
    public CoordinateAxis axis;
    public String id;
    public Point2F point;
    public ObjectPosition position;
}
