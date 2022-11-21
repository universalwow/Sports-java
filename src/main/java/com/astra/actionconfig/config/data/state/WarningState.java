package com.astra.actionconfig.config.data.state;

import lombok.Data;

@Data
public class WarningState {
    public int delayTime = 0;
    public Boolean triggeredWhenRuleMet = true;
    public String content = "";

}
