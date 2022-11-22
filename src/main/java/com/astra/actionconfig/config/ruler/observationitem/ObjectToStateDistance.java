package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.ObjectPositionPoint;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import lombok.Data;

@Data
public class ObjectToStateDistance {
    public String extremeDirection = "MaxX";
    public String id = "";
    public Boolean isRelativeToExtremeDirection = false;
    public Boolean isRelativeToObject = false;
    public double lowerBound = -3;
    public double upperBound = 0;
    public int toStateId = 0;
    public Warning warning;

    public Observation object;

    public ObjectPositionPoint fromPosition;
    public ObjectPositionPoint toPosition;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;

    /*
    {
    "extremeDirection": "MaxX",
    "fromPosition": {
        "axis": "X",
        "id": "rope",
        "point": {
            "x": 928.5,
            "y": 768.75
        },
        "position": "middle"
    },
    "id": "5FC921B9-1817-406B-A25B-2E4A5B94895D",
    "isRelativeToExtremeDirection": true,
    "isRelativeToObject": true,
    "lowerBound": 0,
    "object": {
        "color": {
            "blue": 0.9999999403953552,
            "green": 0.4784314036369324,
            "red": 0
        },
        "confidence": "0.99",
        "id": "rope - 1",
        "label": "rope",
        "rect": [
            [
                747.9375,
                571.3125
            ],
            [
                361.125,
                394.875
            ]
        ],
        "selected": true
    },
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
    "toPosition": {
        "axis": "X",
        "id": "rope",
        "point": {
            "x": 928.5,
            "y": 768.75
        },
        "position": "middle"
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
