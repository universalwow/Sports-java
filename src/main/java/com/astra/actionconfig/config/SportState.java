package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.PngImage;
import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.landmarkd.HumanPose;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.ruler.Rules;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class SportState {
    public int id = -1;
    public String name = "";
    public String description = "";
    public PngImage image;
    public Optional<HumanPose> humanPose;
    public int checkCycle = 1;
    public int keepTime = 5;
    public float passingRate = 0.8f;
    public Optional<Integer> directToStateId;
    public Optional<Double> transFormTimeLimit;

    public List<LandmarkSegment> landmarkSegments;

    public List<Observation> objects;
    public List<Rules> scoreRules;
    //下面两个没有数据
    public List<Rules> violateRules;


    
}
