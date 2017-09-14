package com.ctrip.xpipe.monitor;

import com.ctrip.xpipe.api.monitor.EventMonitor;
import com.ctrip.xpipe.utils.StringUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;

/**
 * @author leoliang
 *
 *         2017年3月1日
 */
public class CatEventMonitor implements EventMonitor {

    private final String FAIL = "fail";
    private final int typeMaxLen = 128;

    @Override
    public void logEvent(String type, String name, long count) {
        Cat.logEvent(type, shorten(name), Event.SUCCESS, "*count=" + count);
    }

    @Override
    public void logEvent(String type, String name) {
        Cat.logEvent(type, shorten(name));
    }

    @Override
    public void logAlertEvent(String simpleAlertMessage) {

        Cat.logEvent(ALERT_TYPE, shorten(simpleAlertMessage), FAIL, "");
    }

    private String shorten(String simpleAlertMessage) {
        return StringUtil.subHead(simpleAlertMessage, typeMaxLen);
    }

}
