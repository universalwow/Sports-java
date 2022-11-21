package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import lombok.Data;

@Data
public class AngleToLandmark {
    public Landmark fromLandmark;
    public Landmark toLandmark;
    public String id;
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;
    /*
    {
        "fromLandmark": {
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
        "id": "B556710B-CFB5-478A-A7D8-3AD57A0B478C",
        "lowerBound": 0,
        "toLandmark": {
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
