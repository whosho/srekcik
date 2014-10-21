package com.performgroup.innovation.kickers.core;

public enum TeamColor {
    RED, BLUE, UNDEFINED;

    public static TeamColor getOposite(TeamColor color) {
       if(color.equals(RED)) return BLUE;
       if(color.equals(BLUE)) return RED;

        return UNDEFINED;
    }
}
