package com.astra.actionconfig.config.ruler.landmarkd;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.*;
import com.astra.actionconfig.config.ruler.ComplexRule;
import com.astra.actionconfig.config.ruler.StateTime;
import com.astra.actionconfig.config.ruler.observationitem.ExtremeDirection;
import lombok.Data;
import org.apache.commons.lang3.Range;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<Boolean> toStateToggle = Optional.of(false);
    public  Optional<Boolean> toLastFrameToggle = Optional.of(false);
    public Optional<Double> weight = Optional.of(0.0);

    public Range<Double> range() {
        return Range.between(lowerBound, upperBound);
    }

    public Range<Double> reverseRange() {
        return Range.between(-1 * upperBound, -1 * lowerBound);
    }

    public boolean satisfy(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap) {
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            Landmark fromLandmark = this.fromLandmarkToAxis.landmark.landmarkType.landmark(poseMap);

            Landmark toLandmark = ComplexRule.initLandmark(isRelativeToExtremeDirection, extremeDirection, toLandmarkToAxis.landmark, toStateTime);

            LandmarkSegment fromSegment = new LandmarkSegment(fromLandmark, toLandmark);
            System.out.println(String.format("maxX %s/%s", fromLandmark.position.x, fromLandmark.position.y));
            System.out.println(String.format("maxX %s/%s", toLandmark.position.x, toLandmark.position.y));

            LandmarkSegment toSegment = ComplexRule.initLandmarkSegment(isRelativeToExtremeDirection, extremeDirection, toLandmarkSegmentToAxis.landmarkSegment, toStateTime);

            if (fromSegment.isEmpty() || toSegment.isEmpty()) {
                return false;
            }
            return ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.range(), fromSegment, toSegment).satisfy;
        }else {
//            System.out.println(String.format("defaultSatisfy %s", defaultSatisfy));
            return defaultSatisfy;
        }


    }

    public SatisfyScore satisfyWithRatio(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap) {
        double score = 0.0;
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            Landmark fromLandmark = this.fromLandmarkToAxis.landmark.landmarkType.landmark(poseMap);

            Landmark toLandmark = ComplexRule.initLandmark(isRelativeToExtremeDirection, extremeDirection, toLandmarkToAxis.landmark, toStateTime);

            LandmarkSegment fromSegment = new LandmarkSegment(fromLandmark, toLandmark);
            System.out.println(String.format("maxX %s/%s", fromLandmark.position.x, fromLandmark.position.y));
            System.out.println(String.format("maxX %s/%s", toLandmark.position.x, toLandmark.position.y));

            LandmarkSegment toSegment = ComplexRule.initLandmarkSegment(isRelativeToExtremeDirection, extremeDirection, toLandmarkSegmentToAxis.landmarkSegment, toStateTime);

            if (fromSegment.isEmpty() || toSegment.isEmpty()) {
                return new SatisfyScore(false, score);
            }

//            SatisfyScore satisfyScore = ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.range(), fromSegment, toSegment);
//            score = satisfyScore.score;
            return ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.range(), fromSegment, toSegment);
        }else {
//            System.out.println(String.format("defaultSatisfy %s", defaultSatisfy));
            return new SatisfyScore(defaultSatisfy, score);
        }


    }

    public SatisfyScore satisfyWithWeight(List<StateTime> stateTimeHistory, Map<LandmarkType, Point3F> poseMap, Map<LandmarkType, Point3F> lastPoseMap) {
        double score = 0.0;
        List<StateTime> toStateTimes = stateTimeHistory.
                stream().
                filter(stateTime -> stateTime.stateId == this.toStateId).collect(Collectors.toList());
        if (toStateTimes.size() > 0) {
            StateTime toStateTime = toStateTimes.get(toStateTimes.size()-1);
            Landmark fromLandmark = this.fromLandmarkToAxis.landmark.landmarkType.landmark(poseMap);
            Landmark toLandmark = this.toLandmarkToAxis.landmark.landmarkType.landmark(lastPoseMap);

            LandmarkSegment fromSegment = new LandmarkSegment(fromLandmark, toLandmark);
            System.out.println(String.format("maxX %s/%s", fromLandmark.position.x, fromLandmark.position.y));
            System.out.println(String.format("maxX %s/%s", toLandmark.position.x, toLandmark.position.y));

            LandmarkSegment toSegment = ComplexRule.initLandmarkSegment(isRelativeToExtremeDirection, extremeDirection, toLandmarkSegmentToAxis.landmarkSegment, toStateTime);

            if (fromSegment.isEmpty() || toSegment.isEmpty()) {
                return new SatisfyScore(false, score);
            }

            SatisfyScore satisfyScore = ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.range(), fromSegment, toSegment);
            if (satisfyScore.satisfy) {
                score = weight.get();
            }


            SatisfyScore reverseSatisfyScore = ComplexRule.satisfyWithDirection(fromLandmarkToAxis.axis, toLandmarkSegmentToAxis.axis, this.reverseRange(), fromSegment, toSegment);
            if (reverseSatisfyScore.satisfy) {
                score = -1 * weight.get();
            }
            return new SatisfyScore(satisfyScore.satisfy, score);
        }else {
//            System.out.println(String.format("defaultSatisfy %s", defaultSatisfy));
            return new SatisfyScore(defaultSatisfy, score);
        }


    }


}
