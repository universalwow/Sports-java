package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.landmarkd.AngleToLandmark;
import com.astra.actionconfig.config.ruler.landmarkd.DistanceToLandmark;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkToStateAngle;
import com.astra.actionconfig.config.ruler.landmarkd.LandmarkToStateDistance;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class RuleSatisfyData {
    public boolean satisfy;
    public Set<Warning> warnings;
    public int pass;
    public int total;

    public RuleSatisfyData(boolean satisfy, HashSet<Warning> warnings, int pass, int total) {
        this.satisfy = satisfy;
        this.warnings = warnings;
        this.pass = pass;
        this.total = total;

    }
}




@Data
public class LandmarkRule {
    public LandmarkType landmarkType;
    public RuleClass ruleClass = RuleClass.Landmark;
    public String id = "";
    public List<LandmarkToStateDistance> landmarkToStateDistance;
    public List<LandmarkToStateAngle> landmarkToStateAngle;
    public List<DistanceToLandmark> distanceToLandmark;
    public List<AngleToLandmark> angleToLandmark;


    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {



        return new RuleSatisfyData(false, new HashSet<Warning>(), 0 ,0);
    }



}
