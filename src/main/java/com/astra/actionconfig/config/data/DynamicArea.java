package com.astra.actionconfig.config.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class DynamicArea implements Serializable {
    public String id;
    public double heightToWidthRatio = 1;
    public float width = 0.1f;
    public Point2F[] area = new Point2F[4];
    public double[] limitedArea = new double[4];

    public Optional<Point2F> imageSize = Optional.empty();
    public Optional<Boolean> selected = Optional.empty();
    public String content = "";

}
