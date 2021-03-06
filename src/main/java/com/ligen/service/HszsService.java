package com.ligen.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HszsService {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    public String getData(String time, int count, int interval) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").lte(time));
//        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count * interval);
        List<JSONObject> jsonList = mongoTemplate.find(query, JSONObject.class, "hszs_detail");

        StringBuilder sb = new StringBuilder();
        for (int i=0; i<count; i++) {
            JSONObject json = jsonList.get(i * interval);
            sb.append("<p>")
                    .append("时间:").append(json.getString("_id"))
                    .append("  指数:").append(json.getString("index"))
                    .append("  成交:").append(json.getString("volume"))
                    .append("</p>");
            log.info("i:{}, id:{}", i * interval, json.getString("_id"));
        }
        return sb.toString();
    }
}
