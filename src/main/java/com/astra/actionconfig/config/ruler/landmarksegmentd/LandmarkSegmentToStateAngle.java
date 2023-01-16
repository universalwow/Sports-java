package com.astra.actionconfig.config.ruler.landmarksegmentd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.ruler.StateTime;
import com.astra.actionconfig.config.ruler.observationitem.ExtremeDirection;
import lombok.Data;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class LandmarkSegmentToStateAngle {
    public String id = "";
    public Boolean isRelativeToExtremeDirection = true;
    public ExtremeDirection extremeDirection = ExtremeDirection.MinX;
    public double lowerBound = 0;
    public int toStateId = 0;
    public double upperBound = 0;
    public LandmarkSegment fromLandmarkSegment;
    public LandmarkSegment toLandmarkSegment;
    public Warning warning;

    public Range<Double> range() {
        if (lowerBound < upperBound) {
            return Range.between(lowerBound, upperBound);
        }else{
            return Range.between(lowerBound, upperBound + 360);
        }

    }

    public boolean satisfy(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap) {
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            LandmarkSegment fromSegment = this.toLandmarkSegment.landmarkTypeSegment().landmarkSegment(poseMap);


            LandmarkSegment toSegment = new LandmarkSegment(
                    new Landmark(fromSegment.startLandmark.landmarkType, new Point3F()),
                    new Landmark(fromSegment.endLandmark.landmarkType, new Point3F())
                    );


            if (isRelativeToExtremeDirection) {
                switch (extremeDirection) {

                    case MinX:
                        toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minX;
                        toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minX;

                        break;
                    case MinY:

                        toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minY;
                        toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minY;

                        break;
                    case MaxX:
                        toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxX;
                        toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxX;

                        break;
                    case MaxY:
                        toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxY;
                        toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxY;

                        break;
                    case MinX_MinY:

                        toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minX.x;
                        toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minX.x;

                        toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minY.y;
                        toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minY.y;

                        break;
                    case MinX_MaxY:

                        toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minX.x;
                        toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minX.x;

                        toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxY.y;
                        toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxY.y;

                        break;
                    case MaxX_MinY:
                        toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxX.x;
                        toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxX.x;

                        toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).minY.y;
                        toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).minY.y;

                        break;
                    case MaxX_MaxY:
                        toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxX.x;
                        toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxX.x;

                        toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.startLandmark.landmarkType).maxY.y;
                        toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(fromSegment.endLandmark.landmarkType).maxY.y;

                        break;
                }

            }else {
                toSegment = this.toLandmarkSegment.landmarkTypeSegment().landmarkSegment(toStateTime.poseMap);
            }

            if (fromSegment.isEmpty() || toSegment.isEmpty()) {
                return false;
            }

            double angle = fromSegment.angle() - toSegment.angle();
            Range<Double> range = range();
            return range.contains(angle) || range.contains(angle + 360) || range.contains(angle - 360);

        }else {
            return true;
        }


    }
}
