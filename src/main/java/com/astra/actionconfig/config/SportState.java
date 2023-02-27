package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.*;
import com.astra.actionconfig.config.data.landmarkd.HumanPose;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.RuleSatisfyData;
import com.astra.actionconfig.config.ruler.Rules;
import com.astra.actionconfig.config.ruler.StateTime;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;


@Data
public class SportState {
    public int id = -1;
    public String name = "";
    public String description = "";
    public PngImage image;
    public Optional<HumanPose> humanPose;
    public double checkCycle = 1;
    public int keepTime = 5;
    public double passingRate = 0.8f;
    public Optional<Integer> directToStateId = Optional.of(-100);
    public Optional<Double> transFormTimeLimit;

    public List<LandmarkSegment> landmarkSegments = Lists.newArrayList();

    public List<Observation> objects = Lists.newArrayList();
    public List<Rules> scoreRules = Lists.newArrayList();
    //下面两个没有数据
    public List<Rules> violateRules = Lists.newArrayList();

    public Optional<Boolean> timeCounterIsTimer = Optional.of(true);

    public SportState(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public SportState(){}

    public static SportState interAction_1() {
        return new SportState(-1, "interAction_1", "interAction_1");
    }

    public static SportState interAction_2() {
        return new SportState(-2, "提交答案", "提交答案");
    }

    public static SportState interAction_3() {
        return new SportState(-3, "interAction_3", "interAction_3");
    }

    public static SportState interAction_a() {
        return new SportState(0, "A", "A");
    }

    public static SportState interAction_b() {
        return new SportState(1, "B", "B");
    }

    public static SportState interAction_c() {
        return new SportState(2, "C", "C");
    }

    public static SportState interAction_d() {
        return new SportState(3, "D", "D");
    }

    public static SportState startState() {
        return new SportState(4, "Start", "start");
    }

    public static SportState endState() {
        return new SportState(5, "End", "end");
    }

    public static SportState readyState() {
        return new SportState(6, "Ready", "ready");
    }

    public Set<String> getFixedAreas() {
        Set areas = new HashSet<String>();

        scoreRules.forEach( rules -> {
            areas.addAll(
                    rules.fixedAreaRules.stream().map(fixedAreaRule -> {
                        return fixedAreaRule.id;
                    }).collect(Collectors.toList())
            );
        });

        violateRules.forEach( rules -> {
            areas.addAll(
                    rules.fixedAreaRules.stream().map(fixedAreaRule -> {
                        return fixedAreaRule.id;
                    }).collect(Collectors.toList())
            );
        });
        return areas;
    }

    public Set<String> getDynamicAreas() {
        Set areas = new HashSet<String>();

        scoreRules.forEach( rules -> {
            areas.addAll(
                    rules.dynamicAreaRules.stream().map(dynamicAreaRule -> {
                        return dynamicAreaRule.id;
                    }).collect(Collectors.toList())
            );
        });

        violateRules.forEach( rules -> {
            areas.addAll(
                    rules.fixedAreaRules.stream().map(dynamicAreaRule -> {
                        return dynamicAreaRule.id;
                    }).collect(Collectors.toList())
            );
        });
        return areas;
    }

    public void generateDynamicArea(String areaId, List<Point2F> area) {
        for (int i = 0; i < scoreRules.size(); i++) {
            scoreRules.get(i).gnerateDynamicArea(areaId, area);
        }

        for (int i = 0; i < violateRules.size(); i++) {
            violateRules.get(i).gnerateDynamicArea(areaId, area);
        }
    }


    public RuleSatisfyData rulesSatisfy(RuleType ruleType,
                                        List<StateTime> stateTimeHistory,
                                        Map<LandmarkType, Point3F> poseMap, Map<LandmarkType, Point3F> lastPoseMap,
                                        List<Observation> objects, Point2F frameSize) {

        List<Rules> rules = new ArrayList<>();

        switch (ruleType) {
            case SCORE:
                rules = scoreRules;
                break;
            case VIOLATE:
                rules = violateRules;
                break;
        }

        return
                rules.stream().reduce(
                        new RuleSatisfyData(false,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {

                            RuleSatisfyData satisfy = next.allSatisfy(stateTimeHistory ,poseMap, lastPoseMap, objects, frameSize);
                            Set warningSet =  new HashSet(satisfy.warnings.stream().peek(warning -> warning.isScoreWarning = Optional.of(ruleType == RuleType.SCORE)
                            ).collect(Collectors.toList()));

                            if (result.total == 0 && satisfy.total == 0) {
                                return new RuleSatisfyData(false, warningSet, satisfy.pass, satisfy.total);
                            } else if (result.total == 0 && satisfy.total !=0) {

                                return new RuleSatisfyData(result.satisfy || satisfy.satisfy,
                                                        warningSet,
                                        satisfy.pass, satisfy.total
                                        );
                            } else if (result.total != 0 && satisfy.total == 0) {
                                return  new RuleSatisfyData(false, result.warnings, result.pass, result.total);
                            } else {
                                double lastSatisfyPercent = (double)result.pass / result.total;
                                double currentSatisfyPercent = (double)satisfy.pass / satisfy.total;
                                if (currentSatisfyPercent > lastSatisfyPercent) {
                                    return new RuleSatisfyData(
                                            result.satisfy || satisfy.satisfy,
                                            warningSet, satisfy.pass, satisfy.total
                                    );
                                }else if (currentSatisfyPercent < lastSatisfyPercent) {
                                    return new RuleSatisfyData(
                                            result.satisfy || satisfy.satisfy,
                                            result.warnings, result.pass, result.total
                                    );
                                }else {
                                    warningSet.addAll(result.warnings);
                                    return new RuleSatisfyData(
                                            result.satisfy || satisfy.satisfy,
                                            warningSet, result.pass, result.total
                                    );
                                }
                            }
                        }, (a, b) -> null

                );



    }



}
