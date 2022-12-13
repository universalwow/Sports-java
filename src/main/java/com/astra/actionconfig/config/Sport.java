package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.DynamicArea;
import com.astra.actionconfig.config.data.FixedArea;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Question;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.config.data.state.SportStateTransform;
import com.astra.actionconfig.config.data.state.ViolateStateSequence;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Optional;

enum SportClass {
    Counter, Timer, TimeCounter, None
}

enum SportPeriod {
    None, CompletePeriod, HarfPeriod, Continuous, Discrete
}

enum InteractionType {
    None,
    SingleChoice, MultipleChoice,
    SingleTouch, OrdinalTouch
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
    public List<List<Integer>> scoreStateSequence = Lists.newArrayList();; // 含义待定

    //下面是状态的定义
    public List<SportStateTransform> stateTransForm = Lists.newArrayList();; // 含义待定
    public List<ViolateStateSequence> violateStateSequence = Lists.newArrayList();; // 含义待定
    public List<SportState> states = Lists.newArrayList();; // 含义待定

    //下面是动态区域定义
    public int dynamicAreaNumber = 3; //动态区域数量
    public List<DynamicArea> dynamicAreas = Lists.newArrayList();; // 含义待定
    public List<FixedArea> fixedAreas = Lists.newArrayList();; // 固定区域

    //下面是交互方式的描述
    public InteractionType interactionType;  //出现值 "None",此处不确定是否为对象
    public Optional<Integer> interactionScoreCycle;  //

    //下面这个，没有数据，暂时不清楚其格式
    public List<List<Integer>> interactionScoreStateSequence = Lists.newArrayList(); // 含义待定

    //下面两个，没有数据，暂时不清楚其格式
    public List<String> collectedObjects = Lists.newArrayList();; //
    public List<LandmarkType> selectedLandmarkTypes = Lists.newArrayList();; //

    public List<Question> questions = Lists.newArrayList();; //


    public Optional<SportState> findFirstStateByStateId(int stateId) {
        return states.stream().filter(state -> {
            return state.id == stateId;
        }).findFirst();
    }

    public Optional<Integer> findFirstDynamicAreaIndex(String areaId) {
        Optional<Integer> index = Optional.empty();
        for (int i = 0; i < dynamicAreas.size(); i++) {
            if (dynamicAreas.get(i).id == areaId) {
                index = Optional.of(i);
            }
        }
        return  index;
    }

    public void updateDynamicArea(String areaId, List<Point2F> area) {
        int areaIndex = findFirstDynamicAreaIndex(areaId).get();
        dynamicAreas.get(areaIndex).area = area.toArray(new Point2F[area.size()]);
    }

    public void  generateDynamicArea(String areaId, List<Point2F> area){
        for (int i = 0; i < states.size(); i++) {
            states.get(i).generateDynamicArea(areaId, area);
        }
    }

    public List<Point2F> generateDynamicArea(Point2F imageSize, String areaId) {
        int areaIndex = findFirstDynamicAreaIndex(areaId).get();
        DynamicArea area = dynamicAreas.get(areaIndex);
        double width = area.width;
        double heightToWidthRatio = area.heightToWidthRatio;

        double centerX = area.limitedArea[0] * imageSize.x +
                Math.random() * ((area.limitedArea[2]  - area.limitedArea[0]) * imageSize.x);

        double centerY = area.limitedArea[1] * imageSize.y +
                Math.random() * ((area.limitedArea[3]  - area.limitedArea[1]) * imageSize.y);

        double width_ = imageSize.x * width;
        double height = width_ * heightToWidthRatio;

        Point2F leftTop = new Point2F(
                centerX - width_/2,
                centerY - height/2
        );

        Point2F rightTop = new Point2F(
                centerX + width_/2,
                centerY - height/2
        );

        Point2F rightBottom = new Point2F(
                centerX + width_/2,
                centerY + height/2
        );

        Point2F leftBottom = new Point2F(
                centerX - width_/2,
                centerY + height/2
        );
        return Lists.newArrayList(leftTop, rightTop, rightBottom, leftBottom);
    }


}
