package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import lombok.Data;

@Data
public class LandmarkToStateAngle {
    public String id = "";
    public Boolean isRelativeToExtremeDirection = true;
    public String extremeDirection = "MinX";
    public double lowerBound = 0;
    public int toStateId = 0;
    public double upperBound = 0;
    public Landmark fromLandmark;
    public Landmark toLandmark;
    public Warning warning;

    /*
    {
    "extremeDirection": "MinX",
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
    "id": "AE931C69-D8EE-4CCC-997C-4A4B5721B99C",
    "isRelativeToExtremeDirection": true,
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
