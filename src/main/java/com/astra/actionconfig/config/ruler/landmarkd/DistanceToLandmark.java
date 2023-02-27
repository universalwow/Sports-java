package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.ComplexRule;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

/*
* 同LandmarkSegmentLength类完全相同
*
* */
@Data
public class DistanceToLandmark {
    public LandmarkSegmentToAxis from;
    public LandmarkSegmentToAxis to;
    public String id = "";
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;

    public Range<Double> range() {
        return Range.between(lowerBound, upperBound);
    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap) {
        LandmarkSegment fromSegment =
                this.from.landmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);


        LandmarkSegment toSegment =
                this.to.landmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);

        if (fromSegment.isEmpty() || toSegment.isEmpty()) {
            return false;
        }

        return ComplexRule.satisfyWithDirection(from.axis, to.axis, this.range(), fromSegment, toSegment).satisfy;
    }
}
