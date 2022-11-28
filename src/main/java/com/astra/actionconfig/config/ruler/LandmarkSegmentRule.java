package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentType;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.landmarksegmentd.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class LandmarkSegmentRule {
    public LandmarkSegmentType landmarkSegmentType;
    public RuleClass ruleClass = RuleClass.LandmarkSegment;
    public String id = "";
    public List<AngleToLandmarkSegment> angleToLandmarkSegment;
    public List<LandmarkSegmentAngle> landmarkSegmentAngle;
    public List<LandmarkSegmentLength> landmarkSegmentLength;
    public List<LandmarkSegmentToStateAngle> landmarkSegmentToStateAngle;
    public List<LandmarkSegmentToStateDistance> landmarkSegmentToStateDistance;


    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {
        RuleSatisfyData landmarkSegmentLengthSatisfies =
                landmarkSegmentLength.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
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
                        }, (a, b) -> null

                );

        RuleSatisfyData landmarkSegmentAngleSatisfies =
                landmarkSegmentAngle.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
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
                        }, (a, b) -> null

                );

        RuleSatisfyData angleToLandmarkSegmentSatisfies =
                angleToLandmarkSegment.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
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
                        }, (a, b) -> null

                );

        RuleSatisfyData landmarkSegmentToStateDistanceSatisfies =
                landmarkSegmentToStateDistance.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
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
                        }, (a, b) -> null

                );

        RuleSatisfyData landmarkSegmentToStateAngleSatisfies =
                landmarkSegmentToStateAngle.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
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
                        }, (a, b) -> null

                );

        Set<Warning> warnings = new HashSet<>();
        warnings.addAll(landmarkSegmentLengthSatisfies.warnings);
        warnings.addAll(landmarkSegmentAngleSatisfies.warnings);
        warnings.addAll(angleToLandmarkSegmentSatisfies.warnings);
        warnings.addAll(landmarkSegmentToStateDistanceSatisfies.warnings);
        warnings.addAll(landmarkSegmentToStateAngleSatisfies.warnings);

        return new RuleSatisfyData(
                landmarkSegmentLengthSatisfies.satisfy &&
                        landmarkSegmentAngleSatisfies.satisfy &&
                        angleToLandmarkSegmentSatisfies.satisfy &&
                        landmarkSegmentToStateDistanceSatisfies.satisfy &&
                        landmarkSegmentToStateAngleSatisfies.satisfy,
                warnings,
                landmarkSegmentLengthSatisfies.pass +
                        landmarkSegmentAngleSatisfies.pass +
                        angleToLandmarkSegmentSatisfies.pass +
                        landmarkSegmentToStateDistanceSatisfies.pass +
                        landmarkSegmentToStateAngleSatisfies.pass,
                landmarkSegmentLengthSatisfies.total +
                        landmarkSegmentAngleSatisfies.total +
                        angleToLandmarkSegmentSatisfies.total +
                        landmarkSegmentToStateDistanceSatisfies.total +
                        landmarkSegmentToStateAngleSatisfies.total);




    }

}

