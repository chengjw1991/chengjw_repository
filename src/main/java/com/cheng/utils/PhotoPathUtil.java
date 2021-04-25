package com.cheng.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chengjw
 * @date 2021-01-19 14:13
 */
@Component
@ConfigurationProperties(prefix = "photopath")
public class PhotoPathUtil {

    private static String path;

    public static String getPath() {
        return path;
    }

    @Value("${path}")
    public  void setPath(String path) {
        PhotoPathUtil.path = path;
    }
}
