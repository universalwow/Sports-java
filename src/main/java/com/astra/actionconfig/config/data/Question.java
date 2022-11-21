package com.astra.actionconfig.config.data;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    public String question = "";//问题
    public List<String> choices; // 选项
    public List<String> answers; // 答案
}
