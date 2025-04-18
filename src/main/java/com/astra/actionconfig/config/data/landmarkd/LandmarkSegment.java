package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.ColorState;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Vector2D;
import lombok.Data;

import java.util.Map;

import static java.lang.Math.abs;


@Data
public class LandmarkSegment {

    public ColorState color = new ColorState();
    public Boolean selected = false;
    public Landmark startLandmark;
    public Landmark endLandmark;

    public LandmarkSegment(Landmark startLandmark, Landmark endLandmark) {
        this.startLandmark = startLandmark;
        this.endLandmark = endLandmark;
    }

    public LandmarkSegment(){}

    public LandmarkTypeSegment landmarkTypeSegment() {
        return new LandmarkTypeSegment(startLandmark.landmarkType, endLandmark.landmarkType);
    }

    public boolean isEmpty() {
        if (startLandmark.isEmpty() || endLandmark.isEmpty()) {
            return true;
        }
        return  false;

    }

    public double angle() {
        return Vector2D.minus(endLandmark.position.vector2D(), startLandmark.position.vector2D()).oppositeY().angle();
    }

    public double distanceX() {
        return abs(startLandmark.position.x - endLandmark.position.x);
    }

    public double distanceY() {
        return abs(startLandmark.position.y - endLandmark.position.y);
    }

    public double distanceXWithDirection() {
        return startLandmark.position.x - endLandmark.position.x;
    }

    public double distanceYWithDirection() {
        return startLandmark.position.y - endLandmark.position.y;
    }

    public double distance() {
        return endLandmark.position.vector2D().distance(startLandmark.position.vector2D());
    }

}
