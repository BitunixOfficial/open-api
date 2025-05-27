package com.bitunix.openapi.response;

import java.util.List;


public class PositionHistoryPageResp extends PageResp {

    private List<PositionHistoryResp> positionList;

    public List<PositionHistoryResp> getPositionList() {
        return positionList;
    }
}
