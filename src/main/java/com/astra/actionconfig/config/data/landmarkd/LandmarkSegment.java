package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.ColorState;
import lombok.Data;

@Data
public class LandmarkSegment {

    public ColorState color = new ColorState();
    public Boolean selected = false;
    public Landmark startLandmark;
    public Landmark endLandmark;

}
