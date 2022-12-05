package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class Warning {
    public Boolean changeStateClear = true;
    public String content;
    public double delayTime;
    public Boolean isScoreWarning = false;
    public Boolean triggeredWhenRuleMet;

    public Warning(String content, boolean triggeredWhenRuleMet, int delayTime) {
        this.content = content;
        this.triggeredWhenRuleMet = triggeredWhenRuleMet;
        this.delayTime = delayTime;
    }
}
