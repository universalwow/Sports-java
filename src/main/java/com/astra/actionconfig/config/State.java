package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.Image;
import com.astra.actionconfig.config.data.ObjectLabel;
import com.astra.actionconfig.config.data.landmarkd.HumanPose;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.ruler.ScoreRuler;
import lombok.Data;

import java.util.List;

@Data
public class State {
    public int id = 0;
    public String name = "";
    public String description = "";
    public Image image;
    public HumanPose humanPose;
    public int checkCycle = 1;
    public int keepTime = 5;
    public float passingRate = 0.8f;

    public List<LandmarkSegment> landmarkSegments;

    public List<ObjectLabel> objects;
    public List<ScoreRuler> scoreRules;
    //下面两个没有数据
    public List<ScoreRuler> violateRules;
}
