package com.dos.md.data.bean;

/**
 * Created by Administrator on 2015/11/23.
 */
public final class Station {
    private static String status, totalElectricityGenerate, totalElectricityUse, averageCapacity, stationName, checkSchedule;

    public static String getCheckSchedule() {

        return checkSchedule;
    }

    public static void setCheckSchedule(String checkSchedule) {
        Station.checkSchedule = checkSchedule;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Station.status = status;
    }

    public static String getTotalElectricityGenerate() {
        return totalElectricityGenerate;
    }

    public static void setTotalElectricityGenerate(String totalElectricityGenerate) {
        Station.totalElectricityGenerate = totalElectricityGenerate;
    }

    public static String getTotalElectricityUse() {
        return totalElectricityUse;
    }

    public static void setTotalElectricityUse(String totalElectricityUse) {
        Station.totalElectricityUse = totalElectricityUse;
    }

    public static String getAverageCapacity() {
        return averageCapacity;
    }

    public static void setAverageCapacity(String averageCapacity) {
        Station.averageCapacity = averageCapacity;
    }

    public static String getStationName() {
        return stationName;
    }

    public static void setStationName(String stationName) {
        Station.stationName = stationName;
    }
}
