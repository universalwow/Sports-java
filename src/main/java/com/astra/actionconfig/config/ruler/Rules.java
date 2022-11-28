package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Rules {
    public String id = "";
    public String description = "";
    public List<DynamicAreaRule> dynamicAreaRules;  //此处应没数据，故猜测与fixedAreaRulers格式相同
    public List<FixedAreaRule> fixedAreaRules;

    //下面两个没有数据，格式为猜测
    public List<LandmarkRule> landmarkRules;
    public List<LandmarkSegmentRule> landmarkSegmentRules;

    //ObservationRuler未定义完全
    public List<ObservationRule> observationRules;


    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {

        RuleSatisfyData landmarkRulesSatisfies =
                landmarkRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);


                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total
                            );
                        }, (a, b) -> null

                );

        RuleSatisfyData landmarkSegmentRulesSatisfies =
                landmarkSegmentRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);


                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total
                            );
                        }, (a, b) -> null

                );

        RuleSatisfyData observationRulesRulesSatisfies =
                observationRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);


                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total
                            );
                        }, (a, b) -> null

                );

        RuleSatisfyData fixedAreaRulesSatisfies =
                fixedAreaRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);


                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total
                            );
                        }, (a, b) -> null

                );

        RuleSatisfyData dynamicAreaRulesSatisfies =
                dynamicAreaRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);


                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total
                            );
                        }, (a, b) -> null

                );

        Set<Warning> warnings = new HashSet<>();
        warnings.addAll(landmarkRulesSatisfies.warnings);
        warnings.addAll(landmarkSegmentRulesSatisfies.warnings);
        warnings.addAll(observationRulesRulesSatisfies.warnings);
        warnings.addAll(fixedAreaRulesSatisfies.warnings);
        warnings.addAll(dynamicAreaRulesSatisfies.warnings);
        return new RuleSatisfyData(
                landmarkRulesSatisfies.satisfy &&
                        landmarkSegmentRulesSatisfies.satisfy &&
                        observationRulesRulesSatisfies.satisfy &&
                        fixedAreaRulesSatisfies.satisfy &&
                        dynamicAreaRulesSatisfies.satisfy,
                warnings,
                landmarkRulesSatisfies.pass +
                        landmarkSegmentRulesSatisfies.pass +
                        observationRulesRulesSatisfies.pass +
                        fixedAreaRulesSatisfies.pass +
                        dynamicAreaRulesSatisfies.pass,
                landmarkRulesSatisfies.total +
                        landmarkSegmentRulesSatisfies.total +
                        observationRulesRulesSatisfies.total +
                        fixedAreaRulesSatisfies.total +
                        dynamicAreaRulesSatisfies.total);
    }

}
