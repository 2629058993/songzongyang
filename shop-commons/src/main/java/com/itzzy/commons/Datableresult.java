package com.itzzy.commons;

import java.io.Serializable;
import java.util.List;

public class Datableresult implements Serializable {
    private Integer draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List data;

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public Datableresult(Integer draw, long recordsTotal, long recordsFiltered, List data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public Datableresult() {

    }
}
