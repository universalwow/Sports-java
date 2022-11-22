package com.astra.actionconfig.config.data;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Question {
    public String question = "";//问题
    public List<String> choices; // 选项
    public Set<String> answers; // 答案
}
