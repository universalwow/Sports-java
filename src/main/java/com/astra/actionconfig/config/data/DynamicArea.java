package com.astra.actionconfig.config.data;

import lombok.Data;

import java.util.Optional;

@Data
public class DynamicArea {
    public String id;
    public int heightToWidthRatio = 1;
    public float width = 0.1f;
    public Point2F[] area = new Point2F[4];
    public double[] limitedArea = new double[4];

    public Optional<Point2F> imageSize;
    public Optional<Boolean> selected;
    public String content = "";

}
