package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.Area;
import com.astra.actionconfig.config.data.FixedArea;
import com.astra.actionconfig.config.data.Question;
import com.astra.actionconfig.config.data.state.TransFormState;
import com.astra.actionconfig.config.data.state.ViolateStateSequence;
import lombok.Data;

import java.util.List;

enum SportClass {
    Counter, Timer, TimeCounter, None
}

enum SportPeriod {
    None, CompletePeriod, HarfPeriod, Continuous, Discrete
}

@Data
public class Sport {
    public String id = ""; //编号
    public String name = ""; //名称
    public String description = ""; //描述

    public Boolean isGestureController = false;  //是否手势控制

    //下面两个是报警的设置
    public String noStateWarning = ""; //
    public int warningDelay = 2;  //报警延时

    //下面三个是运动类型的描述
    public SportClass sportClass = SportClass.None; //
    public SportPeriod sportDiscrete = SportPeriod.None; //
    public SportPeriod sportPeriod = SportPeriod.CompletePeriod; //


    //下面是计分相关的设置
    public int scoreTimeLimit = 2; //
    public List<List<Integer>> scoreStateSequence; // 含义待定

    //下面是状态的定义
    public List<TransFormState> stateTransForm; // 含义待定
    public List<ViolateStateSequence> violateStateSequence; // 含义待定
    public List<SportState> states; // 含义待定

    //下面是动态区域定义
    public int dynamicAreaNumber = 3; //动态区域数量
    public List<Area> dynamicAreas; // 含义待定
    public List<FixedArea> fixedAreas; // 固定区域

    //下面是交互方式的描述
    public String interactionType = "";  //出现值 "None",此处不确定是否为对象
    public int interactionScoreCycle = 1;  //

    //下面这个，没有数据，暂时不清楚其格式
    public List<List<Integer>> interactionScoreStateSequence; // 含义待定

    //下面两个，没有数据，暂时不清楚其格式
    public List<String> collectedObjects; //
    public List<String> selectedLandmarkTypes; //

    public List<Question> questions; //

}
