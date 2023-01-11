package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.*;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.data.state.SportStateTransform;
import com.astra.actionconfig.config.ruler.ExtremeObject;
import com.astra.actionconfig.config.ruler.ExtremePoint3D;
import com.astra.actionconfig.config.ruler.RuleSatisfyData;
import com.astra.actionconfig.config.ruler.StateTime;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class ScoreTime {
    int stateId;
    double time;
    boolean valid;
    Map<LandmarkType, Point3F> poseMap = new EnumMap<>(LandmarkType.class);
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

@Data
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
    public String name;
    public Sport sport;

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
                !question.equals(Optional.empty())) {
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
    public SportState nextStatePreview = SportState.startState();
    public Optional<Question> question;
    public Set<Integer> answerSet = new HashSet<Integer>();
    public boolean orderTouchStart = false;

    StateTime currentStateTime = new StateTime(SportState.startState().id, 0,
            new EnumMap<LandmarkType, Point3F>(LandmarkType.class), Optional.empty());

    public SportState getCurrentState() {

        return sport.findFirstStateByStateId(currentStateTime.stateId).get();
    }

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
                    this.question = Optional.empty();
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

                    this.question = Optional.empty();
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
                        currentStateTimeSetted();
                        orderTouchStart =false;
                    }

                    if (this.answerSet.size() == 1) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            currentStateTimeSetted();
                            orderTouchStart =false;
                        }
                    } else if (this.answerSet.size() == 2) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id, SportState.interAction_b().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            currentStateTimeSetted();
                            orderTouchStart =false;
                        }
                    } else if (this.answerSet.size() == 3) {
                        if (!this.answerSet.equals(Sets.newHashSet(SportState.interAction_a().id, SportState.interAction_b().id, SportState.interAction_c().id))){
                            currentStateTime = new StateTime(SportState.startState().id, currentStateTime.time, currentStateTime.poseMap, currentStateTime.object);
                            currentStateTimeSetted();
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
                            scoreTimesSetted();
                        } else {
                            scoreTimes.addAll(timerScoreTimes);
                            scoreTimesSetted();
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

            if (!currentState.equals(Optional.empty())) {
                Optional<Integer> directToStateId = currentState.get().directToStateId;
                if (!directToStateId.equals(Optional.empty()) && directToStateId.get() != SportState.endState().id && directToStateId.get() != -100) {
                    this.currentStateTime = new StateTime(
                            directToStateId.get(),
                            currentStateTime.time,
                            currentStateTime.poseMap,
                            currentStateTime.object,
                            currentStateTime.dynamicObjectsMaps,
                            currentStateTime.dynamicPoseMaps
                    );
                    currentStateTimeSetted();

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
    public List<ScoreTime> scoreTimes = new ArrayList<>();

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
            currentStateTimeSetted();
        }
    }
    List<ScoreTime> interactionScoreTimes = new ArrayList<>();

    Set<Warning> delayWarnings = Collections.synchronizedSet(new HashSet<Warning>());
    Set noDelayWarnings = Collections.synchronizedSet(new HashSet<Warning>());

    public List<ScoreTime> timerScoreTimes = new ArrayList<>();

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
                    timerScoreTimesSetted();
                }
            } else {
                timerScoreTimes.clear();
                timerScoreTimes.add(last_1);
                timerScoreTimesSetted();
            }
        } else if (sport.sportDiscrete == SportPeriod.Discrete && timerScoreTimes.size() > 1) {
            last_1 = timerScoreTimes.get(timerScoreTimes.size()-1);
            ScoreTime last_2 = timerScoreTimes.get(timerScoreTimes.size() -2);
            if (last_2.stateId != last_1.stateId) {
                timerScoreTimes.clear();
                timerScoreTimes.add(last_1);
                timerScoreTimesSetted();
            }

        }

        if (timerScoreTimes.size() == state.keepTime) {
            currentStateTime = new StateTime(state.id, last_1.time, last_1.poseMap, last_1.object);
            currentStateTimeSetted();

        }
    }

    List<StateTime> stateTimeHistory = Lists.newArrayList(new StateTime(SportState.startState().id, 0,
            new HashMap<>(), Optional.empty()));

    Map<Warning, Timer> cancelableWarningMap = Collections.synchronizedMap(new HashMap<>());

    List<WarningData> warningsData = Collections.synchronizedList(new ArrayList<>());

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
//            System.out.println(String.format("warning------------------ %s", warning.warning.content));
            warningsData.add(warning);
            this.cancel();
            this.timer.cancel();
            this.timer.purge();
            cancelableWarningMap.remove(warning.warning);

        }

    }

    public Timer warningTimer(WarningData warning) {
        Timer timer = new Timer();
        TimerTask task =new WarningTimerTask(timer, warning);
        timer.schedule(task, (long) (warning.warning.delayTime * 1000));
        return new Timer();
    }

    Map<String, List<Boolean>> inCheckingStateHistory = Collections.synchronizedMap( new HashMap<>());
    Map<String, Timer> inCheckingStatesTimer = Collections.synchronizedMap( new HashMap<>());

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
                    timerScoreTimesSetted();
                    nextStatePreview = state;

                }
                this.cancel();
                timer.cancel();
                this.timer.purge();
                inCheckingStateHistory.remove(state.name);
                inCheckingStatesTimer.remove(state.name);
            }
        }
    }

    private Timer checkStateTimer(SportState state, double currentTime, double delay, Map<LandmarkType,Point3F> poseMap, Optional<Observation> object) {
        Timer timer = new Timer();
        CheckStateTimerTask task = new CheckStateTimerTask(timer, state, currentTime, poseMap, object);
        System.out.println(String.format("delay %d", (long) (delay * 1000)));
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

            if (!collectedObject.equals(Optional.empty())) {
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

    private void updateWarnings(double currentTime, Set<Warning> allCurrentFrameWarnings){
        synchronized (cancelableWarningMap) {
            allCurrentFrameWarnings.forEach(warning -> {
                if (warning.delayTime < 0.3) {
                    noDelayWarnings.add(warning);
                    warningsData.add(new WarningData(warning, currentStateTime.stateId, currentTime));
                }
            });

            List<WarningData> totalWarnings = allCurrentFrameWarnings.stream().map(warning -> {
                return new WarningData(warning, currentStateTime.stateId, currentTime);
            }).collect(Collectors.toList());
//不在当前提示中，全部删除
            List<Warning> cancelWarnings = delayWarnings.stream().filter(warning -> {
                return !totalWarnings.stream().map(newWarning -> {
                    return newWarning.warning.content;
                }).collect(Collectors.toList()).contains(warning.content);
            }).collect(Collectors.toList());
//不在当前提示中，全部删除
            List<Warning> cancelWarnings1 = cancelableWarningMap.keySet().stream().filter(warning -> {
                return !totalWarnings.stream().map(newWarning -> {
                    return newWarning.warning.content;
                }).collect(Collectors.toList()).contains(warning.content);
            }).collect(Collectors.toList());
            System.out.println(String.format("-delayWarnings %s/%s", totalWarnings, cancelableWarningMap.keySet()));

            cancelWarnings.forEach(warning -> {
                if (cancelableWarningMap.containsKey(warning)) {
                    Timer timer = cancelableWarningMap.get(warning);
                    timer.cancel();
                    timer.purge();
                    cancelableWarningMap.remove(warning);
                }
            });

            cancelWarnings1.forEach(warning -> {
                if (cancelableWarningMap.containsKey(warning)) {
                    Timer timer = cancelableWarningMap.get(warning);
                    timer.cancel();
                    timer.purge();
                    cancelableWarningMap.remove(warning);
                }
            });

            System.out.println(String.format("delayWarnings %s/%s", this.delayWarnings, cancelWarnings));

            System.out.println(String.format("delayWarnings %s/%s", this.delayWarnings.size(), cancelWarnings.size()));
            this.delayWarnings.removeAll(cancelWarnings);
            this.delayWarnings.removeAll(cancelWarnings1);

            System.out.println(String.format("delayWarnings * %s", this.delayWarnings.size()));


            totalWarnings.stream().filter(warningData -> {
                return  !cancelableWarningMap.keySet().stream().map(warning -> {
                    return warning.content;
                }).collect(Collectors.toList()).contains(warningData.warning.content);

            }).forEach(warningData -> {
                if (warningData.warning.delayTime >= 0.3) {
                    cancelableWarningMap.put(warningData.warning, warningTimer(warningData));
                }
            });
        }




    }

    public void play(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {
        switch (sport.sportClass) {

            case Counter:
                playCounter(poseMap, objects, frameSize, currentTime);
                break;
            case Timer:
                playTimer(poseMap, objects, frameSize, currentTime);
                break;
            case TimeCounter:
                playTimeCounter(poseMap, objects, frameSize, currentTime);
                break;
            case None:
                break;
        }

    }
    private void playCounter(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {
        if (lastTime > currentTime) {
            return;
        }

        if (!sport.selectedLandmarkTypes.isEmpty()) {
            updateCurrentStateLandmarkBounds(poseMap, sport.selectedLandmarkTypes);
        }
        if (!sport.collectedObjects.isEmpty()) {
            updateCurrentStateObjectBounds(objects, sport.collectedObjects);
        }

        Set<Warning> allCurrentFrameWarnings = new HashSet<>();

        if (currentStateTime.time > 1 && currentTime - currentStateTime.time > sport.scoreTimeLimit) {
            currentStateTime = new StateTime(SportState.startState().id, currentTime, poseMap, Optional.empty());
            currentStateTimeSetted();

            allCurrentFrameWarnings.add(new Warning("状态变换间隔太久", true, 0));
        }

        List<SportStateTransform> transforms = sport.stateTransForm.stream().filter(transform -> currentStateTime.stateId == transform.from).collect(Collectors.toList());
        List<RuleSatisfyData> violateRulesTransformSatisfy = transforms.stream().map( transform -> {
            Optional<SportState> toState = sport.findFirstStateByStateId(transform.to);
            if (!toState.equals(Optional.empty())) {
                RuleSatisfyData satisfy = toState.get().rulesSatisfy(RuleType.VIOLATE, stateTimeHistory, poseMap, objects, frameSize);
                return satisfy;
            }
            return new RuleSatisfyData(false, new HashSet<>(), 0 ,0);
        }).collect(Collectors.toList());

        if (violateRulesTransformSatisfy.size() == 1) {
            allCurrentFrameWarnings.addAll(violateRulesTransformSatisfy.get(0).warnings);
        } else if (violateRulesTransformSatisfy.size() > 1) {
            List<RuleSatisfyData> allRulesSatisfySorted = violateRulesTransformSatisfy.stream().
                    sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());
            if (allRulesSatisfySorted.size() > 1){
                RuleSatisfyData first = allRulesSatisfySorted.get(0);
                RuleSatisfyData second = allRulesSatisfySorted.get(1);
//                TODO:　校验排序
                if (!first.satisfy && first.pass > second.pass) {
                    allCurrentFrameWarnings.addAll(first.warnings);
                }

            }

            allRulesSatisfySorted.stream().filter(warningsData ->
                    warningsData.warnings.stream().filter(warning ->
                            warning.triggeredWhenRuleMet).count() != 0).forEach(warningsData -> {
                                allCurrentFrameWarnings.addAll(warningsData.warnings);
            });
        }

        if (!violateRulesTransformSatisfy.stream().filter( satisfy -> {
            return satisfy.satisfy && !satisfy.warnings.stream().filter(warning -> {
                return warning.triggeredWhenRuleMet;
            }).collect(Collectors.toList()).isEmpty();
        } ).collect(Collectors.toList()).isEmpty()) {
            allCurrentFrameWarnings.removeIf(warning -> {
                if (!warning.changeStateClear.equals(Optional.empty())) {
                    return warning.changeStateClear.get();

                }else {
                    return false;
                }
            } );
        }

//        计分逻辑　
        List<RuleSatisfyData> scoreRulesTransformSatisfy = transforms.stream().map( transform -> {
            Optional<SportState> toState = sport.findFirstStateByStateId(transform.to);

            if (!toState.equals(Optional.empty()) && transform.from == currentStateTime.stateId) {
                nextState = toState.get();
                RuleSatisfyData satisfy = toState.get().rulesSatisfy(RuleType.SCORE, stateTimeHistory, poseMap, objects, frameSize);

                if (satisfy.satisfy) {
                    currentStateTime = new StateTime(toState.get().id, currentTime, poseMap, Optional.empty());
                    currentStateTimeSetted();
                }

                return satisfy;
            }
            return new RuleSatisfyData(false, new HashSet<>(), 0 ,0);
        }).collect(Collectors.toList());


        if (scoreRulesTransformSatisfy.size() == 1) {
            allCurrentFrameWarnings.addAll(scoreRulesTransformSatisfy.get(0).warnings);
        } else if (scoreRulesTransformSatisfy.size() > 1) {

            List<RuleSatisfyData> allRulesSatisfySorted = scoreRulesTransformSatisfy.stream().
                    sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());
            System.out.println(String.format("satisfy %s", allRulesSatisfySorted));
            System.out.println("--------------------------");

            if (allRulesSatisfySorted.size() > 1){
                RuleSatisfyData first = allRulesSatisfySorted.get(0);
                RuleSatisfyData second = allRulesSatisfySorted.get(1);
//                TODO:　校验排序
                if (!first.satisfy && first.pass > second.pass) {
                    allCurrentFrameWarnings.addAll(first.warnings);
                }
            }

            allRulesSatisfySorted.stream().filter(warningsData ->
                    warningsData.warnings.stream().filter(warning ->
                            warning.triggeredWhenRuleMet).count() != 0).forEach(warningsData -> {
                allCurrentFrameWarnings.addAll(warningsData.warnings);
            });
        }

        if (!scoreRulesTransformSatisfy.stream().filter( satisfy -> {
            return satisfy.satisfy && !satisfy.warnings.stream().filter(warning -> {
                return warning.triggeredWhenRuleMet;
            }).collect(Collectors.toList()).isEmpty();
        } ).collect(Collectors.toList()).isEmpty()) {
            allCurrentFrameWarnings.removeIf(warning -> {
                if (!warning.changeStateClear.equals(Optional.empty())) {
                    return warning.changeStateClear.get();

                }else {
                    return false;
                }
            } );
        }

        allCurrentFrameWarnings.removeIf(warning -> {
            return warning.content == "";
        });

        updateWarnings(currentTime, allCurrentFrameWarnings);

        if (currentTime > lastTime) {
            lastTime = currentTime;
        }
    }

    private void playTimer(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {
        if (lastTime > currentTime) {
            return;
        }

        if (!sport.selectedLandmarkTypes.isEmpty()) {
            updateCurrentStateLandmarkBounds(poseMap, sport.selectedLandmarkTypes);
        }
        if (!sport.collectedObjects.isEmpty()) {
            updateCurrentStateObjectBounds(objects, sport.collectedObjects);
        }

        Set<Warning> allCurrentFrameWarnings = new HashSet<>();

        Stream<SportState> allHasRuleStates = sport.states.stream().filter(state -> {
            return sport.scoreStateSequence.stream().flatMap(states_ -> {
                return states_.stream();
            }).collect(Collectors.toList()).contains(state.id);
        });

        //        计分逻辑　
        List<RuleSatisfyData> scoreRulesSatisfy = allHasRuleStates.map( state -> {

            RuleSatisfyData satisfy = state.rulesSatisfy(RuleType.SCORE, stateTimeHistory, poseMap, objects, frameSize);
            System.out.println(String.format("lanmarksegment angle %s/%s", satisfy.pass, satisfy.total));

            if (satisfy.satisfy) {
                if (!inCheckingStatesTimer.containsKey(state.name)){
                    inCheckingStatesTimer.put(state.name, checkStateTimer(state, currentTime, state.checkCycle, poseMap, null));
                }

            }

            if (inCheckingStatesTimer.containsKey(state.name)) {
                if (inCheckingStateHistory.containsKey(state.name)) {
                    inCheckingStateHistory.get(state.name).add(satisfy.satisfy);
                }else{
                    inCheckingStateHistory.put(state.name, Lists.newArrayList(satisfy.satisfy));
                }
            }

            return satisfy;
        }).collect(Collectors.toList());

        if (scoreRulesSatisfy.size() == 1) {
            allCurrentFrameWarnings.addAll(scoreRulesSatisfy.get(0).warnings);
        } else if (scoreRulesSatisfy.size() > 1) {
            List<RuleSatisfyData> allRulesSatisfySorted = scoreRulesSatisfy.stream().
                    sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());
            if (allRulesSatisfySorted.size() > 1){
                RuleSatisfyData first = allRulesSatisfySorted.get(0);
                RuleSatisfyData second = allRulesSatisfySorted.get(1);
//                TODO:　校验排序
                if (!first.satisfy && first.pass > second.pass) {
                    allCurrentFrameWarnings.addAll(first.warnings);
                }

            }

            allRulesSatisfySorted.stream().filter(warningsData ->
                    warningsData.warnings.stream().filter(warning ->
                            warning.triggeredWhenRuleMet).count() != 0).forEach(warningsData -> {
                allCurrentFrameWarnings.addAll(warningsData.warnings);
            });
        }

        if (!scoreRulesSatisfy.stream().filter( satisfy -> {
            return satisfy.satisfy && !satisfy.warnings.stream().filter(warning -> {
                return warning.triggeredWhenRuleMet;
            }).collect(Collectors.toList()).isEmpty();
        } ).collect(Collectors.toList()).isEmpty()) {
            allCurrentFrameWarnings.removeIf(warning -> {
                if (!warning.changeStateClear.equals(Optional.empty())) {
                    return warning.changeStateClear.get();

                }else {
                    return false;
                }
            } );
        }

        if (inCheckingStatesTimer.isEmpty()){
            if (scoreTimes.isEmpty()) {
                allCurrentFrameWarnings.add(new Warning(String.format("开始%s", sport.name), true, sport.warningDelay));
            }else {
                List<RuleSatisfyData> allRulesSatisfySorted = scoreRulesSatisfy.stream().
                        sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());

                if (allRulesSatisfySorted.size() > 1){
                    RuleSatisfyData first = allRulesSatisfySorted.get(0);
                    RuleSatisfyData second = allRulesSatisfySorted.get(1);
                    System.out.println(String.format("%s first %s/%s second %s/%s", first.satisfy, first.pass, first.total, second.pass,second.total));
//                TODO:　校验排序
                    if (!first.satisfy && first.pass > second.pass) {
                        allCurrentFrameWarnings.addAll(first.warnings);
                    }

                    allCurrentFrameWarnings.add(new Warning(String.format("继续%s", sport.name), true, sport.warningDelay));

                }
            }
        }

        allCurrentFrameWarnings.removeIf(warning -> {
            return warning.content == "";
        });

        if (currentTime > lastTime) {
            lastTime = currentTime;
        }

        allCurrentFrameWarnings.removeIf(warning -> {
            return warning.content == "";
        });

        updateWarnings(currentTime, allCurrentFrameWarnings);
    }

    private void playTimeCounter(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {
        if (lastTime > currentTime) {
            return;
        }
        System.out.println(2);

        if (!sport.selectedLandmarkTypes.isEmpty()) {
            updateCurrentStateLandmarkBounds(poseMap, sport.selectedLandmarkTypes);
        }
        if (!sport.collectedObjects.isEmpty()) {
            updateCurrentStateObjectBounds(objects, sport.collectedObjects);
        }

        Set<Warning> allCurrentFrameWarnings = new HashSet<>();

        List<SportStateTransform> transforms = sport.stateTransForm.stream().filter(transform -> currentStateTime.stateId == transform.from).collect(Collectors.toList());
        List<RuleSatisfyData> violateRulesTransformSatisfy = transforms.stream().map( transform -> {
            Optional<SportState> toState = sport.findFirstStateByStateId(transform.to);
            if (!toState.equals(Optional.empty())) {
                RuleSatisfyData satisfy = toState.get().rulesSatisfy(RuleType.VIOLATE, stateTimeHistory, poseMap, objects, frameSize);
                return satisfy;
            }
            return new RuleSatisfyData(false, new HashSet<>(), 0 ,0);
        }).collect(Collectors.toList());

        if (violateRulesTransformSatisfy.size() == 1) {
            allCurrentFrameWarnings.addAll(violateRulesTransformSatisfy.get(0).warnings);
        } else if (violateRulesTransformSatisfy.size() > 1) {
            List<RuleSatisfyData> allRulesSatisfySorted = violateRulesTransformSatisfy.stream().
                    sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());
            if (allRulesSatisfySorted.size() > 1){
                RuleSatisfyData first = allRulesSatisfySorted.get(0);
                RuleSatisfyData second = allRulesSatisfySorted.get(1);
//                TODO:　校验排序
                if (!first.satisfy && first.pass > second.pass) {
                    allCurrentFrameWarnings.addAll(first.warnings);
                }

            }

            allRulesSatisfySorted.stream().filter(warningsData ->
                    warningsData.warnings.stream().filter(warning ->
                            warning.triggeredWhenRuleMet).count() != 0).forEach(warningsData -> {
                allCurrentFrameWarnings.addAll(warningsData.warnings);
            });
        }

        if (!violateRulesTransformSatisfy.stream().filter( satisfy -> {
            return satisfy.satisfy && !satisfy.warnings.stream().filter(warning -> {
                return warning.triggeredWhenRuleMet;
            }).collect(Collectors.toList()).isEmpty();
        } ).collect(Collectors.toList()).isEmpty()) {
            allCurrentFrameWarnings.removeIf(warning -> {
                if (!warning.changeStateClear.equals(Optional.empty())) {
                    return warning.changeStateClear.get();

                }else {
                    return false;
                }
            } );
        }


        //        计分逻辑　
        List<RuleSatisfyData> scoreRulesTransformSatisfy = transforms.stream().map( transform -> {
            Optional<SportState> toState = sport.findFirstStateByStateId(transform.to);

            if (!toState.equals(Optional.empty()) && transform.from == currentStateTime.stateId) {
                nextState = toState.get();
                RuleSatisfyData satisfy = toState.get().rulesSatisfy(RuleType.SCORE, stateTimeHistory, poseMap, objects, frameSize);

                if (satisfy.satisfy) {

                    if (!inCheckingStatesTimer.containsKey(toState.get().name)){
                        inCheckingStatesTimer.put(toState.get().name, checkStateTimer(toState.get(), currentTime, toState.get().checkCycle, poseMap, null));
                    }
                }

                if (inCheckingStatesTimer.containsKey(toState.get().name)) {
                    if (inCheckingStateHistory.containsKey(toState.get().name)) {
                        inCheckingStateHistory.get(toState.get().name).add(satisfy.satisfy);
                    }else{
                        inCheckingStateHistory.put(toState.get().name, Lists.newArrayList(satisfy.satisfy));
                    }
                }

                return satisfy;
            }
            return new RuleSatisfyData(false, new HashSet<>(), 0 ,0);
        }).collect(Collectors.toList());


        if (scoreRulesTransformSatisfy.size() == 1) {
            allCurrentFrameWarnings.addAll(scoreRulesTransformSatisfy.get(0).warnings);
        } else if (scoreRulesTransformSatisfy.size() > 1) {
            List<RuleSatisfyData> allRulesSatisfySorted = scoreRulesTransformSatisfy.stream().
                    sorted(Comparator.comparing(RuleSatisfyData::getPass).reversed()).collect(Collectors.toList());
            if (allRulesSatisfySorted.size() > 1){
                RuleSatisfyData first = allRulesSatisfySorted.get(0);
                RuleSatisfyData second = allRulesSatisfySorted.get(1);
//                TODO:　校验排序
                if (!first.satisfy && first.pass > second.pass) {
                    allCurrentFrameWarnings.addAll(first.warnings);
                }

            }

            allRulesSatisfySorted.stream().filter(warningsData ->
                    warningsData.warnings.stream().filter(warning ->
                            warning.triggeredWhenRuleMet).count() != 0).forEach(warningsData -> {
                allCurrentFrameWarnings.addAll(warningsData.warnings);
            });
        }

        if (!scoreRulesTransformSatisfy.stream().filter( satisfy -> {
            return satisfy.satisfy && !satisfy.warnings.stream().filter(warning -> {
                return warning.triggeredWhenRuleMet;
            }).collect(Collectors.toList()).isEmpty();
        } ).collect(Collectors.toList()).isEmpty()) {
            allCurrentFrameWarnings.removeIf(warning -> {
                if (!warning.changeStateClear.equals(Optional.empty())) {
                    return warning.changeStateClear.get();

                }else {
                    return false;
                }
            } );
        }

        allCurrentFrameWarnings.removeIf(warning -> {
            return warning.content == "";
        });

        updateWarnings(currentTime, allCurrentFrameWarnings);

        if (currentTime > lastTime) {
            lastTime = currentTime;
        }
    }

}
