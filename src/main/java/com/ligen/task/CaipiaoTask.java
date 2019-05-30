package com.ligen.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ligen.util.HttpInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ligen on 2018/3/11.
 */
@Component
public class CaipiaoTask {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MongoTemplate mongoTemplate;
    private static String ynwfcUrl = "https://34589.com/api/lottery/ynwfc/history?page=0&size=50";
    private static String ynffcUrl = "https://34589.com/api/lottery/ynffc/history?page=0&size=50";
    private static String cqsscUrl = "https://34589.com/api/lottery/cqssc/history?page=0&size=50";
    private static String xywfcUrl = "https://34589.com/api/lottery/xywfc/history?page=0&size=50";
    private static String pl5Url = "http://www.17500.cn/getData/p5.TXT";

    @Scheduled(cron="0 0 10 * * ?")
    public void 排列五() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = HttpInstance.create().doGet(pl5Url, null, 10);
            savePl5Data(str);
            logger.info("pl5 updated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("pl5 error");
        }
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void 印尼五分彩() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = HttpInstance.create().doGet(ynwfcUrl, null, 10);
            saveData(str, "ynwfc", sdf);
            logger.info("ynwfc updated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ynwfc error");
        }
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void 印尼分分彩() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = HttpInstance.create().doGet(ynffcUrl, null, 10);
            saveData(str, "ynffc", sdf);
            logger.info("ynffc updated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ynffc error");
        }
    }

    @Scheduled(cron="0 2/5 * * * ?")
    public void 重庆时时彩() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = HttpInstance.create().doGet(cqsscUrl, null, 10);
            saveData(str, "cqssc", sdf);
            logger.info("cqssc updated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("cqssc error");
        }
    }

    @Scheduled(cron="0 2/5 * * * ?")
    public void 幸运五分彩() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = HttpInstance.create().doGet(xywfcUrl, null, 10);
            saveData(str, "xywfc", sdf);
            logger.info("xywfc updated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("xywfc error");
        }
    }

    private void saveData(String response, String type, SimpleDateFormat sdf) throws Exception {
        JSONObject json = JSON.parseObject(response);
        JSONArray contents = json.getJSONArray("content");
        for (int i=0; i<contents.size(); i++) {
            JSONObject content = contents.getJSONObject(i);
            String _id = sdf.format(new Date(content.getLong("drawTime")));
            String no = content.getString("no");
            JSONArray numbersArray = content.getJSONObject("result").getJSONArray("numbers");
            StringBuilder numbers = new StringBuilder();
            for (int j = 0; j < numbersArray.size(); j++) {
                numbers.append(numbersArray.getString(j));
            }
            JSONObject data = new JSONObject();
            data.put("_id", _id);
            data.put("no", no);
            data.put("result", numbers.toString());
            mongoTemplate.save(data, type);
        }
    }

    private void savePl5Data(String text) {
        String[] rows = text.split("\n");
        for (String row : rows) {
            String[] cols = row.split(" ");
            String no = cols[0];
            String id = cols[1];
            String result = cols[2] + cols[3] + cols[4] + cols[5] + cols[6];
            JSONObject data = new JSONObject();
            data.put("_id", id);
            data.put("no", no);
            data.put("result", result);
            mongoTemplate.save(data, "pl5");
        }
    }
}
