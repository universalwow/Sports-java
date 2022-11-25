package com.astra.actionconfig.config.ruler.observationitem;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.ObjectPositionPoint;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.StateTime;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class ObjectToStateAngle {
    public ExtremeDirection extremeDirection;
    public String id;
    public Boolean isRelativeToExtremeDirection;
    public double lowerBound;
    public double upperBound;
    public int toStateId;
    public Warning warning;

    public ObjectPositionPoint fromPosition;
    public ObjectPositionPoint toPosition;

    public Range<Double> range() {
        if (lowerBound < upperBound) {
            return Range.between(lowerBound, upperBound);
        }else{
            return Range.between(lowerBound, upperBound + 360);
        }

    }

    public boolean satisfy(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap,
                           Observation object) {
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            Point3F fromObjectPoint = object.pointOf(fromPosition.position);

            Point3F toObjectPoint = new Point3F();

//            LandmarkSegment fromSegment = this.toLandmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);
//            LandmarkSegment toSegment = new LandmarkSegment(
//                    new Landmark(fromSegment.startLandmark.landmarkType, new Point3F()),
//                    new Landmark(fromSegment.endLandmark.landmarkType, new Point3F())
//            );


            if (isRelativeToExtremeDirection) {
                switch (extremeDirection) {

                    case MinX:
                        toObjectPoint =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minX.pointOf(fromPosition.position);
                        break;
                    case MinY:

                        toObjectPoint =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minY.pointOf(fromPosition.position);
                        break;
                    case MaxX:
                        toObjectPoint =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxX.pointOf(fromPosition.position);
                        break;
                    case MaxY:
                        toObjectPoint =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxY.pointOf(fromPosition.position);
                        break;
                    case MinX_MinY:

                        toObjectPoint.x =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minX.pointOf(fromPosition.position).x;
                        toObjectPoint.y =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minY.pointOf(fromPosition.position).y;
                        break;
                    case MinX_MaxY:

                        toObjectPoint.x =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minX.pointOf(fromPosition.position).x;
                        toObjectPoint.y =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxY.pointOf(fromPosition.position).y;
                        break;
                    case MaxX_MinY:
                        toObjectPoint.x =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxX.pointOf(fromPosition.position).x;
                        toObjectPoint.y =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        minY.pointOf(fromPosition.position).y;
                        break;
                    case MaxX_MaxY:
                        toObjectPoint.x =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxX.pointOf(fromPosition.position).x;
                        toObjectPoint.y =
                                toStateTime.dynamicObjectsMaps.get(fromPosition.id).
                                        maxY.pointOf(fromPosition.position).y;
                        break;
                }

            }else {
                toObjectPoint = toStateTime.object.getValue().pointOf(fromPosition.position);
            }

            LandmarkSegment landmarkSegment = new LandmarkSegment(
                    new Landmark(LandmarkType.None, toObjectPoint),
                    new Landmark(LandmarkType.None, fromObjectPoint)
            );

            double angle = landmarkSegment.angle();
            Range<Double> range = range();
            return range.contains(angle) || range.contains(angle + 360);

        }else {
            return true;
        }

    }


}
