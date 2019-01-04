package com.storycom.beans.settings;

import lombok.Data;

@Data
public class EmailSettings {
    private String host;
    private int port;
    private String protocol;
    private String sender;
    private String username;
    private String password;
//    private Integer delayCheck = 60000;
//    private Integer initialDelay = 20000;
}
