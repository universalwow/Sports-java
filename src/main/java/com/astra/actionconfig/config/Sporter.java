package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.*;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.ExtremeObject;
import com.astra.actionconfig.config.ruler.ExtremePoint3D;
import com.astra.actionconfig.config.ruler.StateTime;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


class ScoreTime {
    int stateId;
    double time;
    boolean valid;
    Map<LandmarkType, Point3F> poseMap;
    Optional<Observation> object;

    public ScoreTime(int stateId, double time, boolean valid, Map<LandmarkType, Point3F> poseMap, Optional<Observation> object) {
        this.stateId = stateId;
        this.time = time;
        this.valid = valid;
        this.poseMap = poseMap;
        this.object = object;
    }

    public String id() {
        return String.format("%d-%f", stateId, time);
    }
}

class WarningData {
    Warning warning;
    int stateId;
    double time;

    public WarningData(Warning warning, int stateId, double time) {
        this.warning = warning;
        this.stateId = stateId;
        this.time = time;
    }

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

    public void currentStateTimeSetted() {
        allStateTimeHistory.add(
                new ScoreTime(currentStateTime.stateId, currentStateTime.time, true, currentStateTime.poseMap, currentStateTime.object)
        );

        Set<Warning> allCurrentFrameWarnings = new HashSet<>();
        sport.violateStateSequence.forEach( violateStates -> {
                if (stateTimeHistory.size() >= violateStates.stateIds.size()){
                    boolean allStateSatisfy = true;
                    for (int i = 0; i < violateStates.stateIds.size(); i++) {
                        boolean b = violateStates.stateIds.get(i) == stateTimeHistory.get(
                                i + stateTimeHistory.size() - violateStates.stateIds.size()
                        ).stateId;
                        allStateSatisfy = allStateSatisfy && b;
                    }

                    if (allStateSatisfy) {
                        allCurrentFrameWarnings.add(violateStates.warning);
                    }
                }
        });
        updateNoDelayWarnings(currentStateTime.time, allCurrentFrameWarnings);

        switch (sport.interactionType) {

            case None:
                break;
            case SingleChoice:
                if (currentStateTime.stateId == SportState.interAction_1().id) {
                    answerSet.clear();
                    this.question = Optional.ofNullable(sport.questions.get(new Random().nextInt(sport.questions.size())));
                } else if (currentStateTime.stateId == SportState.interAction_2().id) {
                    if (this.question.get().answerIndexs().equals(answerSet)) {
                        interactionScoreTimes.add(
                                new ScoreTime(currentStateTime.stateId, currentStateTime.time, true, currentStateTime.poseMap, currentStateTime.object)
                        );
                    }
                    this.question = null;
                    this.answerSet.clear();
                } else if (Lists.newArrayList(
                        SportState.interAction_a().id, SportState.interAction_b().id, SportState.interAction_c().id,
                        SportState.interAction_d().id).contains(currentStateTime.stateId)) {
                    if (answerSet.contains(currentStateTime.stateId)) {
                        answerSet.remove(currentStateTime.stateId);
                    }else{
                        this.answerSet.clear();
                        this.answerSet.add(currentStateTime.stateId);
                    }
                }

                break;
            case MultipleChoice:
                if (currentStateTime.stateId == SportState.interAction_1().id) {
                    this.answerSet.clear();
                    this.question = Optional.ofNullable(sport.questions.get(new Random().nextInt(sport.questions.size())));
                } else if (currentStateTime.stateId == SportState.interAction_2().id){
                    if (this.question.get().answerIndexs().equals(answerSet)) {
                        if (sport.sportClass == SportClass.Counter) {
                            interactionScoreTimes.add(
                                    new ScoreTime(currentStateTime.stateId, currentStateTime.time, true, currentStateTime.poseMap, currentStateTime.object)
                            );
                        }
                    }

                    this.question = null;
                    this.answerSet.clear();
                }  else if (Lists.newArrayList(
                        SportState.interAction_a().id, SportState.interAction_b().id, SportState.interAction_c().id,
                        SportState.interAction_d().id).contains(currentStateTime.stateId)) {
                    if (answerSet.contains(currentStateTime.stateId)) {
                        answerSet.remove(currentStateTime.stateId);
                    }else{
                        this.answerSet.add(currentStateTime.stateId);
                    }
                }
                break;
            case SingleTouch:
                getDynamicAreas();
                break;
            case OrdinalTouch:
                if (currentStateTime.stateId == SportState.interAction_1().id) {
                    this.answerSet.clear();
                    orderTouchStart = true;
                    getDynamicAreas();
                } else if (Lists.newArrayList(
                        SportState.interAction_a().id, SportState.interAction_b().id, SportState.interAction_c().id,
                        SportState.interAction_d().id).contains(currentStateTime.stateId)) {
                    this.answerSet.add(currentStateTime.stateId);
                    if (this.answerSet.size() == sport.dynamicAreaNumber){
                        if (sport.sportClass == SportClass.Counter) {
                            interactionScoreTimes.add(
                                    new ScoreTime(currentStateTime.stateId, currentStateTime.time, true, currentStateTime.poseMap, currentStateTime.object)
                            );
                        }
                        currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                        orderTouchStart =false;
                    }

                    if (this.answerSet.size() == 1) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            orderTouchStart =false;
                        }
                    } else if (this.answerSet.size() == 2) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id, SportState.interAction_b().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            orderTouchStart =false;
                        }
                    } else if (this.answerSet.size() == 3) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id, SportState.interAction_b().id, SportState.interAction_c().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            orderTouchStart =false;
                        }
                    }
                }
                break;
        }
        if (currentStateTime.stateId == SportState.startState().id) {
            stateTimeHistory.clear();
            stateTimeHistory.add(currentStateTime);
        } else {
            stateTimeHistory.add(currentStateTime);
            sport.scoreStateSequence.forEach( scoreStates -> {
                if (stateTimeHistory.size() >= scoreStates.size()) {
                    boolean allStateSatisfy = true;
                    for (int i = 0; i < scoreStates.size(); i++) {
                        boolean b = scoreStates.get(i) == stateTimeHistory.get(i + stateTimeHistory.size() - scoreStates.size()).stateId;
                        allStateSatisfy = allStateSatisfy && b;
                    }

                    if (allStateSatisfy) {
                        if (sport.sportClass == SportClass.Counter) {
                            scoreTimes.add(
                                    new ScoreTime(currentStateTime.stateId, currentStateTime.time,true, currentStateTime.poseMap, currentStateTime.object)
                            );
                        } else {
                            scoreTimes.addAll(timerScoreTimes);
                        }
                    }

                }
                }

            );


            sport.interactionScoreStateSequence.forEach( scoreStates -> {
                        if (stateTimeHistory.size() >= scoreStates.size()) {
                            boolean allStateSatisfy = true;
                            for (int i = 0; i < scoreStates.size(); i++) {
                                boolean b = scoreStates.get(i) == stateTimeHistory.get(i + stateTimeHistory.size() - scoreStates.size()).stateId;
                                allStateSatisfy = allStateSatisfy && b;
                            }

                            if (allStateSatisfy) {
                                if (sport.sportClass == SportClass.Counter) {
                                    interactionScoreTimes.add(
                                            new ScoreTime(currentStateTime.stateId, currentStateTime.time,true, currentStateTime.poseMap, currentStateTime.object)
                                    );
                                } else {
                                    interactionScoreTimes.addAll(timerScoreTimes);
                                }
                            }

                        }
                    }

            );

            timerScoreTimes.clear();
//            TODO: state change
            Optional<SportState> currentState = sport.findFirstStateByStateId(currentStateTime.stateId);

            if (currentState != null) {
                Optional<Integer> directToStateId = currentState.get().directToStateId;
                if (directToStateId != null && directToStateId.get() != SportState.endState().id && directToStateId.get() != -100) {
                    this.currentStateTime = new StateTime(
                            directToStateId.get(),
                            currentStateTime.time,
                            currentStateTime.poseMap,
                            currentStateTime.object,
                            currentStateTime.dynamicObjectsMaps,
                            currentStateTime.dynamicPoseMaps
                    );
                }
            }
        }
    }

    private void updateNoDelayWarnings(double time, Set<Warning> allCurrentFrameWarnings) {
        allCurrentFrameWarnings.forEach( warning -> {
            noDelayWarnings.add(warning);
            warningsData.add(new WarningData(warning, currentStateTime.stateId, time));
        }
        );
    }


    SportState nextState = SportState.startState();
    List<ScoreTime> scoreTimes = new ArrayList<>();

    private void scoreTimesSetted() {
        if (scoreTimes.isEmpty()) {
            return;
        }

        if (sport.interactionType != InteractionType.None &&
        scoreTimes.size() % sport.interactionScoreCycle.get() == 0) {
            ScoreTime lastScoreTime = scoreTimes.get(scoreTimes.size() -1);
            currentStateTime = new StateTime(
                    -1, lastScoreTime.time, lastScoreTime.poseMap, lastScoreTime.object
            );
        }
    }
    List<ScoreTime> interactionScoreTimes = new ArrayList<>();

    Set delayWarnings = new HashSet<Warning>();
    Set noDelayWarnings = new HashSet<Warning>();

    List<ScoreTime> timerScoreTimes = new ArrayList<>();

    private void timerScoreTimesSetted() {
        if (timerScoreTimes.isEmpty()) {
            return;
        }
        ScoreTime last_1 = timerScoreTimes.get(timerScoreTimes.size()-1);
        SportState state = sport.findFirstStateByStateId(last_1.stateId).get();

        if (sport.sportDiscrete == SportPeriod.Continuous && timerScoreTimes.size() > 1) {
            ScoreTime last_2 = timerScoreTimes.get(timerScoreTimes.size() -2);
            if (last_2.stateId == last_1.stateId) {
                if (last_1.time - last_2.time > state.checkCycle + 0.5) {
                    timerScoreTimes.clear();
                    timerScoreTimes.add(last_1);
                }
            } else {
                timerScoreTimes.clear();
                timerScoreTimes.add(last_1);
            }
        } else if (sport.sportDiscrete == SportPeriod.Discrete && timerScoreTimes.size() > 1) {
            last_1 = timerScoreTimes.get(timerScoreTimes.size()-1);
            ScoreTime last_2 = timerScoreTimes.get(timerScoreTimes.size() -2);
            if (last_2.stateId != last_1.stateId) {
                timerScoreTimes.clear();
                timerScoreTimes.add(last_1);
            }

        }

        if (timerScoreTimes.size() == state.keepTime) {
            currentStateTime = new StateTime(state.id, last_1.time, last_1.poseMap, last_1.object);
        }
    }

    List<StateTime> stateTimeHistory = Lists.newArrayList(new StateTime(SportState.startState().id, 0,
            new HashMap<>(), null));

    Map<Warning, Timer> cancelableWarningMap = new HashMap<>();

    List<WarningData> warningsData = new ArrayList<>();

    class WarningTimerTask extends TimerTask {
        WarningData warning;
        Timer timer;

        public WarningTimerTask(Timer timer, WarningData warning) {
            this.timer = timer;
            this.warning = warning;
        }

        @Override
        public void run() {
            delayWarnings.add(warning.warning);
            warningsData.add(warning);
            this.cancel();
            this.timer.cancel();

        }

    }

    public Timer warningTimer(WarningData warning) {
        Timer timer = new Timer();
        TimerTask task =new WarningTimerTask(timer, warning);
        timer.schedule(task, (long) (warning.warning.delayTime * 1000));
        return new Timer();
    }

    Map<String, List<Boolean>> inCheckingStateHistory = new HashMap<>();
    Map<String, Timer> inCheckingStatesTimer = new HashMap<>();

    class CheckStateTimerTask extends TimerTask {
        Timer timer;
        SportState state;
        double time;
        Map<LandmarkType, Point3F> poseMap;
        Optional<Observation> object;

        public CheckStateTimerTask(Timer timer, SportState state, double currentTime, Map<LandmarkType, Point3F> poseMap, Optional<Observation> object) {
            this.timer = timer;
            this.state = state;
            this.time = currentTime;
            this.poseMap = poseMap;
            this.object = object;
        }

        @Override
        public void run() {

            if (inCheckingStateHistory.keySet().contains(state.name)) {
                AtomicInteger total = new AtomicInteger();
                AtomicInteger pass = new AtomicInteger();

                inCheckingStateHistory.get(state.name).forEach(flag -> {
                    total.addAndGet(1);
                    pass.addAndGet((flag ? 1 : 0));
                });

                if (total.get() > 0 && (float)pass.get()/total.get() > state.passingRate) {
                    timerScoreTimes.add(
                            new ScoreTime(state.id, time, true, poseMap, object)
                    );
                    nextStatePreview = state;

                }
                this.cancel();
                timer.cancel();
                inCheckingStateHistory.remove(state.name);
                inCheckingStatesTimer.remove(state.name);
            }
        }
    }

    private Timer checkStateTimer(SportState state, double currentTime, double delay, Map<LandmarkType,Point3F> poseMap, Optional<Observation> object) {
        Timer timer = new Timer();
        CheckStateTimerTask task = new CheckStateTimerTask(timer, state, currentTime, poseMap, object);
        timer.schedule(task, (long) (delay * 1000));
        return timer;


    }

    private void updateCurrentStateObjectBounds(List<Observation> objects, List<String> objectLabels) {
        if (stateTimeHistory.isEmpty()) {
            return;
        }
        int index = stateTimeHistory.size() -1;

        objectLabels.forEach(objectLabel -> {
            Optional<Observation> collectedObject = objects.stream().filter(object -> {
                return object.label == objectLabel;
            }).findFirst();

            if (collectedObject != null) {
                Observation collectedObject_ = collectedObject.get();
                if (!stateTimeHistory.get(index).dynamicObjectsMaps.containsKey(objectLabel)) {
                    stateTimeHistory.get(index).dynamicObjectsMaps.put(objectLabel, new ExtremeObject(collectedObject_));
                } else {
                    ExtremeObject object = stateTimeHistory.get(index).dynamicObjectsMaps.get(objectLabel);
                    if (collectedObject_.rectangle().getCenterX() < object.minX.rectangle().getCenterX()) {
                        object.minX = collectedObject_;
                    }

                    if (collectedObject_.rectangle().getCenterX() > object.maxX.rectangle().getCenterX()) {
                        object.maxX = collectedObject_;
                    }

                    if (collectedObject_.rectangle().getCenterY() < object.minY.rectangle().getCenterY()) {
                        object.minY = collectedObject_;
                    }

                    if (collectedObject_.rectangle().getCenterY() > object.maxY.rectangle().getCenterY()) {
                        object.maxY = collectedObject_;
                    }
                }
            }
        });
    }

    private void updateCurrentStateLandmarkBounds(Map<LandmarkType, Point3F> poseMap, List<LandmarkType> landmarkTypes) {
        if (stateTimeHistory.isEmpty()) {
            return;
        }
        int index = stateTimeHistory.size() -1;

        landmarkTypes.forEach(landmarkType -> {
            Point3F point = poseMap.get(landmarkType);

            if (!stateTimeHistory.get(index).dynamicPoseMaps.containsKey(landmarkType)) {
                stateTimeHistory.get(index).dynamicPoseMaps.put(landmarkType, new ExtremePoint3D(point));
            }else {
                ExtremePoint3D object = stateTimeHistory.get(index).dynamicPoseMaps.get(landmarkType);
                if (point.x < object.minX.x) {
                    object.minX = point;
                }

                if (point.x > object.maxX.x) {
                    object.maxX = point;
                }

                if (point.y < object.minY.y) {
                    object.minY = point;
                }

                if (point.y > object.maxY.y) {
                    object.maxY = point;
                }
            }

        });


    }



    double lastTime = 0;




}
