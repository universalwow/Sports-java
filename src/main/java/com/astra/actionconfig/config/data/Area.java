package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Area {
    public String id = "";
    public int heightToWidthRatio = 1;
    public float width = 0.1f;
    public Point2F[] area = new Point2F[4];
    public double[] limitedArea = new double[4];
}
