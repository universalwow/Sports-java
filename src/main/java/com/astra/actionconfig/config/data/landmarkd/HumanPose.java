package com.astra.actionconfig.config.data.landmarkd;

import lombok.Data;

import java.util.List;

@Data
public class HumanPose {
    public String id = "";//编号
    public List<LandmarkSegment> landmarkSegments;
    public List<LandmarkPosition> landmarks;

}
