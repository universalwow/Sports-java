package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.landmarkd.AngleToLandmark;
import com.astra.actionconfig.config.ruler.landmarkd.DistanceToLandmark;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkToStateAngle;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkToStateDistance;
import lombok.Data;

import java.util.List;

@Data
public class LandmarkRule {
    public LandmarkType landmarkType;
    public RuleClass ruleClass = RuleClass.Landmark;
    public String id = "";
    public List<LandmarkToStateDistance> landmarkToStateDistance;
    public List<LandmarkToStateAngle> landmarkToStateAngle;
    public List<DistanceToLandmark> distanceToLandmark;
    public List<AngleToLandmark> angleToLandmark;
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
