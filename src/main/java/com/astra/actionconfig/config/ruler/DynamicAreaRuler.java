package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.LandmarkInArea;
import lombok.Data;

import java.util.List;

@Data
public class DynamicAreaRuler {
    public String id = ""; //
    public String ruleClass = "";//
    public List<LandmarkInArea> dynamicAreaRules; //猜测
}
