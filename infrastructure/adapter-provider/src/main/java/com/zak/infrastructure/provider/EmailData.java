package com.zak.infrastructure.provider;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EmailData {

    private String from;
    private String to;
    private String subject;
    private String content;
}
