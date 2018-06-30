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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class CaipiaoService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    public String pl5(HttpServletRequest request) {
        int count = Integer.parseInt(request.getParameter("count")); //过滤期数
        String numbers = request.getParameter("numbers");  //待过滤号码
        String filt = request.getParameter("filt"); //是否过滤三位
        logger.info("pl5 input:{}", numbers);
        String[] splitNumbers = numbers.split(" ");
        List<String> inNumbers = new ArrayList<>();
        for (String s : splitNumbers) {
            inNumbers.add(s);
        }
        Query query = new Query();
        query.limit(count).with(new Sort(Sort.Direction.DESC, "_id"));
        List<JSONObject> pl5 = mongoTemplate.find(query, JSONObject.class, "pl5");

        StringBuilder matchSb = new StringBuilder();
        matchSb.append("<br /><br />");
        for (JSONObject item : pl5) {
            String prizeNumber = item.getString("result");
            prizeNumber = prizeNumber.substring(0, 4);
            for (int i=inNumbers.size()-1; i>=0; i--) {
                String inNumber = inNumbers.get(i);
                if (inNumber.equals(prizeNumber)) {
                    logger.info("pl5 match in:{}, match:{}", inNumber, prizeNumber);
                    matchSb.append("in:").append(inNumber).append(" match:").append(prizeNumber).append("<br />");
                    inNumbers.remove(i);
                } else if (filt != null) {
                    if (isMatch3(prizeNumber.trim(), inNumber.trim())) {
                        logger.info("pl5 match in:{}, match:{}", inNumber, prizeNumber);
                        matchSb.append("in:").append(inNumber).append(" match3:").append(prizeNumber).append("<br />");
                        inNumbers.remove(i);
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : inNumbers) {
            sb.append(s).append(" ");
        }
        logger.info("pl5 output:{}", sb.toString());
        return sb.toString().trim() + matchSb.toString();
    }

    public String ynwfc(HttpServletRequest request) {
        int count = Integer.parseInt(request.getParameter("count"));
        String numbers = request.getParameter("numbers");
        String filt = request.getParameter("filt");
        int removeIndex = Integer.parseInt(request.getParameter("remove")) - 1;

        String[] splitNumbers = numbers.split(" ");
        List<String> inNumbers = new ArrayList<>();
        for (String s : splitNumbers) {
            inNumbers.add(s);
        }
        Query query = new Query();
        query.limit(count).with(new Sort(Sort.Direction.DESC, "_id"));
        List<JSONObject> ynwfc = mongoTemplate.find(query, JSONObject.class, "ynwfc");
        StringBuilder matchSb = new StringBuilder();
        matchSb.append("<br /><br />");
        for (JSONObject item : ynwfc) {
            String prizeNumber = item.getString("result");
            if (removeIndex >= 0) {
                prizeNumber = removeString(prizeNumber, removeIndex);
            }
            for (int i=inNumbers.size()-1; i>=0; i--) {
                String inNumber = inNumbers.get(i);
                if (inNumber.equals(prizeNumber)) {
                    logger.info("ynwfc match in:{}, match:{}", inNumber, prizeNumber);
                    matchSb.append("in:").append(inNumber).append(" match:").append(prizeNumber).append("<br />");
                    inNumbers.remove(i);
                } else if (filt != null) {
                    if (isMatch3(prizeNumber.trim(), inNumber.trim())) {
                        logger.info("ynwfc match in:{}, match:{}", inNumber, prizeNumber);
                        matchSb.append("in:").append(inNumber).append(" match3:").append(prizeNumber).append("<br />");
                        inNumbers.remove(i);
                    }
                }
            }
        }
        logger.info("ynwfc in:{}, out:{}", splitNumbers.length, inNumbers.size());
        StringBuilder sb = new StringBuilder();
        for (String s : inNumbers) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim() + matchSb.toString();
    }

    public String ynffc(HttpServletRequest request) {
        int count = Integer.parseInt(request.getParameter("count"));
        String numbers = request.getParameter("numbers");
        String filt = request.getParameter("filt");
        int removeIndex = Integer.parseInt(request.getParameter("remove")) - 1;

        String[] splitNumbers = numbers.split(" ");
        List<String> inNumbers = new ArrayList<>();
        for (String s : splitNumbers) {
            inNumbers.add(s);
        }
        Query query = new Query();
        query.limit(count).with(new Sort(Sort.Direction.DESC, "_id"));
        List<JSONObject> ynwfc = mongoTemplate.find(query, JSONObject.class, "ynffc");
        StringBuilder matchSb = new StringBuilder();
        matchSb.append("<br /><br />");
        for (JSONObject item : ynwfc) {
            String prizeNumber = item.getString("result");
            if (removeIndex >= 0) {
                prizeNumber = removeString(prizeNumber, removeIndex);
            }
            for (int i=inNumbers.size()-1; i>=0; i--) {
                String inNumber = inNumbers.get(i);
                if (inNumber.equals(prizeNumber)) {
                    logger.info("ynffc match in:{}, match:{}", inNumber, prizeNumber);
                    matchSb.append("in:").append(inNumber).append(" match:").append(prizeNumber).append("<br />");
                    inNumbers.remove(i);
                } else if (filt != null) {
                    if (isMatch3(prizeNumber.trim(), inNumber.trim())) {
                        logger.info("ynffc match in:{}, match:{}", inNumber, prizeNumber);
                        matchSb.append("in:").append(inNumber).append(" match3:").append(prizeNumber).append("<br />");
                        inNumbers.remove(i);
                    }
                }
            }
        }
        logger.info("ynwfc in:{}, out:{}", splitNumbers.length, inNumbers.size());
        StringBuilder sb = new StringBuilder();
        for (String s : inNumbers) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim() + matchSb.toString();
    }

    public String cqssc(HttpServletRequest request) {
        int count = Integer.parseInt(request.getParameter("count")); //过滤期数
        String numbers = request.getParameter("numbers"); //待过滤号码
        String filt = request.getParameter("filt"); //过滤3位置
        String filt2 = request.getParameter("filt2"); //过滤2位置
        String startNo = request.getParameter("start_no"); //起始过滤期数
        int removeIndex = Integer.parseInt(request.getParameter("remove")) - 1;
        String[] splitNumbers = numbers.split(" ");
        List<String> inNumbers = new ArrayList<>();
        for (String s : splitNumbers) {
            inNumbers.add(s);
        }
        Query query = new Query();
        if (!StringUtils.isEmpty(startNo)) {
            query.addCriteria(Criteria.where("no").lte(startNo));
        }
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
        List<JSONObject> cqssc = mongoTemplate.find(query, JSONObject.class, "cqssc");
        StringBuilder matchSb = new StringBuilder();
        matchSb.append("<br /><br />");
        for (JSONObject item : cqssc) {
            String prizeNumber = item.getString("result");
            if (removeIndex >= 0) {
                prizeNumber = removeString(prizeNumber, removeIndex);
            }
            for (int i=inNumbers.size()-1; i>=0; i--) {
                String inNumber = inNumbers.get(i);
                if (inNumber.equals(prizeNumber)) {
                    logger.info("cqssc match in:{}, match:{}", inNumber, prizeNumber);
                    matchSb.append("in:").append(inNumber).append(" match:").append(prizeNumber).append("<br />");
                    inNumbers.remove(i);
                } else if (filt2 != null) {
                    if (isMatch2(prizeNumber.trim(), inNumber.trim())) {
                        logger.info("cqssc match2 in:{}, match:{}", inNumber, prizeNumber);
                        matchSb.append("in:").append(inNumber).append(" match2:").append(prizeNumber).append("<br />");
                        inNumbers.remove(i);
                    }
                } else if (filt != null) {
                    if (isMatch3(prizeNumber.trim(), inNumber.trim())) {
                        logger.info("cqssc match in:{}, match:{}", inNumber, prizeNumber);
                        matchSb.append("in:").append(inNumber).append(" match3:").append(prizeNumber).append("<br />");
                        inNumbers.remove(i);
                    }
                }

            }
        }
        logger.info("cqssc in:{}, out:{}", splitNumbers.length, inNumbers.size());
        StringBuilder sb = new StringBuilder();
        for (String s : inNumbers) {
            sb.append(s).append(" ");
        }
//        return sb.toString().trim() + matchSb.toString();
        return sb.toString().trim();
    }

    //以任意三位数的组合进行排除
    private boolean isMatch3(String prizeNumber, String inNumber) {
        if (prizeNumber.length() != 4 || inNumber.length() != 4) {
            return false;
        }
        int count = 0;
        for (int i=0; i<4; i++) {
            if (prizeNumber.charAt(i) == inNumber.charAt(i)) {
                count += 1;
            }
        }
        return count >= 3;
    }

    private boolean isMatch2(String prizeNumber, String inNumber) {
        if (prizeNumber.length() != 4 || inNumber.length() != 4) {
            return false;
        }
        int count = 0;
        for (int i=0; i<4; i++) {
            if (prizeNumber.charAt(i) == inNumber.charAt(i)) {
                count += 1;
            }
        }
        return count >= 2;
    }

    private String removeString(String str, int index) {
        return str.substring(0, index) + str.substring(index + 1);
    }

    public String cqsscFilter(int count, int times, int remove) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
        query.fields().include("result");
        List<JSONObject> cqssc = mongoTemplate.find(query, JSONObject.class, "cqssc");
        JSONObject filter = new JSONObject();
        for (JSONObject item : cqssc) {
            String result = item.getString("result");
            result = removeString(result, remove);
            filter.put(result, filter.getIntValue(result) + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (String key : filter.keySet()) {
            int i = filter.getIntValue(key);
            if (i >= times) {
                sb.append(key).append(" ");
            }
        }
        return sb.toString();
    }
}
