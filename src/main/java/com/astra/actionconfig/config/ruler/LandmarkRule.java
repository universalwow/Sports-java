package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.landmarkd.*;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;


@Data
public class LandmarkRule {
    public LandmarkType landmarkType;
    public RuleClass ruleClass = RuleClass.Landmark;
    public String id = "";
    public List<LandmarkToStateDistance> landmarkToStateDistance = Lists.newArrayList();
    public List<LandmarkToStateAngle> landmarkToStateAngle = Lists.newArrayList();
    public List<DistanceToLandmark> distanceToLandmark = Lists.newArrayList();
    public List<AngleToLandmark> angleToLandmark = Lists.newArrayList();


    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {
        RuleSatisfyData distanceToLandmarkSatisfies =
                distanceToLandmark.stream().reduce(
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

        RuleSatisfyData angleToLandmarkSatisfies =
                angleToLandmark.stream().reduce(
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

        RuleSatisfyData landmarkToStateDistanceSatisfies =
                landmarkToStateDistance.stream().reduce(
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

        RuleSatisfyData landmarkToStateAngleSatisfies =
                landmarkToStateAngle.stream().reduce(
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

    public RuleSatisfyData allSatisfyWithRatio(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {

        RuleSatisfyData landmarkToStateDistanceSatisfies =
                landmarkToStateDistance.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0, new ArrayList<>()),
                        (result, next) -> {
                            SatisfyScore satisfyScore = next.satisfyWithRatio(stateTimeHistory, poseMap);
                            Set<Warning> newWarnings = result.warnings;
                            List<Double> scores = result.scores;


                            if (next.warning.triggeredWhenRuleMet && satisfyScore.satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfyScore.satisfy) {
                                newWarnings.add(next.warning);
                            }
                            scores.add(satisfyScore.score);
                            return new RuleSatisfyData(
                                    result.satisfy && satisfyScore.satisfy,
                                    newWarnings,
                                    satisfyScore.satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1, scores
                            );
                        }, (a, b) -> null

                );


        Set<Warning> warnings = new HashSet<>();

        warnings.addAll(landmarkToStateDistanceSatisfies.warnings);
        return new RuleSatisfyData(

                        landmarkToStateDistanceSatisfies.satisfy,
                warnings,

                        landmarkToStateDistanceSatisfies.pass,

                        landmarkToStateDistanceSatisfies.total, landmarkToStateDistanceSatisfies.scores);
    }

    public RuleSatisfyData allSatisfyWithWeight(List<StateTime> stateTimeHistory,
                                               Map<LandmarkType, Point3F> poseMap, Map<LandmarkType, Point3F> lastPoseMap,
                                               List<Observation> objects,
                                               Point2F frameSize) {

        RuleSatisfyData landmarkToStateDistanceSatisfies =
                landmarkToStateDistance.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0, new ArrayList<>()),
                        (result, next) -> {
                            SatisfyScore satisfyScore = next.satisfyWithWeight(stateTimeHistory, poseMap, lastPoseMap);
                            Set<Warning> newWarnings = result.warnings;
                            List<Double> scores = result.scores;


                            if (next.warning.triggeredWhenRuleMet && satisfyScore.satisfy) {
                                newWarnings.add(next.warning);
                            }else if (!next.warning.triggeredWhenRuleMet && !satisfyScore.satisfy) {
                                newWarnings.add(next.warning);
                            }
                            scores.add(satisfyScore.score);
                            return new RuleSatisfyData(
                                    result.satisfy && satisfyScore.satisfy,
                                    newWarnings,
                                    satisfyScore.satisfy ? result.pass + 1 : result.pass,
                                    result.total + 1, scores
                            );
                        }, (a, b) -> null

                );


        Set<Warning> warnings = new HashSet<>();

        warnings.addAll(landmarkToStateDistanceSatisfies.warnings);
        return new RuleSatisfyData(

                landmarkToStateDistanceSatisfies.satisfy,
                warnings,

                landmarkToStateDistanceSatisfies.pass,

                landmarkToStateDistanceSatisfies.total, landmarkToStateDistanceSatisfies.scores);
    }



}
