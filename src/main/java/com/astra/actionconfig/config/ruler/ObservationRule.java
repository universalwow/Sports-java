package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.ruler.observationitem.ObjectToLandmark;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToObject;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToStateAngle;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToStateDistance;
import lombok.Data;

import java.util.List;



enum ObjectLabel {
    ROPE, POSE, BASKETBALL
}

@Data
public class ObservationRule {
    public String id = "";
    public ObjectLabel objectLabel;
    public RuleClass ruleClass = RuleClass.Observation;

    public List<ObjectToLandmark> objectToLandmark;
    public List<ObjectToObject> objectToObject;
    public List<ObjectToStateAngle> objectToStateAngle;

    public List<ObjectToStateDistance> objectToStateDistance;

}
