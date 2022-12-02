package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Point3F;

public class ExtremePoint3D {
    public Point3F minX;
    public Point3F maxX;
    public Point3F minY;
    public Point3F maxY;

    public ExtremePoint3D(Point3F point) {
        this.minX = point;
        this.maxX = point;
        this.minY = point;
        this.maxY = point;
    }
}
