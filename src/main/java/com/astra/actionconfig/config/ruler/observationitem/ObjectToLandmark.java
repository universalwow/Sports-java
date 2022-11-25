package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.data.ObjectPositionPoint;
import com.astra.actionconfig.config.ruler.ComplexRule;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

@Data
public class ObjectToLandmark {
    public String id;
    public Boolean isRelativeToObject;
    public double lowerBound;
    public double upperBound;
    public ObjectPositionPoint fromPosition;
    public Landmark toLandmark;
    public Observation object;
    public Warning warning;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;

    public Range<Double> range() {
        return Range.between(lowerBound, upperBound);
    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap, Observation object) {

        Point3F fromObjectPoint = object.pointOf(this.fromPosition.position);
        Landmark toLandmark = this.toLandmark.getLandmarkType().landmark(poseMap);

        LandmarkSegment fromSegment = new LandmarkSegment(
                new Landmark(
                        LandmarkType.None, fromObjectPoint
                ),
                toLandmark
        );
        LandmarkSegment toSegment = toLandmarkSegmentToAxis.landmarkSegment.
                landmarkTypeSegment().landmarkSegment(poseMap);

        if (isRelativeToObject) {
            return ComplexRule.satisfyWithDirectionToObject(
                    this.fromPosition.axis,
                    this.range(),
                    fromSegment,
                    object.rectangle().height
            );
        }

        return ComplexRule.satisfyWithDirection(
                this.fromPosition.axis,
                this.toLandmarkSegmentToAxis.axis,
                this.range(),
                fromSegment,
                toSegment
        );
    }

}
