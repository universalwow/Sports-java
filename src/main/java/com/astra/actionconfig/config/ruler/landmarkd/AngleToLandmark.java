package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

@Data
public class AngleToLandmark {
    public Landmark fromLandmark;
    public Landmark toLandmark;
    public String id;
    public double lowerBound = 0;
    public double upperBound = 0;
    public Warning warning;

    public LandmarkSegment landmarkSegment() {
        return new LandmarkSegment(toLandmark, fromLandmark);
    }

    public Range<Double> range() {
        if (lowerBound < upperBound) {
            return Range.between(lowerBound, upperBound);
        }else{
            return Range.between(lowerBound, upperBound + 360);
        }

    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap) {
        Range<Double> range = this.range();
        LandmarkSegment segment = this.landmarkSegment().landmarkTypeSegment().landmarkSegment(poseMap);
        if (segment.isEmpty()) {
            return false;
        }

        return range.contains(segment.angle()) || range.contains(segment.angle() + 360);
    }

}
