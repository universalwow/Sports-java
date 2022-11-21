package com.astra.actionconfig.config.data.state;

import lombok.Data;

import java.util.List;
@Data
public class ViolateStateSequence {
    public List<Integer> stateIds;
    public WarningState warning;
}
