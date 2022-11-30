package com.yogo.utils;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UrlUtils {
    private static String KEY_MAP = "Ao9PFmQwSXJV1d36fANi1Tbp3sECeIiZPHzuO4G5Hf7Z_0BR0F7RSabbjFi9xf3S";
    private static String BASE_URL_MAP = "https://dev.virtualearth.net/REST/v1/";

    public static String getUrlFindPoint(String point) {
        return BASE_URL_MAP
                .concat("Locations?q=").concat(point)
                .concat("&maxResults=10")
                .concat("&key=").concat(KEY_MAP);
    }

    public static String getUrlFindRoute(String p0, String p1) {
        return BASE_URL_MAP
                .concat("Routes/Driving?wp.0=").concat(p0)
                .concat("&wp.1=").concat(p1)
                .concat("&ra=routePath&avoid=minimizeTolls")
                .concat("&key=").concat(KEY_MAP);
    }

    public static String getUrlFindCoordinates(BigDecimal lat, BigDecimal lon) {
        return BASE_URL_MAP
                .concat("Locations/")
                .concat(String.valueOf(lat)).concat(",").concat(String.valueOf(lon))
                .concat("?key=").concat(KEY_MAP);
    }
}
