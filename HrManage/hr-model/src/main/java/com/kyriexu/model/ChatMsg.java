package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg {
    private String from;

    private String to;

    private String content;

    private Date date;

    private String fromNickname;
}
