package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Point3F {
    public double x;
    public double y;
    public double z;

    public Point3F(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3F() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point2F point2F() {
        return new Point2F(this.x, this.y);
    }

    public boolean isEmpty() {
        if (this.x < 0.001 && this.y < 0.001) {
            return true;
        }
        return false;
    }

    public Vector2D vector2D() {
        return new Vector2D(this.x, this.y);
    }




}
