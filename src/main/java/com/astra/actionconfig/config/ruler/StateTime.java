package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.Map;

public class StateTime {
    public int stateId;
    public double time;
    public Map<LandmarkType, Point3F> poseMap;
    public Option<Observation> object;
    public Map<String, ExtremeObject> dynamicObjectsMaps;
    public Map<LandmarkType, ExtremePoint3D> dynamicPoseMaps;


}
