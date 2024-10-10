package com.metroflow.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeAttributes {
    private String amPm;
    private String hour;
    private String minute;

    public TimeAttributes(String amPm, String hour, String minute) {
        this.amPm = amPm;
        this.hour = hour;
        this.minute = minute;
    }
}
