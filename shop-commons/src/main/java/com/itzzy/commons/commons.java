package com.itzzy.commons;

import java.io.Serializable;

public class commons implements Serializable {
    private Integer draw;

    private Integer start;

    private Integer length;

    public Integer getDraw() {
        return draw;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
