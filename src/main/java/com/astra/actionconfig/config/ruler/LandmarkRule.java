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
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
        RuleSatisfyData distanceToLandmarkSatisfies =
                distanceToLandmark.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<Warning>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = next.satisfy(poseMap);
                            Set<Warning> newWarnings = result.warnings;

                            if (next.warning.triggeredWhenRuleMet && satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfy) {
                                newWarnings.add(next.warning);
                            }

                            return new RuleSatisfyData(
                                    result.satisfy && satisfy,
                                    newWarnings,
                                    satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1
                            );
                        }, (a, b) -> {
                            return  null;
                        }

                );

        RuleSatisfyData angleToLandmarkSatisfies =
                angleToLandmark.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<Warning>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = next.satisfy(poseMap);
                            Set<Warning> newWarnings = result.warnings;

                            if (next.warning.triggeredWhenRuleMet && satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfy) {
                                newWarnings.add(next.warning);
                            }

                            return new RuleSatisfyData(
                                    result.satisfy && satisfy,
                                    newWarnings,
                                    satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1
                            );
                        }, (a, b) -> {
                            return  null;
                        }

                );

        RuleSatisfyData landmarkToStateDistanceSatisfies =
                landmarkToStateDistance.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<Warning>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = next.satisfy(stateTimeHistory, poseMap);
                            Set<Warning> newWarnings = result.warnings;

                            if (next.warning.triggeredWhenRuleMet && satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfy) {
                                newWarnings.add(next.warning);
                            }

                            return new RuleSatisfyData(
                                    result.satisfy && satisfy,
                                    newWarnings,
                                    satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1
                            );
                        }, (a, b) -> {
                            return  null;
                        }

                );

        RuleSatisfyData landmarkToStateAngleSatisfies =
                landmarkToStateAngle.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<Warning>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = next.satisfy(stateTimeHistory, poseMap);
                            Set<Warning> newWarnings = result.warnings;

                            if (next.warning.triggeredWhenRuleMet && satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfy) {
                                newWarnings.add(next.warning);
                            }

                            return new RuleSatisfyData(
                                    result.satisfy && satisfy,
                                    newWarnings,
                                    satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1
                            );
                        }, (a, b) -> {
                            return  null;
                        }

                );

        Set<Warning> warnings = new HashSet<Warning>();
        warnings.addAll(distanceToLandmarkSatisfies.warnings);
        warnings.addAll(angleToLandmarkSatisfies.warnings);
        warnings.addAll(landmarkToStateDistanceSatisfies.warnings);
        warnings.addAll(landmarkToStateAngleSatisfies.warnings);
        return new RuleSatisfyData(
                distanceToLandmarkSatisfies.satisfy &&
                        angleToLandmarkSatisfies.satisfy &&
                landmarkToStateDistanceSatisfies.satisfy &&
                landmarkToStateAngleSatisfies.satisfy,
                warnings,
                distanceToLandmarkSatisfies.pass +
                        angleToLandmarkSatisfies.pass +
                        landmarkToStateDistanceSatisfies.pass +
                        landmarkToStateAngleSatisfies.pass,
                distanceToLandmarkSatisfies.total +
                        angleToLandmarkSatisfies.total +
                        landmarkToStateDistanceSatisfies.total +
                        landmarkToStateAngleSatisfies.total);
    }



}
