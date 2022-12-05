package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Warning;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RuleSatisfyData {
    public boolean satisfy;
    public Set<Warning> warnings;
    public int pass;
    public int total;

    public RuleSatisfyData(boolean satisfy, Set<Warning> warnings, int pass, int total) {
        this.satisfy = satisfy;
        this.warnings = warnings;
        this.pass = pass;
        this.total = total;

    }
}
