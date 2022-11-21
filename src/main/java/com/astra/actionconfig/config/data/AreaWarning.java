package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class AreaWarning {
    public Boolean changeStateClear = true;
    public String content =  "";
    public int delayTime= 2;
    public Boolean isScoreWarning = true;
    public Boolean triggeredWhenRuleMet = false;
}
