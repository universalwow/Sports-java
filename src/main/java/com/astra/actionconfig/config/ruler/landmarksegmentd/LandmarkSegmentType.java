package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;

@Data
public class LandmarkSegmentType {
    public String id;
    public LandmarkType endLandmarkType;
    public LandmarkType startLandmarkType;
    /*
                 "endLandmarkType": "LeftAnkle",
                "id": "LeftKnee-LeftAnkle",
                "startLandmarkType": "LeftKnee"
     */
}
