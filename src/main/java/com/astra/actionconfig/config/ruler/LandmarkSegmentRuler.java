package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.ruler.landmarksegmentd.*;
import lombok.Data;

import java.util.List;

@Data
public class LandmarkSegmentRuler {
    public LandmarkSegmentType landmarkSegmentType;
    public String ruleClass = "";
    public String id = "";
    public List<LandmarkSegmentAngle> angleToLandmarkSegment;
    public List<LandmarkSegmentAngleRuler> landmarkSegmentAngle;
    public List<LandmarkSegmentDistanceRuler> landmarkSegmentLength;
    public List<LandmarkSegmentStateAngleRuler> landmarkSegmentToStateAngle;
    public List<LandmarkSegmentStateDistanceRuler> landmarkSegmentToStateDistance;
    /*
angleToLandmarkSegment[
landmarkSegmentAngle[
landmarkSegmentLength[
landmarkSegmentToStateAngle[
landmarkSegmentToStateDistance[
     */
}

