package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.landmarkd.LandmarkPositionArea;
import com.astra.actionconfig.config.data.state.WarningState;
import lombok.Data;

@Data
public class ObjectStateAngleRuler {
    public String extremeDirection = "MinX";
    public String id = "";
    public Boolean isRelativeToExtremeDirection = false;
    public double lowerBound = -3;
    public double upperBound = 0;
    public int toStateId = 0;
    public WarningState warning;

    public LandmarkPositionArea fromPosition;
    public LandmarkPositionArea toPosition;

    /*
    {
        "extremeDirection": "MinX",
        "fromPosition": {
            "axis": "X",
            "id": "rope",
            "point": {
                "x": 928.5,
                "y": 768.75
            },
            "position": "middle"
        },
        "id": "C0CF847D-B573-4A36-B5B9-D9133B855294",
        "isRelativeToExtremeDirection": true,
        "lowerBound": 0,
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
