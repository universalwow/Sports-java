package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.*;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.StateTime;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;


class ScoreTime {
    int stateId;
    double time;
    boolean valid;
    Map<LandmarkType, Point3F> poseMap;
    Optional<Observation> object;

    public String id() {
        return String.format("%d-%f", stateId, time);
    }
}

class WarningData {
    Warning warning;
    int stateId;
    double time;

    public String id() {
        return String.format("%d-%f-%s", stateId, warning.content, time);
    }
}

public class Sporter {
    UUID id = UUID.randomUUID();
    String name;
    Sport sport;

    public Sporter(String name, Sport sport) {
        this.name = name;
        this.sport = sport;

    }

    public void clearTimer() {
        cancelableWarningMap.keySet().forEach(warning -> {
            cancelableWarningMap.get(warning).cancel();
            cancelableWarningMap.remove(warning);
        }
        );

        inCheckingStatesTimer.keySet().forEach(key -> {
            inCheckingStatesTimer.get(key).cancel();
            inCheckingStatesTimer.remove(key);
        });
    }

    public List<FixedArea> getFixedAreas() {
        Set areas = new HashSet<String>();
        sport.stateTransForm.stream().filter(transform -> {
           return transform.from == currentStateTime.stateId;
        }).map(transform -> {
            return transform.to;
        }).forEach(stateId -> {
            SportState state = sport.states.stream().filter(s -> {
                return s.id == stateId;
            }).findFirst().get();
            areas.addAll(state.getFixedAreas());
        });

        if (Lists.newArrayList(InteractionType.MultipleChoice,
                InteractionType.SingleChoice).contains(sport.interactionType) &&
                question != null) {
            List<FixedArea> fixedAreas = new ArrayList();
            for (int i = 0; i < question.get().choices.size(); i++) {
                if (this.answerSet.contains(i)) {
                    sport.fixedAreas.get(i).selected = Optional.of(true);
                } else {
                    sport.fixedAreas.get(i).selected = Optional.of(false);
                }
                sport.fixedAreas.get(i).content = question.get().choices.get(i);
                fixedAreas.add(sport.fixedAreas.get(i));

            }
            fixedAreas.add(sport.fixedAreas.get(4));
            return fixedAreas;


        }
        return sport.fixedAreas.stream().filter(area -> {
            return areas.contains(area.id);
        }).collect(Collectors.toList());

    }

    public List<DynamicArea> getDynamicAreas() {

        Set areas = new HashSet<String>();
        sport.stateTransForm.stream().filter(transform -> {
            return transform.from == currentStateTime.stateId;
        }).map(transform -> {
            return transform.to;
        }).forEach(stateId -> {
            SportState state = sport.states.stream().filter(s -> {
                return s.id == stateId;
            }).findFirst().get();
            areas.addAll(state.getDynamicAreas());
        });

        if (Lists.newArrayList(InteractionType.OrdinalTouch).contains(sport.interactionType) &&
                orderTouchStart) {
            List<DynamicArea> dynamicAreas = new ArrayList();
            for (int i = 0; i < sport.dynamicAreaNumber; i++) {
                sport.dynamicAreas.get(i).content = String.format("%d", i +1);

                if (this.answerSet.contains(i)) {
                    sport.fixedAreas.get(i).selected = Optional.of(true);
                } else {
                    sport.fixedAreas.get(i).selected = Optional.of(false);
                }
                dynamicAreas.add(sport.dynamicAreas.get(i));

            }
            return dynamicAreas;


        }
        return sport.dynamicAreas.stream().filter(area -> {
            return areas.contains(area.id);
        }).collect(Collectors.toList());

    }

    public void generateDynamicArea() {
        Set<String> areas = new HashSet<>();
        List<Integer> stateIds = sport.stateTransForm.stream().filter(transform ->
                    transform.from == currentStateTime.stateId
                ).map(transform -> {
                    return transform.to;
        }).collect(Collectors.toList());

        stateIds.forEach(stateId -> {
            SportState state = sport.states.stream().filter(s -> {
                return s.id == stateId;
            }).findFirst().get();

            areas.addAll(state.getDynamicAreas());
        });

        areas.forEach(areaId -> {
            DynamicArea dynamicArea = sport.dynamicAreas.stream().filter(area -> {
                return area.id == areaId;
            }).findFirst().get();
//            TODO: -------------------------------------------


            List<Point2F> area = sport.generateDynamicArea(dynamicArea.imageSize.get(), areaId);
            sport.updateDynamicArea(areaId,area);
            sport.generateDynamicArea(areaId,area);
        });
    }

    List<ScoreTime> allStateTimeHistory = new ArrayList<ScoreTime>();
    SportState nextStatePreview = SportState.startState();
    public Optional<Question> question;
    public Set<Integer> answerSet = new HashSet<Integer>();
    public boolean orderTouchStart = false;

    StateTime currentStateTime = new StateTime(SportState.startState().id, 0,
            new HashMap<LandmarkType, Point3F>(), null);
    SportState nextState = SportState.startState();
    List<ScoreTime> scoreTimes = new ArrayList<>();
    List<ScoreTime> interactionScoreTimes = new ArrayList<>();

    Set delayWarnings = new HashSet<Warning>();
    Set noDelayWarnings = new HashSet<Warning>();

    List<ScoreTime> timerScoreTimes = new ArrayList<>();

    List<StateTime> stateTimeHistory = Lists.newArrayList(new StateTime(SportState.startState().id, 0,
            new HashMap<>(), null));

    Map<Warning, Timer> cancelableWarningMap = new HashMap<>();

    List<WarningData> warningsData = new ArrayList<>();

    public Timer warningTimer(WarningData warning) {
        return new Timer();
    }

    Map<String, List<Boolean>> inCheckingStateHistory = new HashMap<>();
    Map<String, Timer> inCheckingStatesTimer = new HashMap<>();

    double lastTime = 0;













}
