package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.ObjectPositionPoint;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import lombok.Data;

@Data
public class ObjectToObject {
    public String id = "";
    public Boolean isRelativeToObject = true;
    public double lowerBound;
    public double upperBound;
    public ObjectPositionPoint fromPosition;
    public ObjectPositionPoint toPosition;
    public Observation object;
    public Warning warning;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;

/*
    {
        "fromPosition": {
            "axis": "Y",
            "id": "rope",
            "point": {
                "x": 928.5,
                "y": 768.75
            },
            "position": "middle"
        },
        "id": "90C8A701-426F-4E01-A3A0-C8224D84DC45",
        "isRelativeToObject": true,
        "lowerBound": 0.9101007599814024,
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
        "toLandmark": {
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
        "upperBound": 0.9101007599814024,
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
