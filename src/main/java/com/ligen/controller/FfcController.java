package com.ligen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/ffc")
public class FfcController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/mlffc")
    public String mlffc() {

        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(200);
        List<JSONObject> list = mongoTemplate.find(query, JSONObject.class, "mlffc");

        JSONArray resultArray = new JSONArray();

        for (int loc1 = 0; loc1<5; loc1++) {
            for (int loc2 = loc1+1; loc2<5; loc2++) {
                String last1 = "";
                String last2 = "";
                int tryCount = 0;
                int lastTryCount = 0;
                boolean lastTry = true;
                int maxTryCount = 0;
                JSONObject result = new JSONObject();
                result.put("location", loc1 + "" + loc2);
                result.put("data", new JSONArray());
                for (int i=0; i<list.size(); i++) {
                    JSONObject data = list.get(i);
                    JSONArray numbers = data.getJSONArray("numbers");
                    JSONObject lastTryData = new JSONObject();

                    int n1 = numbers.getIntValue(loc1);
                    int n2 = numbers.getIntValue(loc2);
                    String e1 = isOdd(n1);
                    String e2 = isOdd(n2);
                    if (e1.equals(last1) && e2.equals(last2)) {
                        log.info("位置:{} {}, 中, 尝试次数:{}, 号码:{}, 期数:{}, {}", loc1, loc2, tryCount, n1 + "" + n2, data.getString("no"), e1 + e2);
                        last1 = "";
                        last2 = "";
                        if (tryCount > maxTryCount) {
                            maxTryCount = tryCount;
                        }
                        if (lastTry) {
                            lastTryData.put("bang", "中");
                            lastTryData.put("tryCount", tryCount);
                            lastTryData.put("number", n1 + "" + n2);
                            lastTryData.put("no", data.getString("no"));
                            lastTryData.put("oddEven", e1 + e2);
                            result.getJSONArray("data").add(lastTryData);
                        }
                        lastTry = false;
                        tryCount = 0;
                    } else {
                        if (lastTry) {
                            lastTryCount += 1;
                            lastTryData.put("bang", tryCount == 0 ? "开始" : "不中");
                            lastTryData.put("tryCount", tryCount);
                            lastTryData.put("number", n1 + "" + n2);
                            lastTryData.put("no", data.getString("no"));
                            lastTryData.put("oddEven", e1 + e2);
                            result.getJSONArray("data").add(lastTryData);
                        }
                        tryCount += 1;
                        last1 = e1;
                        last2 = e2;
                        log.info("位置:{} {},不中, 尝试次数:{}, 号码:{}, 期数:{}, {}", loc1, loc2, tryCount, n1 + "" + n2, data.getString("no"), e1 + e2);

                    }
                }
                log.info("位置:{} {} 最近已经{}次不中，近1000期最高{}次连续不中", loc1, loc2, lastTryCount, maxTryCount);
                result.put("lastTryCount", lastTryCount);
                result.put("maxTryCount", maxTryCount);
                resultArray.add(result);
            }
        }
        return resultArray.toJSONString();
    }

    private String isOdd(int n) {
        return n % 2 != 0 ? "奇" : "偶";
    }
}
