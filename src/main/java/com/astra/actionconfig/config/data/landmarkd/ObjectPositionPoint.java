package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Point2F;
import lombok.Data;

enum ObjectPosition {
    topLeft, topMiddle, topRight,
    middleLeft, middle, middleRight,
    bottomLeft, bottomMiddle, bottomRight

}


@Data
public class ObjectPositionPoint {
    public CoordinateAxis axis;
    public String id;
    public Point2F point;
    public ObjectPosition position;
}
