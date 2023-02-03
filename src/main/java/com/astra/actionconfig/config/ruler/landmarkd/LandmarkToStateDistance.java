package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.*;
import com.astra.actionconfig.config.ruler.ComplexRule;
import com.astra.actionconfig.config.ruler.StateTime;
import com.astra.actionconfig.config.ruler.observationitem.ExtremeDirection;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class LandmarkToStateDistance {
    public String id = "";
    public Boolean isRelativeToExtremeDirection;
    public double lowerBound = 0;
    public int toStateId = 0;
    public double upperBound = 0;
    public ExtremeDirection extremeDirection;
    public Boolean defaultSatisfy = false;
    public LandmarkToAxis fromLandmarkToAxis;
    public LandmarkSegmentToAxis toLandmarkSegmentToAxis;
    public LandmarkToAxis toLandmarkToAxis;
    public Warning warning;

    public Range<Double> range() {
        return Range.between(lowerBound, upperBound);
    }

    public boolean satisfy(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap) {
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            Landmark fromLandmark = this.fromLandmarkToAxis.landmark.landmarkType.landmark(poseMap);

            Landmark toLandmark = new Landmark(toLandmarkToAxis.landmark.landmarkType, new Point3F(0.,0.,0.));

            if (isRelativeToExtremeDirection) {
                switch (extremeDirection) {
                    case MinX:
                        toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX;
                        break;
                    case MinY:
                        toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY;

                        break;
                    case MaxX:

                        toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX;

                        break;
                    case MaxY:
                        toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY;
                        System.out.println(String.format("max y %s/%s", toLandmark.position.x, toLandmark.position.y));
                        break;
                    case MinX_MinY:
                        toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX.x;
                        toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY.y;

                        break;
                    case MinX_MaxY:
                        toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX.x;
                        toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY.y;

                        break;
                    case MaxX_MinY:
                        toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX.x;
                        toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY.y;

                        break;
                    case MaxX_MaxY:
                        toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX.x;
                        toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY.y;

                        break;
                }

            }else {
                toLandmark = toLandmark.landmarkType.landmark(toStateTime.poseMap);
            }

            LandmarkSegment fromSegment = new LandmarkSegment(fromLandmark, toLandmark);
            System.out.println(String.format("maxX %s/%s", fromLandmark.position.x, fromLandmark.position.y));
            System.out.println(String.format("maxX %s/%s", toLandmark.position.x, toLandmark.position.y));

            LandmarkSegment toSegment = ComplexRule.initLandmarkSegment(isRelativeToExtremeDirection, extremeDirection, toLandmarkSegmentToAxis.landmarkSegment, toStateTime);

            if (fromSegment.isEmpty() || toSegment.isEmpty()) {
                return false;
            }
            return ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.range(), fromSegment, toSegment);
        }else {
//            System.out.println(String.format("defaultSatisfy %s", defaultSatisfy));
            return defaultSatisfy;
        }


    }


}
