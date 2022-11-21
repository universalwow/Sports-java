package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Warning {
    public Boolean changeStateClear;
    public String content;
    public double delayTime;
    public Boolean isScoreWarning;
    public Boolean triggeredWhenRuleMet;
}
