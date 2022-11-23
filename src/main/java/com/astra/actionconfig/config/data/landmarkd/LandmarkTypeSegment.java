package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Point3F;

import java.util.Map;

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
