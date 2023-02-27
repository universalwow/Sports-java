package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.Range;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Rules {
    public String id = "";
    public String description = "";
    public List<DynamicAreaRule> dynamicAreaRules = Lists.newArrayList(); //此处应没数据，故猜测与fixedAreaRulers格式相同
    public List<FixedAreaRule> fixedAreaRules = Lists.newArrayList();

    //下面两个没有数据，格式为猜测
    public List<LandmarkRule> landmarkRules = Lists.newArrayList();
    public List<LandmarkSegmentRule> landmarkSegmentRules = Lists.newArrayList();

    //ObservationRuler未定义完全
    public List<ObservationRule> observationRules = Lists.newArrayList();

    public Optional<List<LandmarkRule>> landmarkToStateDistanceMergeRules = Optional.of(Lists.newArrayList());

    public Optional<Double> mergeLowerBound = Optional.of(0.0);
    public Optional<Double> mergeUpperBound = Optional.of(0.0);

    public Optional<Double> weightLowerBound = Optional.of(0.0);
    public Optional<Double> weightUpperBound = Optional.of(0.0);


    public Range<Double> range() {
//        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa %f/%d", mergeLowerBound.get(), landmarkToStateDistanceMergeRules.get().size()));
        return Range.between(mergeLowerBound.get(), mergeUpperBound.get());
    }

    public Range<Double> weightRange() {
        return Range.between(weightLowerBound.get(), weightUpperBound.get());
    }

    public List<LandmarkRule> mergeRules() {
        return landmarkToStateDistanceMergeRules.orElse(Lists.newArrayList());
    }

    public List<LandmarkRule> mergeRulesToStateToggleOn() {
        return mergeRules().stream().filter(rules -> {
           return rules.landmarkToStateDistance.stream().filter(rule -> {
               return rule.toStateToggle.orElse(false);
           }).count() > 0;
        }).collect(Collectors.toList());
    }

    public List<LandmarkRule> mergeRulesToLastFrameToggleOn() {
        return mergeRules().stream().filter(rules -> {
            return rules.landmarkToStateDistance.stream().filter(rule -> {
                return rule.toLastFrameToggle.orElse(false);
            }).count() > 0;
        }).collect(Collectors.toList());
    }

    public void gnerateDynamicArea(String areaId, List<Point2F> area) {
        for (int i = 0; i < dynamicAreaRules.size(); i++) {
            dynamicAreaRules.get(i).generateDynamicArea(areaId, area);
        }
    }

    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap, Map<LandmarkType, Point3F> lastPoseMap,
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


        RuleSatisfyData landmarkToStateDistanceMergeRulesSatisfy = this.allSatisfyWithRatio(mergeRulesToStateToggleOn(), stateTimeHistory, poseMap, objects, frameSize);
        RuleSatisfyData landmarkToStateDistanceLastFrameRulesSatisfy = this.allSatisfyWithWeight(mergeRulesToLastFrameToggleOn(), stateTimeHistory, poseMap, lastPoseMap, objects, frameSize);

        Set<Warning> warnings = new HashSet<>();
        warnings.addAll(landmarkRulesSatisfies.warnings);
        warnings.addAll(landmarkSegmentRulesSatisfies.warnings);
        warnings.addAll(observationRulesRulesSatisfies.warnings);
        warnings.addAll(fixedAreaRulesSatisfies.warnings);
        warnings.addAll(dynamicAreaRulesSatisfies.warnings);
        warnings.addAll(landmarkToStateDistanceMergeRulesSatisfy.warnings);
        warnings.addAll(landmarkToStateDistanceLastFrameRulesSatisfy.warnings);

        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa %s/%s/%s", landmarkRulesSatisfies.satisfy, landmarkSegmentRulesSatisfies.satisfy, landmarkToStateDistanceMergeRulesSatisfy.satisfy));
        return new RuleSatisfyData(
                landmarkRulesSatisfies.satisfy &&
                        landmarkSegmentRulesSatisfies.satisfy &&
                        observationRulesRulesSatisfies.satisfy &&
                        fixedAreaRulesSatisfies.satisfy &&
                        dynamicAreaRulesSatisfies.satisfy && landmarkToStateDistanceMergeRulesSatisfy.satisfy&& landmarkToStateDistanceLastFrameRulesSatisfy.satisfy,
                warnings,
                landmarkRulesSatisfies.pass +
                        landmarkSegmentRulesSatisfies.pass +
                        observationRulesRulesSatisfies.pass +
                        fixedAreaRulesSatisfies.pass +
                        dynamicAreaRulesSatisfies.pass + landmarkToStateDistanceMergeRulesSatisfy.pass + landmarkToStateDistanceLastFrameRulesSatisfy.pass,
                landmarkRulesSatisfies.total +
                        landmarkSegmentRulesSatisfies.total +
                        observationRulesRulesSatisfies.total +
                        fixedAreaRulesSatisfies.total +
                        dynamicAreaRulesSatisfies.total + landmarkToStateDistanceMergeRulesSatisfy.total + landmarkToStateDistanceLastFrameRulesSatisfy.total);
    }



    public RuleSatisfyData allSatisfyWithRatio(List<LandmarkRule> landmarkToStateDistanceMegeRules, List<StateTime> stateTimeHistory,
                                       Map<LandmarkType, Point3F> poseMap,
                                       List<Observation> objects,
                                       Point2F frameSize) {

//        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaa %s", landmarkToStateDistanceMegeRules.size()));

        RuleSatisfyData landmarkRulesSatisfies =
                landmarkToStateDistanceMegeRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0, new ArrayList<>()),
                        (result, next) -> {
                            List<Double> scores = result.scores;
                            RuleSatisfyData satisfy = next.allSatisfyWithRatio(stateTimeHistory ,poseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);
                            scores.addAll(satisfy.scores);
                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total,
                                    scores

                            );
                        }, (a, b) -> null

                );



        Set<Warning> warnings = new HashSet<>();
        warnings.addAll(landmarkRulesSatisfies.warnings);


        double total = landmarkRulesSatisfies.scores.stream().reduce(0.0, (a, b) -> a + b);
        boolean mergeSatisfy = this.range().contains(total);
        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaaa merge %s/%s/%s %s %s total rule %s", mergeLowerBound.get(), mergeUpperBound.get(), total, mergeSatisfy, landmarkRulesSatisfies.scores, landmarkRulesSatisfies.total));

        if (landmarkRulesSatisfies.total == 0) {
            mergeSatisfy = true;
        }

        return new RuleSatisfyData(
                mergeSatisfy,
                warnings,
                landmarkRulesSatisfies.pass,
                landmarkRulesSatisfies.total, landmarkRulesSatisfies.scores);
    }

    public RuleSatisfyData allSatisfyWithWeight(List<LandmarkRule> landmarkToStateDistanceMegeRules, List<StateTime> stateTimeHistory,
                                               Map<LandmarkType, Point3F> poseMap, Map<LandmarkType, Point3F> lastPoseMap,
                                               List<Observation> objects,
                                               Point2F frameSize) {

//        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaa %s", landmarkToStateDistanceMegeRules.size()));

        RuleSatisfyData landmarkRulesSatisfies =
                landmarkToStateDistanceMegeRules.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0, new ArrayList<>()),
                        (result, next) -> {
                            List<Double> scores = result.scores;
                            RuleSatisfyData satisfy = next.allSatisfyWithWeight(stateTimeHistory ,poseMap, lastPoseMap, objects, frameSize);
                            Set<Warning> newWarnings = result.warnings;
                            newWarnings.addAll(satisfy.warnings);
                            scores.addAll(satisfy.scores);
                            return new RuleSatisfyData(
                                    result.satisfy && satisfy.satisfy,
                                    newWarnings,
                                    result.pass + satisfy.pass,
                                    result.total + satisfy.total,
                                    scores

                            );
                        }, (a, b) -> null

                );



        Set<Warning> warnings = new HashSet<>();
        warnings.addAll(landmarkRulesSatisfies.warnings);


        double total = landmarkRulesSatisfies.scores.stream().reduce(0.0, (a, b) -> a + b);
        boolean mergeSatisfy = this.weightRange().contains(total);
        System.out.println(String.format("aaaaaaaaaaaaaaaaaaaaaaa weight %s/%s/%s %s %s total rule %s", weightLowerBound.get(), weightUpperBound.get(), total, mergeSatisfy,  landmarkRulesSatisfies.scores, landmarkRulesSatisfies.total));

        if (landmarkRulesSatisfies.total == 0) {
            mergeSatisfy = true;
        }

        return new RuleSatisfyData(
                mergeSatisfy,
                warnings,
                landmarkRulesSatisfies.pass,
                landmarkRulesSatisfies.total, landmarkRulesSatisfies.scores);
    }


}
