package com.astra.actionconfig.config.data;

import lombok.Data;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Collections;
import java.util.List;


@Data
public class Observation implements Comparable<Observation> {

    public String id;
    public String label; //标签
    public String confidence = "1.00"; //这是否应该用float
    public ColorState color;
    public double[][] rect = new double[2][2];
    public Boolean selected = false;


    public Observation() {}

    public Observation(String id, String label, String confidence, double[][] rect) {
        this.id = id;
        this.label = label;
        this.confidence = confidence;
        this.color = new ColorState();
        this.rect = rect;
        this.selected = false;

    }

    public Rectangle rectangle() {
        return new Rectangle((int)(rect[0][0]), (int)(rect[0][1]), (int)(rect[1][0]), (int)(rect[1][1]));
    }

    public static Observation initObservation(String id, String label, String confidence, List<List<Double>> rect) {
        double[][] rect_ = new double[2][2];
        rect_[0][0] = rect.get(0).get(0);
        rect_[0][1] = rect.get(0).get(1);
        rect_[1][0] = rect.get(1).get(0);
        rect_[1][1] = rect.get(1).get(1);
        return  new Observation(id, label, confidence, rect_);
    }

    public static List<Observation> sort(List<Observation> observations) {
        Collections.sort(observations);
        System.out.println(observations);
        return observations;
    }


    public Point3F pointOf(ObjectPosition position) {
        Rectangle rect = rectangle();
        Point3F point = new Point3F(0.,0.,0.);
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

    @Override
    public int compareTo(Observation o) {
        return (int) (this.rect[0][0] - o.rect[0][0]);
    }
}
