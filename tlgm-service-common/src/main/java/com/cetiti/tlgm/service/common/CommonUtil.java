package com.cetiti.tlgm.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.COMMUNITY_ID_LENGTH;
import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.GRID_ID_LENGTH;

/**
 * 通用工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 11:30:10
 */
public class CommonUtil {

    private CommonUtil() {}

    /**
     * 获得下一层网格标识，镇街和村社才有下层网格
     * @param position
     * @param number
     * @return
     */
    public static BigDecimal getSubDecimal(Integer position, BigDecimal number) {
        if (null == number) {
            return null;
        }

        int length = getBigDecimalLength(number);
        if (length == COMMUNITY_ID_LENGTH || length == GRID_ID_LENGTH){
            return new BigDecimal(String.valueOf(number).substring(0, position));
        } else {
            return null;
        }
    }

    /**
     * 计算number长度，如果为空返回-999_999
     * @param number
     * @return
     */
    public static int getBigDecimalLength(BigDecimal number) {
        if (null == number) {
            return -999_999;
        }

        return String.valueOf(number).length();
    }

    /**
     * 模拟HTTP请求
     * @param urlStr 地址
     * @param param 变量
     * @return
     * @throws IOException
     */
    public static String sendPostRequest(String urlStr, String param) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

        writer.write(param);
        writer.flush();

        StringBuffer answer = new StringBuffer();
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
     * @param number
     * @return
     */
    public static Long getLongNumber(Long number) {
        if (null == number || number < 0L) {
            return 0L;
        }
        return number;
    }

    /**
     * 获取Double类型的分数，null和小于0.0的值返回0.0
     * @param score
     * @return
     */
    public static Double getDoubleScore(Double score) {
        if (null == score || score < 0.0) {
            return 0.0;
        }
        return score;
    }

}
