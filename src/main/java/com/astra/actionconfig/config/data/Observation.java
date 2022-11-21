package com.astra.actionconfig.config.data;

import lombok.Data;
import org.w3c.dom.css.Rect;

import java.awt.*;


@Data
public class Observation {

    public String id;
    public String label; //标签
    public String confidence = "1.00"; //这是否应该用float
    public ColorState color;
    public float[][] rect = new float[2][2];
    public Boolean selected = false;

    public Rectangle rectangle() {
        return new Rectangle((int)(rect[0][0]), (int)(rect[0][1]), (int)(rect[1][0]), (int)(rect[1][1]));
    }

}
