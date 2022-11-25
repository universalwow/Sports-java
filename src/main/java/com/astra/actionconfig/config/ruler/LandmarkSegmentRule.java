package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentType;
import com.astra.actionconfig.config.ruler.landmarksegmentd.*;
import lombok.Data;

import java.util.List;

@Data
public class LandmarkSegmentRule {
    public LandmarkSegmentType landmarkSegmentType;
    public RuleClass ruleClass = RuleClass.LandmarkSegment;
    public String id = "";
    public List<AngleToLandmarkSegment> angleToLandmarkSegment;
    public List<LandmarkSegmentAngle> landmarkSegmentAngle;
    public List<LandmarkSegmentLength> landmarkSegmentLength;
    public List<LandmarkSegmentToStateAngle> landmarkSegmentToStateAngle;
    public List<LandmarkSegmentToStateDistance> landmarkSegmentToStateDistance;

}

