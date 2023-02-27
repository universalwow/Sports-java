package com.astra.actionconfig.config.ruler.landmarkd;

import lombok.Data;

@Data
public class SatisfyScore {
    public boolean satisfy;
    public double score;

    public SatisfyScore(boolean satisfy, double score) {
        this.satisfy = satisfy;
        this.score = score;
    }
}
