package com.ligen.service;

import com.alibaba.fastjson.JSONArray;
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
import java.text.DecimalFormat;
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
        return sb.toString().trim();
    }

    public String cqsscV2(HttpServletRequest request) {
        String numbers = request.getParameter("numbers"); //待过滤号码集，都是4位
        String[] filtNumbers = numbers.split(" ");
//        Query query = new Query();
//        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
//        List<JSONObject> cqssc = mongoTemplate.find(query, JSONObject.class, "cqssc");
//        for (JSONObject item : cqssc) {
//            String prizeNumber = item.getString("result");
//            prizeNumber = removeString(prizeNumber, removeIndex);
//            handledNumbers.add(prizeNumber);
//        }
        DecimalFormat df = new DecimalFormat("0000");
        List<String> handledNumbers = new ArrayList<>(10000);
        for (int i=0; i<10000; i++) {
            handledNumbers.add(df.format(i));
        }

        for (String filtNumber : filtNumbers) {

            char[] filtChars = filtNumber.toCharArray();
            for (int j=handledNumbers.size()-1; j>=0; j--) {
                String number = handledNumbers.get(j);
                //比较每一位
                char[] chars = number.toCharArray();
                int sameCount = 0;
                for (int i=0; i<4; i++) {
                    if (filtChars[i] == chars[i]) {
                        sameCount += 1;
                    }
                }
                //有两位一样就过滤掉
                if (sameCount >= 2) {
                    handledNumbers.remove(j);
                }
            }
        }

        StringBuilder matchSb = new StringBuilder();

        matchSb.append("<p>");
        for (String number : handledNumbers) {
            matchSb.append(number).append(" ");
        }
        matchSb.append("</p>");
//        for (JSONObject item : cqssc) {
//            String prizeNumber = item.getString("result");
//            if (removeIndex >= 0) {
//                prizeNumber = removeString(prizeNumber, removeIndex);
//            }
//            for (int i=inNumbers.size()-1; i>=0; i--) {
//                String inNumber = inNumbers.get(i);
//                if (inNumber.equals(prizeNumber)) {
//                    logger.info("cqssc match in:{}, match:{}", inNumber, prizeNumber);
//                    matchSb.append("in:").append(inNumber).append(" match:").append(prizeNumber).append("<br />");
//                    inNumbers.remove(i);
//                } else if (filt2 != null) {
//                    if (isMatch2(prizeNumber.trim(), inNumber.trim())) {
//                        logger.info("cqssc match2 in:{}, match:{}", inNumber, prizeNumber);
//                        matchSb.append("in:").append(inNumber).append(" match2:").append(prizeNumber).append("<br />");
//                        inNumbers.remove(i);
//                    }
//                } else if (filt != null) {
//                    if (isMatch3(prizeNumber.trim(), inNumber.trim())) {
//                        logger.info("cqssc match in:{}, match:{}", inNumber, prizeNumber);
//                        matchSb.append("in:").append(inNumber).append(" match3:").append(prizeNumber).append("<br />");
//                        inNumbers.remove(i);
//                    }
//                }
//
//            }
//        }
        logger.info("cqssc v2 in:{}, out:{}", 10000, handledNumbers.size());
        return matchSb.toString().trim();
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

    /**
     * 寻找在某一列上最新开的连续n个数的历史，看后面出的是什么数
     * 比如选定第二位806，往前找第a,a-1,a-2期第二位是8，0，6的期，返回a+1期对应位置的数。
     * @param index  第几个位置
     * @param code  要排序的号码序列
     * @param count
     * @return
     */
    public String orderTail(int index, String code, Integer count) {

        JSONArray result = orderTailCalculate(index, code, count, "cqssc");
        String html = orderTailRender(result);
        return html;
//        long start = System.currentTimeMillis();
//        if (count == null) {
//            count = 5000;
//        }
//        int length = code.length(); //数列有几个数
//        char[] chars = code.toCharArray();
//
//        Query query = new Query();
//        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
//        List<JSONObject> recentNumbers = mongoTemplate.find(query, JSONObject.class, "cqssc");
//        logger.info("order_tail read data timeuse:{}", System.currentTimeMillis() - start);
//        StringBuilder sb = new StringBuilder();
//        JSONArray resultArray = new JSONArray();
//        for (int i=0; i<recentNumbers.size() - (length - 1); i++) {
//            char[] savedNumbers = new char[length];
//            for (int j=0; j<length; j++) {
//                String numbers = recentNumbers.get(i+j).getString("result");
//                char[] saved = numbers.substring(index, index + 1).toCharArray();
//                savedNumbers[j] = saved[0];
//            }
//            //比较每个位置的数
//            boolean same = true;
//            for (int j=0; j<length; j++) {
//                if (savedNumbers[j] != chars[j]) {
//                    same = false;
//                    break;
//                }
//            }
//            if (same && i >= 1) {
//                String numbersTarget = recentNumbers.get(i - 1).getString("result");
//                String savedTarget = numbersTarget.substring(index, index + 1);
//                String noTarget = recentNumbers.get(i - 1).getString("no");
//
//                JSONArray item = new JSONArray();
//                sb.append("<p>期数:").append(noTarget).append(" 号码:").append(savedTarget).append("</p>");
//                item.add(new JSONObject().fluentPut("no", noTarget).fluentPut("result", savedTarget));
//                for (int j=0; j<length; j++) {
//                    String sequenceNo = recentNumbers.get(i + j).getString("no");
//                    sb.append("<p>期数:").append(sequenceNo).append(" 号码:").append(savedNumbers[j]).append("</p>");
//                    item.add(new JSONObject().fluentPut("no", sequenceNo).fluentPut("result", savedNumbers[j]));
//                }
//                sb.append("<p>------------------------------------------------</p>");
//                resultArray.add(item);
//            }
//
//        }
//        logger.info("order_tail finish calculate timeuse:{}", System.currentTimeMillis() - start);
//        return sb.toString();
    }

    public JSONArray orderTailCalculate(int index, String code, Integer count, String collection) {
        long start = System.currentTimeMillis();
        if (count == null) {
            count = 5000;
        }
        int length = code.length(); //数列有几个数
        char[] chars = code.toCharArray();

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
        List<JSONObject> recentNumbers = mongoTemplate.find(query, JSONObject.class, collection);
        logger.info("order_tail read data timeuse:{}", System.currentTimeMillis() - start);
        JSONArray resultArray = new JSONArray();
        for (int i=0; i<recentNumbers.size() - (length - 1); i++) {
            char[] savedNumbers = new char[length];
            for (int j=0; j<length; j++) {
                String numbers = recentNumbers.get(i+j).getString("result");
                char[] saved = numbers.substring(index, index + 1).toCharArray();
                savedNumbers[j] = saved[0];
            }
            //比较每个位置的数
            boolean same = true;
            for (int j=0; j<length; j++) {
                if (savedNumbers[j] != chars[j]) {
                    same = false;
                    break;
                }
            }
            if (same && i >= 1) {
                String numbersTarget = recentNumbers.get(i - 1).getString("result");
                String savedTarget = numbersTarget.substring(index, index + 1);
                String noTarget = recentNumbers.get(i - 1).getString("no");

                JSONArray item = new JSONArray();
                item.add(new JSONObject().fluentPut("no", noTarget).fluentPut("result", savedTarget));
                for (int j=0; j<length; j++) {
                    String sequenceNo = recentNumbers.get(i + j).getString("no");
                    item.add(new JSONObject().fluentPut("no", sequenceNo).fluentPut("result", savedNumbers[j]));
                }
                resultArray.add(item);
            }

        }
        logger.info("order_tail finish calculate count:{}, timeuse:{}", resultArray.size(), System.currentTimeMillis() - start);
        return resultArray;
    }

    public String orderTailRender(JSONArray resultArray) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<resultArray.size(); i++) {
            JSONArray item = resultArray.getJSONArray(i);
            for (int j=0; j<item.size(); j++) {
                JSONObject json = item.getJSONObject(j);
                sb.append("<p>期数:").append(json.getString("no")).append(" 号码:").append(json.getString("result")).append("</p>");
            }
        sb.append("<p>------------------------------------------------</p>");
        }
        return sb.toString();
    }


    /**
     *输入某期号m和间隔n，返回从m开始，间隔n期的50个数据
     * @param interval
     * @param qishu
     * @return
     */
    public String qishuInterval(int interval, String qishu) {

//        MongoCollection<Document> cqssc = mongoTemplate.getCollection("cqssc");
//        FindIterable<Document> findIterable = cqssc.find(new Document("", ""));
//        MongoCursor<Document> iterator = findIterable.iterator();

        Query query = new Query();
        query.addCriteria(Criteria.where("no").is(qishu));
        if (!mongoTemplate.exists(query, "cqssc")) {
            return "";
        }
        int totalCount = interval * 51;
        query = new Query();
        query.addCriteria(Criteria.where("no").lte(qishu));
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(totalCount);
        List<JSONObject> cqssc = mongoTemplate.find(query, JSONObject.class, "cqssc");

        StringBuilder sb = new StringBuilder();
        sb.append("<p>").append(cqssc.get(0).getString("no")).append(" ").append(cqssc.get(0).getString("result")).append("</p>");
        int count = 0;
        for (int i = 1; i < cqssc.size(); i++) {
            if (i % interval == 0) {
                sb.append("<p>").append(cqssc.get(i).getString("no")).append(" ").append(cqssc.get(i).getString("result")).append("</p>");
                count += 1;
                logger.info("qishuInterval no:{}, result:{}", cqssc.get(i).getString("no"), cqssc.get(i).getString("result"));
                if (count == 50) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 百里挑一
     * @param no 开始期数
     * @param m 连续多少期
     * @return
     */
    public String notSame(String no, Integer m) {

        int d=0;
        boolean same = true;
        List<JSONObject> cqssc = null;
        while (same) {
            d += 1;
            Query query = new Query();
            query.addCriteria(Criteria.where("no").lte(no));
            query.fields().include("no").include("result");
            query.with(new Sort(Sort.Direction.DESC, "no")).limit(m * d);
            cqssc = mongoTemplate.find(query, JSONObject.class, "cqssc");
            String last = null;
            same = false;
            logger.info("d:{}", d);
            for (int i=(d-1); i<cqssc.size(); i+=d) {
                JSONObject json = cqssc.get(i);
                String res = json.getString("result").substring(0, 1);
                logger.info("no:{}, res:{}", json.getString("no"), res);
                if (res.equals(last)) { //出现连续相同的数则d+1
                    same = true;
                    break;
                }
                last = res;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<p>").append(d).append("</p>");
        for (int i=0; i<cqssc.size(); i+=d) {
            sb.append("<p>").append("no:").append(cqssc.get(i).getString("no")).append("  res:").append(cqssc.get(i).getString("result")).append("</p>");
        }

        return sb.toString();
    }

    public String algorithm01(String no, int count, String collection) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(count);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, collection);
        StringBuilder html = new StringBuilder();

        String[] lastNo = new String[4];
        for (JSONObject cqssc : cqsscList) {
            String result = cqssc.getString("result");
            String currNo = cqssc.getString("no");
            //第no期开奖号码后四位
            String a1 = result.substring(1, 2);
            String a2 = result.substring(2, 3);
            String a3 = result.substring(3, 4);
            String a4 = result.substring(4, 5);

            logger.info("algorithm01 第{}期后四位A: {}{}{}{}", currNo, a1, a2, a3, a4);
            query = new Query();
            query.addCriteria(Criteria.where("no").lte(currNo));
            query.with(new Sort(Sort.Direction.DESC, "no")).limit(5000);
            List<JSONObject> originList = mongoTemplate.find(query, JSONObject.class, collection);

            String b1 = null;
            String b2 = null;
            String b3 = null;
            String b4 = null;

            for (int i=1; i<originList.size(); i++) {
                JSONObject origin = originList.get(i);
                String originResult = origin.getString("result");
                if (b1 == null && originResult.substring(1, 2).equals(a1)) {
                    b1 = originList.get(i-1).getString("result").substring(1);
                }
                if (b2 == null && originResult.substring(2, 3).equals(a2)) {
                    b2 = originList.get(i-1).getString("result").substring(1);
                }
                if (b3 == null && originResult.substring(3, 4).equals(a3)) {
                    b3 = originList.get(i-1).getString("result").substring(1);
                }
                if (b4 == null && originResult.substring(4, 5).equals(a4)) {
                    b4 = originList.get(i-1).getString("result").substring(1);
                }

                if (b1 != null && b2 != null && b3 != null && b4 != null) {
                    break;
                }
            }
            logger.info("algorithm01 第{}期B: b1:{} b2:{} b3:{} b4:{}", currNo, b1, b2, b3, b4);
            String c1 = b1.substring(0,1) + b2.substring(0,1) + b3.substring(0,1) + b4.substring(0,1);
            String c2 = b1.substring(1,2) + b2.substring(1,2) + b3.substring(1,2) + b4.substring(1,2);
            String c3 = b1.substring(2,3) + b2.substring(2,3) + b3.substring(2,3) + b4.substring(2,3);
            String c4 = b1.substring(3,4) + b2.substring(3,4) + b3.substring(3,4) + b4.substring(3,4);
            logger.info("algorithm01 第{}期C: c1:{} c2:{} c3:{} c4:{}", currNo, c1, c2, c3, c4);
            JSONArray d1Array = orderTailCalculate(1, c1, 30000, collection);
            JSONArray d2Array = orderTailCalculate(2, c2, 30000, collection);
            JSONArray d3Array = orderTailCalculate(3, c3, 30000, collection);
            JSONArray d4Array = orderTailCalculate(4, c4, 30000, collection);

            String d1 = "x";
            String d2 = "x";
            String d3 = "x";
            String d4 = "x";
            if (d1Array.size() > 0) {
                d1 = d1Array.getJSONArray(0).getJSONObject(0).getString("result");
                logger.info("algorithm01 第{}期 d1:{}", currNo, d1);
            }
            if (d2Array.size() > 0) {
                d2 = d2Array.getJSONArray(0).getJSONObject(0).getString("result");
                logger.info("algorithm01 第{}期 d2:{}", currNo, d2);
            }
            if (d3Array.size() > 0) {
                d3 = d3Array.getJSONArray(0).getJSONObject(0).getString("result");
                logger.info("algorithm01 第{}期 d3:{}", currNo, d3);
            }
            if (d4Array.size() > 0) {
                d4 = d4Array.getJSONArray(0).getJSONObject(0).getString("result");
                logger.info("algorithm01 第{}期 d4:{}", currNo, d4);
            }
            String checkRight = "无";
            String d = d1 + d2 + d3 + d4;
            if (lastNo[0] != null) {
                checkRight = "对";
                if (lastNo[0].equals(d1) || lastNo[1].equals(d2) || lastNo[2].equals(d3) || lastNo[3].equals(d4)) {
                    checkRight = "错";
                }
            }
            lastNo[0] = a1;
            lastNo[1] = a2;
            lastNo[2] = a3;
            lastNo[3] = a4;
            html.append("<p>期号：").append(cqssc.getString("no")).append("  开奖号码：").append(result).append(" 结果：" +  d).append("  和上一期开奖号码比对：").append(checkRight).append("</p>");

        }
        return html.toString();
    }

    public String abxxn(String code, int count) {
        Query query = new Query();
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(30000);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        JSONArray resultArray01 = abxxn(0, 1, code, count, cqsscList);
        JSONArray resultArray02 = abxxn(0, 2, code, count, cqsscList);
        JSONArray resultArray03 = abxxn(0, 3, code, count, cqsscList);
        JSONArray resultArray12 = abxxn(1, 2, code, count, cqsscList);
        JSONArray resultArray13 = abxxn(1, 3, code, count, cqsscList);
        JSONArray resultArray23 = abxxn(2, 3, code, count, cqsscList);


        StringBuilder sb = new StringBuilder();
        sb.append("<p>第1,2位").append("</p>");
        for (int i=0; i<resultArray01.size(); i++) {
            sb.append("<p>")
                .append("期号:")
                .append(resultArray01.getJSONObject(i).getString("no"))
                .append("   号码：")
                .append(resultArray01.getJSONObject(i).getString("result"))
                .append("</p>");

        }
        sb.append("<p>第1,3位").append("</p>");
        for (int i=0; i<resultArray02.size(); i++) {
            sb.append("<p>").append("期号:").append(resultArray01.getJSONObject(i).getString("no")).append("</p>");

        }
        sb.append("<p>第1,4位").append("</p>");
        for (int i=0; i<resultArray03.size(); i++) {
            sb.append("<p>").append("期号:").append(resultArray01.getJSONObject(i).getString("no")).append("</p>");

        }
        sb.append("<p>第2,3位").append("</p>");
        for (int i=0; i<resultArray12.size(); i++) {
            sb.append("<p>").append("期号:").append(resultArray01.getJSONObject(i).getString("no")).append("</p>");

        }
        sb.append("<p>第2,4位").append("</p>");
        for (int i=0; i<resultArray13.size(); i++) {
            sb.append("<p>").append("期号:").append(resultArray01.getJSONObject(i).getString("no")).append("</p>");

        }
        sb.append("<p>第3,4位").append("</p>");
        for (int i=0; i<resultArray23.size(); i++) {
            sb.append("<p>").append("期号:").append(resultArray01.getJSONObject(i).getString("no")).append("</p>");

        }
        return sb.toString();

    }

    private JSONArray abxxn(int indexA, int indexB, String code, int count, List<JSONObject> cqsscList) {

        JSONArray resultArray = new JSONArray();
        String a = code.substring(indexA, indexA + 1);
        String b = code.substring(indexB, indexB + 1);
        int currentCount = 0;

        for (int j=0; j<cqsscList.size()-1; j++) {
            JSONObject cqssc = cqsscList.get(j);
            String result = cqssc.getString("result");
            result = result.substring(1);
            String aa = result.substring(indexA, indexA + 1);
            String bb = result.substring(indexB, indexB + 1);
            if (a.equals(aa) && b.equals(bb)) {
                currentCount++;
                resultArray.add(new JSONObject()
                        .fluentPut("no", cqsscList.get(j+1).getString("no"))
                        .fluentPut("result", cqsscList.get(j+1).getString("result")));
            }
            if (count <= currentCount) {
                break;
            }
        }
        System.out.println(resultArray);
        return resultArray;
    }
}
