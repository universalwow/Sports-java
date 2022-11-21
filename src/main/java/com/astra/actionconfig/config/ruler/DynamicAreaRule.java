package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.LandmarkInArea;
import lombok.Data;

import java.util.List;



enum  RuleClass {
    LandmarkSegment, Landmark, Observation, FixedArea, DynamicArea
}

@Data
public class DynamicAreaRule {
    public String id; //
    public RuleClass ruleClass = RuleClass.DynamicArea;//
    public List<LandmarkInArea> landmarkInDynamicArea; //猜测
}
