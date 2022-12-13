package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkInArea;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class FixedAreaRule {
    public String id = ""; //
    public RuleClass ruleClass = RuleClass.FixedArea;//
    public List<LandmarkInArea> landmarkInFixedArea = Lists.newArrayList();

    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {
        RuleSatisfyData landmarkInFixedAreaSatisfies =
                landmarkInFixedArea.stream().reduce(
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

        return landmarkInFixedAreaSatisfies;
    }


}
