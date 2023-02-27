package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.google.common.collect.Maps;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateTime {
    public int stateId;
    public double time;
    public Map<LandmarkType, Point3F> poseMap;
    public Optional<Observation> object;
    public Map<String, ExtremeObject> dynamicObjectsMaps = new HashMap<>();
    public Map<LandmarkType, ExtremePoint3D> dynamicPoseMaps = new EnumMap<LandmarkType, ExtremePoint3D>(LandmarkType.class);
    public boolean isDirectedTo = false;


    public StateTime(int stateId, double time, Map<LandmarkType, Point3F> poseMap,
                     Optional<Observation> object, boolean isDirectedTo) {
        this.stateId = stateId;
        this.time = time;
        this.poseMap = poseMap;
        this.object = object;
        this.isDirectedTo = isDirectedTo;

    }

    public StateTime(int stateId, double time, Map<LandmarkType, Point3F> poseMap,
                     Optional<Observation> object, Map<String, ExtremeObject> dynamicObjectsMaps, Map<LandmarkType, ExtremePoint3D> dynamicPoseMaps, boolean isDirectedTo) {
        this.stateId = stateId;
        this.time = time;
        this.poseMap = poseMap;
        this.object = object;
        this.dynamicObjectsMaps = dynamicObjectsMaps;
        this.dynamicPoseMaps = dynamicPoseMaps;
        this.isDirectedTo = isDirectedTo;

    }
}
