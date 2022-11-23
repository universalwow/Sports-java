package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.ColorState;
import com.astra.actionconfig.config.data.Point3F;
import lombok.Data;

import java.util.Map;

import static java.lang.Math.abs;


public class LandmarkTypeSegment {
    public LandmarkType startLandmarkType;
    public LandmarkType endLandmarkType;
    public String id;


    public LandmarkTypeSegment(LandmarkType startLandmarkType, LandmarkType endLandmarkType) {
        this.startLandmarkType = startLandmarkType;
        this.endLandmarkType = endLandmarkType;
        this.id = String.format("%s-%s", this.startLandmarkType, this.endLandmarkType);
    }

    public LandmarkSegment landmarkSegment(Map<LandmarkType, Point3F> poseMap) {
        Point3F startPosition = poseMap.get(this.startLandmarkType);
        Point3F endPosition = poseMap.get(this.endLandmarkType);

        return new LandmarkSegment(
                new Landmark(this.startLandmarkType, startPosition),
                new Landmark(this.endLandmarkType, endPosition));
    }
}

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


    public LandmarkTypeSegment landmarkTypeSegment() {
        return new LandmarkTypeSegment(startLandmark.landmarkType, endLandmark.landmarkType);
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
        return
    }

}
