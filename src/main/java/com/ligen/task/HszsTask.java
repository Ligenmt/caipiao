package com.ligen.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HszsTask {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    OkHttpClient okHttpClient;

    private String tencentUrl = "https://web.ifzq.gtimg.cn/appstock/app/hkMinute/query?_var=min_data_hkHSI&code=hkHSI&r=0.2264603511037533";

    @Scheduled(cron="30 * * * * *")
    public void hszs() {

        Date now = new Date();
        int day = now.getDay();
        int hours = now.getHours();
        if (day == 0 || day == 6) {
            return;
        }
        if (hours < 9 || hours > 19) {
            return;
        }

        try {
            Request req = new Request.Builder()
            .url(tencentUrl)
            .get()
            .build();
            String str = okHttpClient.newCall(req).execute().body().string();
            System.out.println(str);
            str = str.replace("min_data_hkHSI=", "");
            JSONObject jsonObject = JSON.parseObject(str);
            JSONObject data = jsonObject.getJSONObject("data").getJSONObject("hkHSI").getJSONObject("data");
            String date = data.getString("date");
            data.put("_id", date);
            mongoTemplate.save(data, "hszs");
            logger.info("data saved:{}", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
