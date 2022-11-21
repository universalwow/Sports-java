package com.astra.actionconfig.config.data.state;

import com.astra.actionconfig.config.data.Warning;
import lombok.Data;

import java.util.List;
@Data
public class ViolateStateSequence {
    public List<Integer> stateIds;
    public Warning warning;
}
