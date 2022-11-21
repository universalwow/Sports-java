package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import com.astra.actionconfig.config.data.state.WarningState;
import lombok.Data;

@Data
public class LandmarkDistanceRuler {
    public LandmarkSegmentToAxis from;
    public LandmarkSegmentToAxis to;
    public String id = "";
    public double lowerBound = 0;
    public double upperBound = 0;
    public WarningState warning;
    /*
    {
        "from": {
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
                    "landmarkType": "LeftAnkle",
                    "position": {
                        "x": 895.2235107421875,
                        "y": 848.5889892578125,
                        "z": 283.5280456542969
                    },
                    "selected": true
                },
                "selected": false,
                "startLandmark": {
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
            }
        },
        "id": "D0F07AD7-1E29-40EA-A52A-A5BE6B450467",
        "lowerBound": 0,
        "to": {
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
