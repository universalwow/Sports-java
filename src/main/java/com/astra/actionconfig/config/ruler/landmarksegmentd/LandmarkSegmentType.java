package com.astra.actionconfig.config.ruler.landmarksegmentd;

import lombok.Data;

@Data
public class LandmarkSegmentType {
    public String id;
    public String endLandmarkType = "";
    public String startLandmarkType = "";
    /*
                 "endLandmarkType": "LeftAnkle",
                "id": "LeftKnee-LeftAnkle",
                "startLandmarkType": "LeftKnee"
     */
}
