package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Point3F;

import java.awt.*;
import java.util.Map;

public enum LandmarkType {
    Nose,
    LeftEyeOuter, LeftEye, LeftEyeInner,
    RightEyeOuter, RightEye, RightEyeInner,
    LeftEar, RightEar,
    MouthLeft, MouthRight,
    LeftShoulder, RightShoulder,
    LeftHip, RightHip,
    LeftElbow, RightElbow,
    LeftWrist, RightWrist,
    LeftThumb, RightThumb,
    LeftIndexFinger, RightIndexFinger,
    LeftPinkyFinger, RightPinkyFinger,
    LeftKnee, RightKnee,
    LeftAnkle, RightAnkle,
    LeftHeel, RightHeel,
    LeftToe, RightToe,
    None;

    public Landmark landmark(Map<LandmarkType, Point3F> poseMap) {
        Point3F point = poseMap.get(this);
        return new Landmark(this, point);
    }
}
