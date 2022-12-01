package com.astra.actionconfig.config.data;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Question {
    public String question = "";//问题
    public List<String> choices; // 选项
    public Set<String> answers; // 答案

    public Set<Integer> answerIndexs() {
        Set<Integer> answersIndexs = new HashSet<Integer>();
        for (int i = 0; i < choices.size(); i++) {
            if (answers.contains(choices.get(i))) {
                answersIndexs.add(i);
            }

        }
        return answersIndexs;
    }
}
