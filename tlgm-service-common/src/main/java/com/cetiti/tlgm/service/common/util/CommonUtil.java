package com.cetiti.tlgm.service.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.COMMUNITY_ID_LENGTH;
import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.GRID_ID_LENGTH;

/**
 * 通用工具类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 11:30:10
 */
public class CommonUtil {

    private CommonUtil() {
    }

    /**
     * 获得下一层网格标识，镇街和村社才有下层网格
     *
     * @param position 网格标识
     * @param number   网格长度
     * @return 下层网格标识
     */
    public static BigDecimal getSubDecimal(Integer position, BigDecimal number) {
        if (null == number) {
            return null;
        }

        int length = getBigDecimalLength(number);
        if (length == COMMUNITY_ID_LENGTH || length == GRID_ID_LENGTH) {
            return new BigDecimal(String.valueOf(number).substring(0, position));
        } else {
            return null;
        }
    }

    /**
     * 计算number长度，如果为空返回-999_999
     *
     * @param number 数字
     * @return 计算数字长度
     */
    public static int getBigDecimalLength(BigDecimal number) {
        if (null == number) {
            return -999_999;
        }

        return String.valueOf(number).length();
    }

    /**
     * 模拟HTTP请求
     *
     * @param urlStr 地址
     * @param param  变量
     * @return 相应数据
     * @throws IOException 抛出异常信息
     */
    public static String sendPostRequest(String urlStr, String param) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

        writer.write(param);
        writer.flush();

        StringBuilder answer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            answer.append(line);
        }

        writer.close();
        reader.close();

        return answer.toString();
    }

    /**
     * 获取Long类型的数目，null和小于0的值返回0L
     *
     * @param number 数值
     * @return 处理后数值
     */
    public static Long getLongWithDefaultValue(Long number) {
        if (null == number || number < 0L) {
            return 0L;
        }
        return number;
    }

    /**
     * 获取Double类型的分数，null和小于0.0的值返回0.0
     *
     * @param score 分数
     * @return 处理后数据
     */
    public static Double getDoubleWithDefaultValue(Double score) {
        if (null == score || score < 0.0) {
            return 0.0;
        }
        return score;
    }

    /**
     * 计算两个点之间的距离
     *
     * @param lonStart 起始点经度
     * @param latStart 起始点纬度
     * @param lonEnd   结束点经度
     * @param latEnd   结束点纬度
     * @return 两点距离(公里)
     */
    public static double getDistance(double lonEnd, double latEnd, double lonStart, double latStart) {
        double EARTH_RADIUS = 6371 * 1000;
        double radLatStart = rad(latEnd);
        double radLatEnd = rad(latStart);
        double disLat = radLatEnd - radLatStart;
        double disLon = rad(lonStart) - rad(lonEnd);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(disLat / 2), 2) +
                Math.cos(radLatEnd) * Math.cos(radLatStart) * Math.pow(Math.sin(disLon / 2), 2)));
        distance = (distance * EARTH_RADIUS) / 1000.0;
        BigDecimal approximate = new BigDecimal(distance).setScale(4, RoundingMode.UP);
        return approximate.doubleValue();
    }

    private static double rad(double distance) {
        return distance * Math.PI / 180.0;
    }

    /**
     * 计算两个时间相差的小时数
     *
     * @param endTime 结束时间
     * @param startTime 开始时间
     * @return 两个时间差值
     */
    public static double getTimeInterval(Date endTime, Date startTime) {
        double interval = (endTime.getTime() - startTime.getTime()) / (3600 * 1000.0);
        BigDecimal approximate = new BigDecimal(interval).setScale(4, RoundingMode.UP);
        return approximate.doubleValue();
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date end = formatter.parse("2017-12-13 8:34:13");
        Date start = formatter.parse("2017-12-13 8:31:50");
        System.out.println(CommonUtil.getTimeInterval(end, start));
        System.out.println(CommonUtil.getDistance(120.0140133333330, 30.2840750000000, 120.0144433333330, 30.2841216666667));
    }

}
