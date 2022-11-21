package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkPositionAxis;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import lombok.Data;

@Data
public class LandmarkToStateDistance {
    public String id = "";
    public Boolean isRelativeToExtremeDirection = true;
    public double lowerBound = 0;
    public int toStateId = 0;
    public double upperBound = 0;
    public String extremeDirection = "MaxX";
    public Boolean defaultSatisfy = true;
    public LandmarkPositionAxis fromLandmarkToAxis;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;
    public LandmarkPositionAxis toLandmarkToAxis;
    public Warning warning;

    /*
     {
        "defaultSatisfy": true,
        "extremeDirection": "MaxX",
        "fromLandmarkToAxis": {
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
        },
        "id": "A13E81B5-EF1B-4D81-A4AD-8C8499BE8F63",
        "isRelativeToExtremeDirection": true,
        "lowerBound": 0,
        "toLandmarkSegmentToAxis": {
            "axis": "X",
            "landmarkSegment": {
                "color": {
                    "blue": 0.9999999403953552,
                    "green": 0.9999999403953552,
                    "red": 0.9999999403953552
                },
                "endLandmark": {
                    "color": {
                        "blue": 0.9999999403953552,
                        "green": 0.9999999403953552,
                        "red": 0.9999999403953552
                    },
                    "landmarkType": "RightShoulder",
                    "position": {
                        "x": 979.5059814453125,
                        "y": 406.466796875,
                        "z": -89.5245590209961
                    },
                    "selected": false
                },
                "selected": false,
                "startLandmark": {
                    "color": {
                        "blue": 0.9999999403953552,
                        "green": 0.9999999403953552,
                        "red": 0.9999999403953552
                    },
                    "landmarkType": "LeftShoulder",
                    "position": {
                        "x": 856.8896484375,
                        "y": 409.37396240234375,
                        "z": -93.81895446777344
                    },
                    "selected": false
                }
            }
        },
        "toLandmarkToAxis": {
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
        },
        "toStateId": 7,
        "upperBound": 0,
        "warning": {
            "changeStateClear": true,
            "content": "",
            "delayTime": 2,
            "isScoreWarning": true,
            "triggeredWhenRuleMet": true
        }
    }


     */
}
