package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import lombok.Data;

@Data
public class AngleToLandmarkSegment {
    public String id;
    public LandmarkSegment landmarkSegment;
    public double lowerBound = 0f;
    public double upperBound = 0;
    public Warning warning;
    /*
         "id": "03D11A27-9749-4297-939B-5A906F05714F",
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
        },
        "lowerBound": 175.8735932313021,
        "upperBound": 204,
        "warning": {
            "changeStateClear": false,
            "content": "",
            "delayTime": 2,
            "isScoreWarning": true,
            "triggeredWhenRuleMet": false
        }
     */
}
