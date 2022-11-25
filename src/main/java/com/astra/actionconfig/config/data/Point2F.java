package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Point2F {
    public double x;
    public double y;


    public Point2F(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
