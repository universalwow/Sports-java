package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class FixedArea {
    public String id = "";
    public String content = "";
    public int heightToWidthRatio = 1;
    public float width = 0.1f;
    public Point2F[] area    = new Point2F[4];
    public Point2F center;
    public Point2F imageSize;
}
