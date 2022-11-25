package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Point3F {
    public double x;
    public double y;
    public double z;

    public Vector2D vector2D() {
        return new Vector2D(this.x, this.y);
    }


}
