package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Observation;

public class ExtremeObject {
    public Observation minX;
    public Observation maxX;
    public Observation minY;
    public Observation maxY;

    public ExtremeObject(Observation object) {
        this.minX = object;
        this.maxX = object;
        this.minY = object;
        this.maxY = object;
    }
}
