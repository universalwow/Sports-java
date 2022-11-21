package com.astra.actionconfig.config.data.landmarkd;

import lombok.Data;

import java.util.List;

@Data
public class HumanPose {
    public int id;//编号
    public List<LandmarkSegment> landmarkSegments;
    public List<Landmark> landmarks;

}
