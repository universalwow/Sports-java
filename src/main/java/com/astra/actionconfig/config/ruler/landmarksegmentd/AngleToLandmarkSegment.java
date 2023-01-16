package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

@Data
public class AngleToLandmarkSegment {
    public LandmarkSegment from;
    public LandmarkSegment to;
    public String id;
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;

    public Range<Double> range() {
        if (lowerBound < upperBound) {
            return Range.between(lowerBound, upperBound);
        }else{
            return Range.between(lowerBound, upperBound + 360);
        }

    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap) {
        Range<Double> range = this.range();
        LandmarkSegment fromSegment = this.from.landmarkTypeSegment().landmarkSegment(poseMap);

        LandmarkSegment toSegment = this.to.landmarkTypeSegment().landmarkSegment(poseMap);
        if (fromSegment.isEmpty() || toSegment.isEmpty()) {
            return false;
        }
        double angle = fromSegment.angle() - toSegment.angle();
        return range.contains(angle) || range.contains(angle + 360) || range.contains(angle - 360);
    }


}
