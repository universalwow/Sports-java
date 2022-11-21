package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import lombok.Data;

@Data
public class LandmarkSegmentAngle {
    public LandmarkSegment from;
    public LandmarkSegment to;
    public String id;
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;
    /*
    "from": {
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
    "id": "CD8CE187-5799-4D5C-A1C1-3E77988BAB3A",
    "lowerBound": -1.5098304521526416,
    "to": {
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
            "landmarkType": "LeftKnee",
            "position": {
                "x": 886.864501953125,
                "y": 732.7236328125,
                "z": -2.663571834564209
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
            "landmarkType": "LeftHip",
            "position": {
                "x": 880.8971557617188,
                "y": 602.1461181640625,
                "z": 3.87355375289917
            },
            "selected": false
        }
    },
    "upperBound": -1.5098304521526416,
    "warning": {
        "changeStateClear": false,
        "content": "",
        "delayTime": 2,
        "isScoreWarning": true,
        "triggeredWhenRuleMet": false
    }

     */
}
