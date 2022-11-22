package com.astra.actionconfig.config.data.landmarkd;

import lombok.Data;

@Data
public class LandmarkToAxis {
    public CoordinateAxis axis;//关节点
    public Landmark landmark;
    /*
        "axis": "X",
    "landmark": {
        "color": {
            "blue": 0.9999999403953552,
            "green": 0.9999999403953552,
            "red": 0.9999999403953552
        },
        "landmarkType": "LeftAnkle",
        "position": {
            "x": 895.2235107421875,
            "y": 848.5889892578125,
            "z": 283.5280456542969
        },
        "selected": true
    }
     */
}
