package com.github.javierugarte.athleticcalendar;

import java.util.ArrayList;
import java.util.List;

public class MergeDatas {

    private List<Match> dataMerge = null;

    private List<Match> dataCalendar;
    private List<Match> dataServer;

    public void setDataCalendar(List<Match> dataCalendar) {
        this.dataCalendar = dataCalendar;
    }

    public void setDataServer(List<Match> dataServer) {
        this.dataServer = dataServer;
    }

    public List<Match> mergeData() {
        if (dataServer == null || dataCalendar == null) {
            return dataMerge;
        }

        dataMerge = new ArrayList<>();

        for (Match matchServer : this.dataServer) {
            for (Match matchCalendar : this.dataCalendar) {
                if (isEqualEvent(matchServer, matchCalendar)) {
                    matchServer.setCalendarId(matchCalendar.getCalendarId());
                    matchServer.setExists(true);
                    if (matchServer.getStartTime().getTime() != matchCalendar.getStartTime().getTime() ||
                            !matchServer.getTvs().equalsIgnoreCase(matchCalendar.getTvs())) {
                        matchServer.setDifferent(true);
                    } else {
                        matchServer.setDifferent(false);
                    }
                }
            }

            dataMerge.add(matchServer);
        }
        return dataMerge;
    }

    private boolean isEqualEvent(Match matchServer, Match matchCalendar) {
        return matchServer.getId().equalsIgnoreCase(matchCalendar.getId());
    }

}
