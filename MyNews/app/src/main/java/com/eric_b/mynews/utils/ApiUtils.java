package com.eric_b.mynews.utils;

public class ApiUtils {
    public static final String BASE_URL = "https://api.nytimes.com/";

    public static NyTimesService getNyTimesService() {
        return RetrofitClient.getClient(BASE_URL).create(NyTimesService.class);
    }
}
