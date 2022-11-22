package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.ObjectPositionPoint;
import lombok.Data;


enum ExtremeDirection {
    MinX, MinY,
    MaxX, MaxY,
    MinX_MinY, MinX_MaxY,
    MaxX_MinY, MaxX_MaxY
}

@Data
public class ObjectToStateAngle {
    public ExtremeDirection extremeDirection;
    public String id;
    public Boolean isRelativeToExtremeDirection;
    public double lowerBound;
    public double upperBound;
    public int toStateId;
    public Warning warning;

    public ObjectPositionPoint fromPosition;
    public ObjectPositionPoint toPosition;

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
