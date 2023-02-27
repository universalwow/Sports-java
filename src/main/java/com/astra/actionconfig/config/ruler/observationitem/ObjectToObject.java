package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.ObjectPositionPoint;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegmentToAxis;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.ComplexRule;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.Map;

@Data
public class ObjectToObject {
    public String id;
    public Boolean isRelativeToObject;
    public double lowerBound;
    public double upperBound;
    public ObjectPositionPoint fromPosition;
    public ObjectPositionPoint toPosition;
    public Observation object;
    public Warning warning;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;

    public Range<Double> range() {
        return Range.between(lowerBound, upperBound);
    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap, Observation fromObject, Observation toObject) {
        Point3F fromObjectPosition = fromObject.pointOf(this.fromPosition.position);
        Point3F toObjectPosition = toObject.pointOf(this.toPosition.position);

        LandmarkSegment fromSegment = new LandmarkSegment(
                new Landmark(LandmarkType.None, fromObjectPosition),
                new Landmark(LandmarkType.None, toObjectPosition)
        );


        LandmarkSegment toSegment = toLandmarkSegmentToAxis.landmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);

        if (fromSegment.isEmpty() || toSegment.isEmpty()) {
            return false;
        }
        if (isRelativeToObject) {
            return ComplexRule.satisfyWithDirectionToObject(
                    this.fromPosition.axis,
                    this.range(),
                    fromSegment,
                    fromObject.rectangle().height
            );
        }
        return ComplexRule.satisfyWithDirection(
                this.fromPosition.axis,
                this.toLandmarkSegmentToAxis.axis,
                this.range(),
                fromSegment,
                toSegment
        ).satisfy;
    }


}
