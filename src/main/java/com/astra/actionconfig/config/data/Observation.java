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

    public Point3F pointOf(ObjectPosition position) {
        Rectangle rect = rectangle();
        Point3F point = new Point3F();
        switch (position) {

            case topLeft:
                point.x = rect.getMinX();
                point.y = rect.getMinY();

                break;
            case topMiddle:
                point.x = rect.getCenterX();
                point.y = rect.getMinY();
                break;
            case topRight:
                point.x = rect.getMaxX();
                point.y = rect.getMinY();
                break;
            case middleLeft:
                point.x = rect.getMinX();
                point.y = rect.getCenterY();
                break;
            case middle:
                point.x = rect.getCenterX();
                point.y = rect.getCenterY();
                break;
            case middleRight:
                point.x = rect.getMaxX();
                point.y = rect.getCenterY();
                break;
            case bottomLeft:
                point.x = rect.getMinX();
                point.y = rect.getMaxY();

                break;
            case bottomMiddle:
                point.x = rect.getCenterX();
                point.y = rect.getMaxY();

                break;
            case bottomRight:
                point.x = rect.getMaxX();
                point.y = rect.getMaxY();

                break;
        }
        return point;
    }

}
