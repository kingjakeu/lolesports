package com.kingjakeu.lolesports.api.common.constant;

import lombok.Getter;

@Getter
public enum CommonCode {
    TEAM_STATUS_ACTIVE("active"),
    STATE_IN_PROGRESS("inProgress"),
    STATE_UNNEEDED("unneeded"),
    STATE_COMPLETED("completed"),

    BLUE_SIDE("BLUE"),
    RED_SIDE("RED"),
    WIN("Win"),
    TIMEZONE_OFFSET("+9"),
    ;
    private final String code;
    CommonCode(String code){
        this.code = code;
    }

    public boolean codeEqualsTo(String code){
        return this.code.equals(code);
    }
}
