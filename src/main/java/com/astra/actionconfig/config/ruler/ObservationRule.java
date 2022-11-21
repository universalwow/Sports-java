package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.ruler.observationitem.ObjectToObject;
import com.astra.actionconfig.config.ruler.observationitem.ObjectStateAngleRuler;
import com.astra.actionconfig.config.ruler.observationitem.ObjectStateDistanceRuler;
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

    public List<ObjectToObject> objectToLandmark;
    public List<ObjectToObject> objectToObject;
    public List<ObjectStateAngleRuler> objectToStateAngle;

    public List<ObjectStateDistanceRuler> objectToStateDistance;

}
