package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import lombok.Data;

@Data
public class LandmarkSegmentLength {
    public LandmarkSegmentToAxis from;
    public LandmarkSegmentToAxis to;
    public String id = "";
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;
    /*
       {
            "from": {
                "axis": "Y",
                "landmarkSegment": {
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
                }
            },
            "id": "D75134C8-B089-48FD-BD04-66F520AA5298",
            "lowerBound": -15.087248855544253,
            "to": {
                "axis": "X",
                "landmarkSegment": {
                    "color": {
                        "blue": 0.3490195870399475,
                        "green": 0.7803921699523926,
                        "red": 0.20392152667045593
                    },
                    "endLandmark": {
                        "color": {
                            "blue": 0.9999999403953552,
                            "green": 0.9999999403953552,
                            "red": 0.9999999403953552
                        },
                        "landmarkType": "RightAnkle",
                        "position": {
                            "x": 940.9599609375,
                            "y": 850.445068359375,
                            "z": 277.4242248535156
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
                        "landmarkType": "RightKnee",
                        "position": {
                            "x": 948.6396484375,
                            "y": 737.164794921875,
                            "z": -9.095199584960938
                        },
                        "selected": false
                    }
                }
            },
            "upperBound": -15.087248855544253,
            "warning": {
                "changeStateClear": false,
                "content": "",
                "delayTime": 2,
                "isScoreWarning": true,
                "triggeredWhenRuleMet": false
            }
        }
     */
}
