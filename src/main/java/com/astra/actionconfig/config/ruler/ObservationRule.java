package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToLandmark;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToObject;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToStateAngle;
import com.astra.actionconfig.config.ruler.observationitem.ObjectToStateDistance;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;


enum ObjectLabel {
    rope, pose, basketball
}

@Data
public class ObservationRule {
    public String id = "";
    public ObjectLabel objectLabel;
    public RuleClass ruleClass = RuleClass.Observation;

    public List<ObjectToLandmark> objectToLandmark = Lists.newArrayList();
    public List<ObjectToObject> objectToObject = Lists.newArrayList();
    public List<ObjectToStateAngle> objectToStateAngle = Lists.newArrayList();

    public List<ObjectToStateDistance> objectToStateDistance = Lists.newArrayList();


    public RuleSatisfyData allSatisfy(List<StateTime> stateTimeHistory,
                                      Map<LandmarkType, Point3F> poseMap,
                                      List<Observation> objects,
                                      Point2F frameSize) {
        RuleSatisfyData objectToLandmarkSatisfies =
                objectToLandmark.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = false;
                            if (objects.size() > 0) {
                                System.out.println(String.format("-------------------%s - %s", objects.get(0).label == next.fromPosition.id, next.fromPosition.id));

                            }

                            Optional<Observation> selectedObject = objects.stream().
                                    filter(object -> object.label.equals(next.fromPosition.id)).findFirst();
                            System.out.println(String.format("-------------------%s - %s", selectedObject, next.fromPosition.id));
                            if (!selectedObject.equals(Optional.empty())) {
                                satisfy = next.satisfy(poseMap, selectedObject.get());
                            }

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

        RuleSatisfyData objectToObjectSatisfies =
                objectToObject.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = false;

                            Optional<Observation> selectedFromObject = objects.stream()
                                    .filter(object -> object.label.equals(next.fromPosition.id)).findFirst();
                            Optional<Observation> selectedToObject = objects.stream()
                                    .filter(object -> object.label.equals(next.toPosition.id)).findFirst();

                            if (!selectedFromObject.equals(Optional.empty()) && !selectedToObject.equals(Optional.empty())) {
                                satisfy = next.satisfy(poseMap, selectedFromObject.get(), selectedToObject.get());
                            }

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

        RuleSatisfyData objectToStateDistanceSatisfies =
                objectToStateDistance.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = false;

                            Optional<Observation> selectedObject = objects.stream()
                                    .filter(object -> object.label.equals(next.fromPosition.id)).findFirst();
                            if (!selectedObject.equals(Optional.empty())) {
                                satisfy = next.satisfy(stateTimeHistory, poseMap, selectedObject.get());
                            }

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

        RuleSatisfyData objectToStateAngleSatisfies =
                objectToStateAngle.stream().reduce(
                        new RuleSatisfyData(true,
                                new HashSet<>(),
                                0, 0),
                        (result, next) -> {
                            boolean satisfy = false;

                            Optional<Observation> selectedObject = objects.stream()
                                    .filter(object -> object.label.equals(next.fromPosition.id)).findFirst();
                            if (!selectedObject.equals(Optional.empty())) {
                                satisfy = next.satisfy(stateTimeHistory, poseMap, selectedObject.get());
                            }

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
        warnings.addAll(objectToLandmarkSatisfies.warnings);
        warnings.addAll(objectToObjectSatisfies.warnings);
        warnings.addAll(objectToStateDistanceSatisfies.warnings);
        warnings.addAll(objectToStateAngleSatisfies.warnings);
        return new RuleSatisfyData(
                objectToLandmarkSatisfies.satisfy &&
                        objectToObjectSatisfies.satisfy &&
                        objectToStateDistanceSatisfies.satisfy &&
                        objectToStateAngleSatisfies.satisfy,
                warnings,
                objectToLandmarkSatisfies.pass +
                        objectToObjectSatisfies.pass +
                        objectToStateDistanceSatisfies.pass +
                        objectToStateAngleSatisfies.pass,
                objectToLandmarkSatisfies.total +
                        objectToObjectSatisfies.total +
                        objectToStateDistanceSatisfies.total +
                        objectToStateAngleSatisfies.total);
    }



}
