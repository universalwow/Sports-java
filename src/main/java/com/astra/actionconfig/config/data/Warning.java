package com.astra.actionconfig.config.data;

import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class Warning {
//    {"changeStateClear":false,"content":"","delayTime":2,"isScoreWarning":true,"triggeredWhenRuleMet":false}
    public Optional<Boolean> changeStateClear;
    public String content;
    public double delayTime;
    public Optional<Boolean> isScoreWarning;
    public Boolean triggeredWhenRuleMet;

    public Warning(){}

    public Warning(String content, boolean triggeredWhenRuleMet, double delayTime) {
        this.content = content;
        this.triggeredWhenRuleMet = triggeredWhenRuleMet;
        this.delayTime = delayTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warning)) return false;
        Warning warning = (Warning) o;
        return content.equals(warning.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
