package com.kyriexu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RespPageBean {
    private Long total;
    private List<?> data;
    private int current;

    public RespPageBean(Long total, List<?> data, int current) {
        this.total = total;
        this.data = data;
        this.current = current;
    }

    public RespPageBean(Long total, List<?> data) {
        this.total = total;
        this.data = data;
    }
}
