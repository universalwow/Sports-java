package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkInArea;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


enum  RuleClass {
    LandmarkSegment, Landmark, Observation, FixedArea, DynamicArea
}

@Data
public class DynamicAreaRule {
    public String id; //
    public RuleClass ruleClass = RuleClass.DynamicArea;//
    public List<LandmarkInArea> landmarkInDynamicArea; //猜测

    public void generateDynamicArea(String areaId, List<Point2F> area) {
        for (int i = 0; i < landmarkInDynamicArea.size(); i++) {
            if (landmarkInDynamicArea.get(i).areaId == areaId) {
                landmarkInDynamicArea.get(i).area = area.toArray(new Point2F[area.size()]);
            }
        }
    }

    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {
        RuleSatisfyData landmarkInDynamicAreaSatisfies =
                landmarkInDynamicArea.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = next.satisfy(poseMap, frameSize);
                            Set<Warning> newWarnings = result.warnings;

                            if (next.warning.triggeredWhenRuleMet && satisfy) {
                                newWarnings.add(next.warning);
                            } else if (!next.warning.triggeredWhenRuleMet && !satisfy) {
                                newWarnings.add(next.warning);
                            }

                            return new RuleSatisfyData(
                                    result.satisfy && satisfy,
                                    newWarnings,
                                    satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1
                            );
                        }, (a, b) -> null

                );

        return landmarkInDynamicAreaSatisfies;
    }


}
