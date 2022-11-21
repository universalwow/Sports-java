package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.ruler.landmarkd.LandmarkAngleRuler;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkDistanceRuler;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkStateAngleRuler;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkStateDistanceRuler;
import lombok.Data;

import java.util.List;

@Data
public class LandmarkRuler {
    public String landmarkType = "";
    public String ruleClass = "";
    public String id = "";
    public List<LandmarkStateDistanceRuler> landmarkToStateDistance;
    public List<LandmarkStateAngleRuler> landmarkToStateAngle;
    public List<LandmarkDistanceRuler> distanceToLandmark;
    public List<LandmarkAngleRuler> angleToLandmark;
    /*
        "landmarkType": "LeftAnkle",
        "ruleClass": "Landmark"
        "landmarkToStateDistance": [
        "landmarkToStateAngle": [
        "id": "LeftAnkle",
        "distanceToLandmark": [
        "angleToLandmark": [
     */
}
