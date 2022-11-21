package com.astra.actionconfig.config.ruler;

import lombok.Data;

import java.util.List;

@Data
public class ScoreRuler {
    public String id = "";
    public String description = "";
    public List<DynamicAreaRuler> dynamicAreaRules;  //此处应没数据，故猜测与fixedAreaRulers格式相同
    public List<FixedAreaRuler> fixedAreaRules;

    //下面两个没有数据，格式为猜测
    public List<LandmarkRuler> landmarkRules;
    public List<LandmarkSegmentRuler> landmarkSegmentRules;

    //ObservationRuler未定义完全
    public List<ObservationRuler> observationRules;

}
