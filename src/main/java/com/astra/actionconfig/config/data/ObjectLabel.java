package com.astra.actionconfig.config.data;

import lombok.Data;

@Data
public class ObjectLabel {
    public String id = "";
    public String label = "person"; //标签
    public String confidence = "1.00"; //这是否应该用float
    public ColorState color;
    public float[][] rect = new float[2][2];
    public Boolean selected = false;

}
