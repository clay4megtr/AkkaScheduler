package com.beifeng.etl.util;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.beifeng.common.DateEnum;
import com.beifeng.util.TimeUtil;

public class TimeUtilTest {
    public static void main(String[] args) {
        long time = 1450023132124L;
        time = 1450105932125L;
        System.out.println(TimeUtil.getDateInfo(time, DateEnum.HOUR));
    }
}
