package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.ruler.observationitem.ExtremeDirection;
import lombok.Data;

@Data
public class LandmarkSegmentToStateAngle {
    public String id = "";
    public Boolean isRelativeToExtremeDirection = true;
    public ExtremeDirection extremeDirection = ExtremeDirection.MinX;
    public double lowerBound = 0;
    public int toStateId = 0;
    public double upperBound = 0;
    public LandmarkSegment fromLandmarkSegment;
    public LandmarkSegment toLandmarkSegment;
    public Warning warning;

    /*
     {
        "extremeDirection": "MinX_MinY",
        "fromLandmarkSegment": {
            "color": {
                "blue": 0.9999999403953552,
                "green": 0.4784314036369324,
                "red": 0
            },
            "endLandmark": {
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
                "selected": false
            },
            "selected": true,
            "startLandmark": {
                "color": {
                    "blue": 0.9999999403953552,
                    "green": 0.9999999403953552,
                    "red": 0.9999999403953552
                },
                "landmarkType": "LeftKnee",
                "position": {
                    "x": 886.864501953125,
                    "y": 732.7236328125,
                    "z": -2.663571834564209
                },
                "selected": false
            }
        },
        "id": "4F35130F-6211-4211-9EC4-C143729379F7",
        "isRelativeToExtremeDirection": true,
        "lowerBound": 0,
        "toLandmarkSegment": {
            "color": {
                "blue": 0.9999999403953552,
                "green": 0.4784314036369324,
                "red": 0
            },
            "endLandmark": {
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
                "selected": false
            },
            "selected": true,
            "startLandmark": {
                "color": {
                    "blue": 0.9999999403953552,
                    "green": 0.9999999403953552,
                    "red": 0.9999999403953552
                },
                "landmarkType": "LeftKnee",
                "position": {
                    "x": 886.864501953125,
                    "y": 732.7236328125,
                    "z": -2.663571834564209
                },
                "selected": false
            }
        },
        "toStateId": 7,
        "upperBound": 0,
        "warning": {
            "changeStateClear": true,
            "content": "",
            "delayTime": 2,
            "isScoreWarning": true,
            "triggeredWhenRuleMet": false
        }
    }

     */
}
