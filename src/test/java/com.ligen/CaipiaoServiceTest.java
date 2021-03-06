package com.ligen;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ligen.task.HszsTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//
//import com.ligen.service.CaipiaoService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
@SpringBootTest
@RunWith(SpringRunner.class)
public class CaipiaoServiceTest {

    @Autowired
    HszsTask hszsTask;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testGetData() {

        List<JSONObject> jsonList = mongoTemplate.findAll(JSONObject.class, "hszs");
        for (JSONObject json : jsonList) {
            String date = json.getString("date");
            JSONArray dataList = json.getJSONArray("data");
            for (int i=0; i<dataList.size(); i++) {
                String[] split = dataList.getString(i).split(" ");
                String time = split[0];
                String index = split[1];
                String volume = split[2];
                JSONObject detail = new JSONObject();
                detail.put("_id", date + time);
                detail.put("index", index);
                detail.put("volume", volume);
                mongoTemplate.save(detail, "hszs_detail");
            }
            System.out.println(date);
        }


        hszsTask.getData();
    }
}
