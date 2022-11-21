package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.LandmarkInArea;
import lombok.Data;

import java.util.List;

@Data
public class FixedAreaRule {
    public String id = ""; //
    public RuleClass ruleClass = RuleClass.FixedArea;//
    public List<LandmarkInArea> landmarkInFixedArea;

}
