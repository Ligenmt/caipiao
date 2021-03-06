package com.ligen.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ligen.util.HttpInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ProxyTask {

//    Logger logger = LoggerFactory.getLogger(getClass());
//    static String freeUrl = "https://www.kuaidaili.com/free";
//
//    public static JSONArray proxyList = new JSONArray();
//
//    @PostConstruct
//    public void init() {
//        crawler();
//    }
//
//    @Scheduled(cron="0 0 10 * * ?")
//    public void crawler() {
//        try {
//            proxyList.clear();
//            String str = HttpInstance.create().doGet(freeUrl, null, 10);
//            Document doc = Jsoup.parse(str);
//            Element root = doc.getElementById("list");
//            Elements trs = root.getElementsByTag("tr");
//            for (Element tr : trs) {
//                Elements tds = tr.getElementsByTag("td");
//                if (tds.isEmpty()) {
//                    continue;
//                }
//                Element td0 = tds.get(0);
//                Element td1 = tds.get(1);
//                proxyList.add(new JSONObject().fluentPut("host", td0.text()).fluentPut("port", td1.text()));
//            }
//            logger.info("proxyList updated");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("pl5 error");
//        }
//    }

}
