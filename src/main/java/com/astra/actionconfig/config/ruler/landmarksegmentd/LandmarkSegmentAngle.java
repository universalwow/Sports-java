package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

@Data
public class LandmarkSegmentAngle {

    public String id;
    public LandmarkSegment landmarkSegment;
    public double lowerBound = 0f;
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
        LandmarkSegment segment = this.landmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);

        if (segment.isEmpty()) {
            return false;
        }

        System.out.println(String.format("%s -> %s", segment.landmarkTypeSegment().id, segment.angle()));
        return range.contains(segment.angle()) || range.contains(segment.angle() + 360);
    }
}
