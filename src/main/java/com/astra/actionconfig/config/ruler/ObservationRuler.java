package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.ruler.observationitem.ObjectLandmarkRuler;
import com.astra.actionconfig.config.ruler.observationitem.ObjectStateAngleRuler;
import com.astra.actionconfig.config.ruler.observationitem.ObjectStateDistanceRuler;
import lombok.Data;

import java.util.List;

@Data
public class ObservationRuler {
    public String id = "";
    public String objectLabel = "";
    public String ruleClass = "Observation";

    public List<ObjectLandmarkRuler> objectToLandmark;
    public List<ObjectLandmarkRuler> objectToObject;
    public List<ObjectStateAngleRuler> objectToStateAngle;

    public List<ObjectStateDistanceRuler> objectToStateDistance;

}
