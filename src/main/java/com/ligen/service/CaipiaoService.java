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
import java.util.HashSet;
import java.util.Iterator;
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
        //从0000-9999
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
    public String orderTail(int index, String code, Integer count, String collection) {

        JSONArray result = orderTailCalculate(index, null, code, count, collection);
        String html = orderTailRender(result);
        return html;
    }

    public String orderTailV2(String no, int count, String collection) {

        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(4);
        List<JSONObject> startList = mongoTemplate.find(query, JSONObject.class, collection);
        logger.info("orderTailCalculateV2 data:{}", startList);
        //千位
        String a1 = orderTailCalculateV2(1, no, startList, count, collection);
        String a2 = orderTailCalculateV2(2, no, startList, count, collection);
        String a3 = orderTailCalculateV2(3, no, startList, count, collection);
        String a4 = orderTailCalculateV2(4, no, startList, count, collection);

        return "<p>a1:" + a1 + " </p><p>a2:" + a2 + " </p><p>a3:" + a3 + " </p><p>a4:" + a4 + "</p>";
    }

    public String orderTailCalculateV2(int index, String no, List<JSONObject> startList, int count, String collection) {
        StringBuilder code = new StringBuilder();
        for (int i=0; i<4; i++) {
            String result = startList.get(i).getString("result").substring(index, index+1);
            code.append(result);
        }
        logger.info("orderTailCalculateV2 index:{} startCode:{}", index, code);
        StringBuilder result = new StringBuilder();
        while (true) {
            JSONArray calculatedArray = orderTailCalculate(index, no, code.toString(), count, collection);
            if (calculatedArray.size() > 0) {
                String s = calculatedArray.getJSONArray(0).getJSONObject(0).getString("result");
                result.append(s);
            }
            code = new StringBuilder();
            for (int i=0; i<calculatedArray.size(); i++) {
                if (i == 4) {
                    break;
                }
                code.append(calculatedArray.getJSONArray(i).getJSONObject(0).getString("result"));
            }
            logger.info("orderTailCalculateV2 index:{}, result:{}, code:{}", index, result, code);
            if (calculatedArray.size() < 4) {
                break;
            }
        }
        return result.toString();
    }

    public JSONArray orderTailCalculate(int index, String no, String code, Integer count, String collection) {
        long start = System.currentTimeMillis();
        if (count == null) {
            count = 5000;
        }
        int length = code.length(); //数列有几个数
        char[] chars = code.toCharArray();

        Query query = new Query();
        if (no != null) {
            query.addCriteria(Criteria.where("no").lte(no));
        }
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
        logger.info("order_tail finish calculate count:{}, timeuse:{}, value:{}", resultArray.size(), System.currentTimeMillis() - start, resultArray);
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

        List<JSONObject> cqssc = qishuIntervalCalculate(interval, qishu, 51, "cqssc");

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
     * 以qishuInterval为基础 输入期号及该期号码，然后从输出结果中取第一个输出做新的输入（期号不变），一直迭代 直到每个位置的数都取出来 最后得到每个位置的输出序列
     * @param interval
     * @param qishu
     * @return
     */
    public JSONArray qishuIntervalIteration(int interval, String qishu, String collection) {
        String firstInterval = interval + "";
        JSONArray indexArray = new JSONArray();
        indexArray
                .fluentAdd(new JSONArray(10))
                .fluentAdd(new JSONArray(10))
                .fluentAdd(new JSONArray(10))
                .fluentAdd(new JSONArray(10));
        int count = 0;
        while (true) {
            count += 1;
            List<JSONObject> cqssc = qishuIntervalCalculate(interval, qishu, 2, collection);
            //找到第一期
            String result = cqssc.get(interval).getString("result").substring(1);
            logger.info("qishuIntervalIteration count:{}, interval:{}, result:{}", interval, count, result);
            if (!indexArray.getJSONArray(0).contains(result.charAt(0))) {
                indexArray.getJSONArray(0).add(result.charAt(0));
            }
            if (!indexArray.getJSONArray(1).contains(result.charAt(1))) {
                indexArray.getJSONArray(1).add(result.charAt(1));
            }
            if (!indexArray.getJSONArray(2).contains(result.charAt(2))) {
                indexArray.getJSONArray(2).add(result.charAt(2));
            }
            if (!indexArray.getJSONArray(3).contains(result.charAt(3))) {
                indexArray.getJSONArray(3).add(result.charAt(3));
            }

            if (indexArray.getJSONArray(0).size() == 10 && indexArray.getJSONArray(1).size() == 10 && indexArray.getJSONArray(2).size() == 10 && indexArray.getJSONArray(3).size() == 10) {
                break;
            }
            if (count >= 150) {
                logger.info("too many circulate");
                break;
            }
            interval = Integer.valueOf(result);
        }
        for (int i=0; i<4; i++) {
            JSONArray jsonArray = indexArray.getJSONArray(i);
            if (jsonArray.size() == 9) {
                logger.info("size9:{}", jsonArray);
                String str = "0123456789";
                for (int j=0; j<9; j++) {
                    String value = jsonArray.getString(j);
                    str = str.replace(value, "");
                    logger.info("size9 remove:{}, left:{}", value, str);
                }
                jsonArray.add(str);
            } else if (jsonArray.size() < 9) {
                for (int j=jsonArray.size(); j<10; j++) {
                    jsonArray.add("x");
                }
            }
        }
        return indexArray;
    }

    public String qishuIntervalIterationRender(String qishu, String firstInterval, JSONArray indexArray) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>查询期数:").append(qishu).append("</h2>");
        sb.append("<h2>间隔:").append(firstInterval).append("</h2>");
        for (int i=0; i<4; i++) {
            sb.append("<p style=\"font-size: 30px;\">位置").append(i).append("数据序列: ");
            JSONArray jsonArray = indexArray.getJSONArray(i);
            StringBuilder ssb = new StringBuilder();
            for (int j=0; j<jsonArray.size(); j++) {
                ssb.append(jsonArray.get(j));
            }
            sb.append(ssb);
            String num = ssb.toString();
            sb.append("  md5:").append(Md5Service.md5tonum(num));
            sb.append("</p>");
        }
        return sb.toString();
    }

    public List<JSONObject> qishuIntervalCalculate(int interval, String qishu, int times, String collection) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").is(qishu));
        if (!mongoTemplate.exists(query, collection)) {
            return new ArrayList<>();
        }
        int totalCount = interval * times;
        query = new Query();
        query.addCriteria(Criteria.where("no").lte(qishu));
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(totalCount);
        List<JSONObject> cqssc = mongoTemplate.find(query, JSONObject.class, collection);
        return cqssc;
    }

    /**
     * 百里挑一
     * @param no 开始期数
     * @param m 连续多少期
     * @return d 相邻都不相同的最大间隔
     */
    public String notSame(String no, Integer m, String collection) {

        StringBuilder sb = new StringBuilder();

        for (int index = 1; index <= 5; index++) {
            int d=0;
            boolean same = true;
            List<JSONObject> cqssc = null;
            while (same) {
                d += 1;
                Query query = new Query();
                query.addCriteria(Criteria.where("no").lte(no));
                query.fields().include("no").include("result");
                query.with(new Sort(Sort.Direction.DESC, "no")).limit(m * d);
                cqssc = mongoTemplate.find(query, JSONObject.class, collection);
                String last = null;
                same = false;
                logger.info("d:{}", d);
                for (int i=(d-1); i<cqssc.size(); i+=d) {
                    JSONObject json = cqssc.get(i);
                    String res = json.getString("result").substring(index-1, index);
                    logger.info("no:{}, res:{}", json.getString("no"), res);
                    if (res.equals(last)) { //出现连续相同的数则d+1
                        same = true;
                        break;
                    }
                    last = res;
                }
            }
            sb.append("<p>位置").append(index).append(":").append(d).append("</p>");
            for (int i=(d-1); i<cqssc.size(); i+=d) {
                sb.append("<p>").append("no:").append(cqssc.get(i).getString("no")).append("  res:").append(cqssc.get(i).getString("result")).append("</p>");
            }
        }
        return sb.toString();
    }

    /**
     * 百里挑一
     * @param no 开始期数
     * @param m 连续多少期
     * @param index 位置
     * @return d 相邻都不相同的最大间隔
     */
    public JSONObject notSamePlus(String no, Integer m, Integer index, String collection) {

        StringBuilder sb = new StringBuilder();
        int d=0;
        boolean same = true;
        List<JSONObject> cqssc = null;
        String lastRepeatHtml = null;
        while (same) {
            d += 1;
            Query query = new Query();
            query.addCriteria(Criteria.where("no").lte(no));
            query.fields().include("no").include("result");
            query.with(new Sort(Sort.Direction.DESC, "no")).limit(m * d + d);
            cqssc = mongoTemplate.find(query, JSONObject.class, collection);
            String last = null;
            String latest = cqssc.get(cqssc.size()-1).getString("result").substring(index-1, index);
            same = false;
//            logger.info("d:{}", d);
            for (int i=(d-1); i<cqssc.size()-d; i+=d) {
                JSONObject json = cqssc.get(i);
                String res = json.getString("result").substring(index-1, index);
//                logger.info("no:{}, res:{}", json.getString("no"), res);
                if (res.equals(last)) { //出现连续相同的数则d+1
                    same = true;
                    break;
                }
                last = res;
            }
            if (same) {
                continue;
            }
            //n位和n+d位相同
            if (last.equals(latest)) {
                lastRepeatHtml = "<p>no:" +  cqssc.get(cqssc.size()-1).getString("no") + " res:" + cqssc.get(cqssc.size()-1).getString("result") + "</p>";
            } else {
                same = true;
            }
        }
        JSONObject result = new JSONObject();
        result.put("list", cqssc);
        result.put("d", d);
        result.put("lastRepeatHtml", lastRepeatHtml);
//        sb.append("<p>位置").append(index).append("  d:").append(d).append(" m:").append(m).append("</p>");
//        for (int i=(d-1); i<cqssc.size()-d; i+=d) {
//            sb.append("<p>").append("no:").append(cqssc.get(i).getString("no")).append("  res:").append(cqssc.get(i).getString("result")).append("</p>");
//        }
//        sb.append("<p>m+1位：</p>");
//        sb.append(lastRepeatHtml);
        return result;
    }

    public String notSameV3(String no, int count, String collection) {

        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(count + 1);
        List<JSONObject> list = mongoTemplate.find(query, JSONObject.class, collection);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<count; i++) {
            String thisNo = list.get(i).getString("no");
            String code2 = calculateNotSameV3(thisNo, 2, collection);
            String code2Last = code2.substring(code2.length()-1);
            String code3 = calculateNotSameV3(thisNo, 3, collection);
            String code3Last = code3.substring(code3.length()-1);
            String code4 = calculateNotSameV3(thisNo, 4, collection);
            String code4Last = code4.substring(code4.length()-1);
            String code5 = calculateNotSameV3(thisNo, 5, collection);
            String code5Last = code5.substring(code5.length()-1);
            String code = code2Last + code3Last + code4Last + code5Last;
            String lastResult = list.get(i+1).getString("result");
            String lastNo = list.get(i+1).getString("no");
            logger.info("{}期计算结果:{}, {}期号码:{}", thisNo, code, lastNo, lastResult);
            String md52 = Md5Service.md5tonum(code2);
            String md53 = Md5Service.md5tonum(code3);
            String md54 = Md5Service.md5tonum(code4);
            String md55 = Md5Service.md5tonum(code5);
            sb.append("<p>位置2数序列:").append(code2).append("  md5转换:").append(md52).append("</p>");
            sb.append("<p>位置3数序列:").append(code3).append("  md5转换:").append(md53).append("</p>");
            sb.append("<p>位置4数序列:").append(code4).append("  md5转换:").append(md54).append("</p>");
            sb.append("<p>位置5数序列:").append(code5).append("  md5转换:").append(md55).append("</p>");
            sb.append("<p>").append(thisNo).append("期计算结果:").append(code).append(", ").append(lastNo).append("期号码:").append(lastResult).append("</p>");
            sb.append("<p>");
            for (int j=0; j<10; j++) {
                String colj = code2.substring(j, j+1) + code3.substring(j, j+1) + code4.substring(j, j+1) + code5.substring(j, j+1);
                sb.append(colj).append(" ");
            }
            sb.append("</p>");
        }

        return sb.toString();
    }

    public String calculateNotSameV3(String no, int index, String collection) {
        logger.info("calculateNotSameV3 thisNo:{}, index:{} ", no, index);
        StringBuilder sb = new StringBuilder();
        int m = 1;
        JSONObject numberMap = new JSONObject();
        while (true) {
            JSONObject notSamePlus = notSamePlus(no, m, index, collection);
            List<JSONObject> list = (List<JSONObject>) notSamePlus.get("list");
            int d = notSamePlus.getIntValue("d");
            String first = list.get(d-1).getString("result");
            first = first.substring(index-1, index);
            if (!numberMap.containsKey(first)) {
                sb.append(first);
                numberMap.put(first, "ok");
            }
            logger.info("no:{},index:{},m:{},d:{},size:{},sb:{}", no, index, m, d, numberMap.size(), sb.toString());
            if (numberMap.size() == 9) {
                String temp = "1234567890";
                for (String key : numberMap.keySet()) {
                    temp = temp.replace(key, "");
                }
                logger.info("last:{}", temp);
                sb.append(temp);
//                return temp;
                return sb.toString();
            }
            m += 1;
            if (m >= 39) {
                logger.info("m is too large");
                break;
            }
        }
        sb.append("x");
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
            JSONArray d1Array = orderTailCalculate(1, null, c1, 30000, collection);
            JSONArray d2Array = orderTailCalculate(2, null, c2, 30000, collection);
            JSONArray d3Array = orderTailCalculate(3, null, c3, 30000, collection);
            JSONArray d4Array = orderTailCalculate(4, null, c4, 30000, collection);

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


    public String algorithm01Pl5(String no, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(count);
        List<JSONObject> pl5List = mongoTemplate.find(query, JSONObject.class, "pl5");
        StringBuilder html = new StringBuilder();

        String[] lastNo = new String[4];
        for (JSONObject cqssc : pl5List) {
            String result = cqssc.getString("result");
            String currNo = cqssc.getString("no");
            //第no期开奖号码后四位
            String a1 = result.substring(0, 1);
            String a2 = result.substring(1, 2);
            String a3 = result.substring(2, 3);
            String a4 = result.substring(3, 4);

            logger.info("algorithm01 第{}期前四位A: {}{}{}{}", currNo, a1, a2, a3, a4);
            query = new Query();
            query.addCriteria(Criteria.where("no").lte(currNo));
            query.with(new Sort(Sort.Direction.DESC, "no")).limit(5000);
            List<JSONObject> originList = mongoTemplate.find(query, JSONObject.class, "pl5");

            String b1 = null;
            String b2 = null;
            String b3 = null;
            String b4 = null;

            for (int i=1; i<originList.size(); i++) {
                JSONObject origin = originList.get(i);
                String originResult = origin.getString("result");
                if (b1 == null && originResult.substring(0, 1).equals(a1)) {
                    b1 = originList.get(i-1).getString("result").substring(1);
                }
                if (b2 == null && originResult.substring(1, 2).equals(a2)) {
                    b2 = originList.get(i-1).getString("result").substring(1);
                }
                if (b3 == null && originResult.substring(2, 3).equals(a3)) {
                    b3 = originList.get(i-1).getString("result").substring(1);
                }
                if (b4 == null && originResult.substring(3, 4).equals(a4)) {
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
            JSONArray d1Array = orderTailCalculate(0, null, c1, 30000, "pl5");
            JSONArray d2Array = orderTailCalculate(1, null, c2, 30000, "pl5");
            JSONArray d3Array = orderTailCalculate(2, null, c3, 30000, "pl5");
            JSONArray d4Array = orderTailCalculate(3, null, c4, 30000, "pl5");

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
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(10000);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        JSONArray resultArray12 = abxxnCalculate(1, 2, code, count, cqsscList);
        JSONArray resultArray13 = abxxnCalculate(1, 3, code, count, cqsscList);
        JSONArray resultArray14 = abxxnCalculate(1, 4, code, count, cqsscList);
        JSONArray resultArray23 = abxxnCalculate(2, 3, code, count, cqsscList);
        JSONArray resultArray24 = abxxnCalculate(2, 4, code, count, cqsscList);
        JSONArray resultArray34 = abxxnCalculate(3, 4, code, count, cqsscList);

        String html = abxxnRender(resultArray12, resultArray13, resultArray14, resultArray23, resultArray24, resultArray34);
        return html;
    }

    public JSONArray abxxnApi(String code, int count) {

        Query query = new Query();
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(10000);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        JSONArray resultArray12 = abxxnCalculate(1, 2, code, count, cqsscList);
        JSONArray resultArray13 = abxxnCalculate(1, 3, code, count, cqsscList);
        JSONArray resultArray14 = abxxnCalculate(1, 4, code, count, cqsscList);
        JSONArray resultArray23 = abxxnCalculate(2, 3, code, count, cqsscList);
        JSONArray resultArray24 = abxxnCalculate(2, 4, code, count, cqsscList);
        JSONArray resultArray34 = abxxnCalculate(3, 4, code, count, cqsscList);
        JSONArray result = new JSONArray();
        result.add(resultArray12);
        result.add(resultArray13);
        result.add(resultArray14);
        result.add(resultArray23);
        result.add(resultArray24);
        result.add(resultArray34);
        return result;

    }

    public String abxxnV2(String no, String code, int count) {

        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(10000);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        JSONArray resultArray12 = abxxnCalculate(1, 2, code, count, cqsscList);
        JSONArray resultArray13 = abxxnCalculate(1, 3, code, count, cqsscList);
        JSONArray resultArray14 = abxxnCalculate(1, 4, code, count, cqsscList);
        JSONArray resultArray23 = abxxnCalculate(2, 3, code, count, cqsscList);
        JSONArray resultArray24 = abxxnCalculate(2, 4, code, count, cqsscList);
        JSONArray resultArray34 = abxxnCalculate(3, 4, code, count, cqsscList);

        DecimalFormat df = new DecimalFormat("0000");
        List<String> handledNumbers = new ArrayList<>(10000);
        for (int i=0; i<10000; i++) {
            handledNumbers.add(df.format(i));
        }

        for (int i=handledNumbers.size()-1; i>=0; i--) {
            String number = handledNumbers.get(i);

            if (abxxnV2Filt(resultArray12, 1, 2, number)) {
                handledNumbers.remove(i);
                continue;
            }
            if (abxxnV2Filt(resultArray13, 1, 3, number)) {
                handledNumbers.remove(i);
                continue;
            }
            if (abxxnV2Filt(resultArray14, 1, 4, number)) {
                handledNumbers.remove(i);
                continue;
            }
            if (abxxnV2Filt(resultArray23, 2, 3, number)) {
                handledNumbers.remove(i);
                continue;
            }
            if (abxxnV2Filt(resultArray24, 2, 4, number)) {
                handledNumbers.remove(i);
                continue;
            }
            if (abxxnV2Filt(resultArray34, 3, 4, number)) {
                handledNumbers.remove(i);
            }

        }

        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        for (int i=0; i<handledNumbers.size(); i++) {
            String number = handledNumbers.get(i);
            sb.append(number).append(" ");
        }
        sb.append("</p>");
        return sb.toString();
    }

    private boolean abxxnV2Filt(JSONArray resultArray, int a, int b, String result) {
        for (int j=0; j<resultArray.size(); j++) {
            String filterResult = resultArray.getJSONObject(j).getString("result");
            char[] chars = result.toCharArray();
            char[] filtChars = filterResult.toCharArray();
            if (chars[a-1] == filtChars[a] && chars[b-1] == filtChars[b]) {
                logger.info("abxxnV2Filt remove:{}", result);
                return true;
            }
        }
        return false;
    }


    private JSONArray abxxnCalculate(int indexA, int indexB, String code, int count, List<JSONObject> cqsscList) {

        JSONArray resultArray = new JSONArray();
        String a = code.substring(indexA-1, indexA);
        String b = code.substring(indexB-1, indexB);
        int currentCount = 0;

        for (int j=1; j<cqsscList.size()-1; j++) {
            JSONObject cqssc = cqsscList.get(j);
            String result = cqssc.getString("result");
            String no = cqssc.getString("no");
//            result = result.substring(1);
            String aa = result.substring(indexA, indexA + 1);
            String bb = result.substring(indexB, indexB + 1);
            if (a.equals(aa) && b.equals(bb)) {
                logger.info("{} {} code:{}, no:{}, result:{}", indexA, indexB, code, no, result);
                resultArray.add(new JSONObject()
                        .fluentPut("no", cqsscList.get(j-1).getString("no"))
                        .fluentPut("result", cqsscList.get(j-1).getString("result"))
                        .fluentPut("index", String.format("%03d", currentCount)));
                currentCount++;
            }
            if (count <= currentCount) {
                break;
            }
        }
        return resultArray;
    }

    private String abxxnRender(JSONArray resultArray12, JSONArray resultArray13, JSONArray resultArray14, JSONArray resultArray23, JSONArray resultArray24, JSONArray resultArray34) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>第1,2位").append("</p>");
        for (int i=0; i<resultArray12.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray12.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray12.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        sb.append("<p>第1,3位").append("</p>");
        for (int i=0; i<resultArray13.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray13.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray13.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        sb.append("<p>第1,4位").append("</p>");
        for (int i=0; i<resultArray14.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray14.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray14.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        sb.append("<p>第2,3位").append("</p>");
        for (int i=0; i<resultArray23.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray23.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray23.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        sb.append("<p>第2,4位").append("</p>");
        for (int i=0; i<resultArray24.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray24.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray24.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        sb.append("<p>第3,4位").append("</p>");
        for (int i=0; i<resultArray34.size(); i++) {
            sb.append("<p>")
                    .append(String.format("%03d", i))
                    .append("期号:")
                    .append(resultArray34.getJSONObject(i).getString("no"))
                    .append("   号码：")
                    .append(resultArray34.getJSONObject(i).getString("result"))
                    .append("</p>");
        }
        return sb.toString();
    }


    public String abxxn2(String code) {

        Query query = new Query();
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(60000);
        List<JSONObject> cqsscList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        StringBuilder sb = new StringBuilder();
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;
        StringBuilder suffixResult = new StringBuilder();
        for (int i=1; i<cqsscList.size(); i++) {
            String result = cqsscList.get(i).getString("result");
            if (result.endsWith(code)) {
                JSONObject output = cqsscList.get(i-1);
                String result1 = output.getString("result");
                sum1 += Integer.valueOf(result1.substring(1, 2));
                sum2 += Integer.valueOf(result1.substring(2, 3));
                sum3 += Integer.valueOf(result1.substring(3, 4));
                sum4 += Integer.valueOf(result1.substring(4, 5));
                sb.append("<p>")
                        .append("期号:")
                        .append(output.getString("no"))
                        .append("   号码：")
                        .append(result1)
                        .append("</p>");
                suffixResult.append(result1.substring(1)).append(" ");
            }
        }
        sb.append("<p>").append(suffixResult.toString()).append("</p>");
        sb.append("<p>").append("位置1和:").append(sum1).append("</p>");
        sb.append("<p>").append("位置2和:").append(sum2).append("</p>");
        sb.append("<p>").append("位置3和:").append(sum3).append("</p>");
        sb.append("<p>").append("位置4和:").append(sum4).append("</p>");
        return sb.toString();
    }

    public JSONArray abxxnApiFilt(JSONArray filterArray) {

        //从0000-9999
        DecimalFormat df = new DecimalFormat("0000");
        JSONArray handledNumbers = new JSONArray(10000);
        for (int i=0; i<10000; i++) {
            handledNumbers.add(df.format(i));
        }

        for (int i=0; i<filterArray.size(); i++) {
            JSONObject input = filterArray.getJSONObject(i);
            int a = input.getIntValue("a");
            int b = input.getIntValue("b");
            String result = input.getString("result");
            char[] resultChars = result.toCharArray();
            for (int j=handledNumbers.size()-1; j>=0; j--) {
                String number = handledNumbers.getString(j);
                char[] numberChars = number.toCharArray();
                if (numberChars[a-1] == resultChars[a] && numberChars[b-1] == resultChars[b]) {
                    handledNumbers.remove(j);
                }
            }
        }

        return handledNumbers;
    }

    public String compose01(String no, int count, int circle) {

        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(count);
        List<JSONObject> xywfcList = mongoTemplate.find(query, JSONObject.class, "xywfc");
        StringBuilder sb = new StringBuilder();
        for (int j=0; j<xywfcList.size(); j++) {
            JSONObject first = xywfcList.get(j);
            String firstRes = first.getString("result");
            no = first.getString("no");
            int interval = Integer.valueOf(firstRes.substring(1));
            JSONArray aArray = qishuIntervalIteration(interval, no, "xywfc");
            StringBuilder sba = new StringBuilder();
            for (int i=0; i<4; i++) {
                JSONArray jsonArray = aArray.getJSONArray(i);
                StringBuilder ssb = new StringBuilder();
                for (int k=0; k<jsonArray.size(); k++) {
                    ssb.append(jsonArray.get(k));
                }
                String num = ssb.toString();
                String md5 = Md5Service.md5tonum(num);
                int v1 = Integer.parseInt(String.valueOf(md5.charAt(0)));
                int v2 = Integer.parseInt(String.valueOf(md5.charAt(1)));
                int v3 = Integer.parseInt(String.valueOf(md5.charAt(2)));
                int v4 = Integer.parseInt(String.valueOf(md5.charAt(3)));
                //求和取个位
                int v = (v1 + v2 + v3 + v4) % 10;
                sba.append(v);
            }
            List<String> abcdList = new ArrayList<>(circle + 1);
            List<String> efghList = new ArrayList<>(circle + 1);
            List<String> ijklList = new ArrayList<>(circle + 1);

            String abcd = sba.toString();
            abcdList.add(abcd);
            for (int i=0; i<circle; i++) {
                String abcd2 = Md5Service.md5tonum(abcd);
                abcdList.add(abcd2);
                abcd = abcd2;
            }
//            String abcd2 = Md5Service.md5tonum(abcd);
//            String abcd3 = Md5Service.md5tonum(abcd2);

            String e = no + firstRes.substring(1);
            String efgh = Md5Service.md5tonum(e);
            efghList.add(efgh);
            for (int i=0; i<circle; i++) {
                String efgh2 = Md5Service.md5tonum(efgh);
                efghList.add(efgh2);
                efgh = efgh2;
            }
//            String efgh2 = Md5Service.md5tonum(efgh);
//            String efgh3 = Md5Service.md5tonum(efgh2);

            String i3 = no + firstRes;
            String ijkl = Md5Service.md5tonum(i3);
//            String ijkl2 = Md5Service.md5tonum(ijkl);
//            String ijkl3 = Md5Service.md5tonum(ijkl2);
            ijklList.add(ijkl);
            for (int i=0; i<circle; i++) {
                String ijkl2 = Md5Service.md5tonum(ijkl);
                ijklList.add(ijkl2);
                ijkl = ijkl2;
            }

            sb.append("<p>").append(no).append("期结果: ").append(firstRes).append(" <br>A:<br>");
            for (int i=0; i<abcdList.size(); i++) {
                sb.append(abcdList.get(i)).append(" ");
            }
            sb.append(" <br>B:<br> ");
            for (int i=0; i<efghList.size(); i++) {
                sb.append(efghList.get(i)).append(" ");
            }
            sb.append(" <br>C:<br> ");
            for (int i=0; i<ijklList.size(); i++) {
                sb.append(ijklList.get(i)).append(" ");
            }
            sb.append("</p>");
        }
        return sb.toString();
    }


    public String compose01Xj(String no, int count, int circle) {

        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.fields().include("no").include("result");
        query.with(new Sort(Sort.Direction.DESC, "no")).limit(count);
        List<JSONObject> xywfcList = mongoTemplate.find(query, JSONObject.class, "xjssc");
        StringBuilder sb = new StringBuilder();
        for (int j=0; j<xywfcList.size(); j++) {
            JSONObject first = xywfcList.get(j);
            String firstRes = first.getString("result");
            no = first.getString("no");
            int interval = Integer.valueOf(firstRes.substring(1));
            JSONArray aArray = qishuIntervalIteration(interval, no, "xjssc");
            StringBuilder sba = new StringBuilder();
            for (int i=0; i<4; i++) {
                JSONArray jsonArray = aArray.getJSONArray(i);
                StringBuilder ssb = new StringBuilder();
                for (int k=0; k<jsonArray.size(); k++) {
                    ssb.append(jsonArray.get(k));
                }
                String num = ssb.toString();
                String md5 = Md5Service.md5tonum(num);
                int v1 = Integer.parseInt(String.valueOf(md5.charAt(0)));
                int v2 = Integer.parseInt(String.valueOf(md5.charAt(1)));
                int v3 = Integer.parseInt(String.valueOf(md5.charAt(2)));
                int v4 = Integer.parseInt(String.valueOf(md5.charAt(3)));
                //求和取个位
                int v = (v1 + v2 + v3 + v4) % 10;
                sba.append(v);
            }
            List<String> abcdList = new ArrayList<>(circle + 1);
            List<String> efghList = new ArrayList<>(circle + 1);
            List<String> ijklList = new ArrayList<>(circle + 1);

            String abcd = sba.toString();
            abcdList.add(abcd);
            for (int i=0; i<circle; i++) {
                String abcd2 = Md5Service.md5tonum(abcd);
                abcdList.add(abcd2);
                abcd = abcd2;
            }
//            String abcd2 = Md5Service.md5tonum(abcd);
//            String abcd3 = Md5Service.md5tonum(abcd2);

            String e = no + firstRes.substring(1);
            String efgh = Md5Service.md5tonum(e);
            efghList.add(efgh);
            for (int i=0; i<circle; i++) {
                String efgh2 = Md5Service.md5tonum(efgh);
                efghList.add(efgh2);
                efgh = efgh2;
            }
//            String efgh2 = Md5Service.md5tonum(efgh);
//            String efgh3 = Md5Service.md5tonum(efgh2);

            String i3 = no + firstRes;
            String ijkl = Md5Service.md5tonum(i3);
//            String ijkl2 = Md5Service.md5tonum(ijkl);
//            String ijkl3 = Md5Service.md5tonum(ijkl2);
            ijklList.add(ijkl);
            for (int i=0; i<circle; i++) {
                String ijkl2 = Md5Service.md5tonum(ijkl);
                ijklList.add(ijkl2);
                ijkl = ijkl2;
            }

            sb.append("<p>").append(no).append("期结果: ").append(firstRes).append(" <br>A:<br>");
            for (int i=0; i<abcdList.size(); i++) {
                sb.append(abcdList.get(i)).append(" ");
            }
            sb.append(" <br>B:<br> ");
            for (int i=0; i<efghList.size(); i++) {
                sb.append(efghList.get(i)).append(" ");
            }
            sb.append(" <br>C:<br> ");
            for (int i=0; i<ijklList.size(); i++) {
                sb.append(ijklList.get(i)).append(" ");
            }
            sb.append("</p>");
        }
        return sb.toString();
    }

    public String compose02(String number) {
        String[] list = number.split(" ");
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();
        StringBuilder s4 = new StringBuilder();
        for (String l : list) {
            s1.append(l, 0, 1);
            s2.append(l, 1, 2);
            s3.append(l, 2, 3);
            s4.append(l, 3, 4);
        }
        String a = s1.toString();
        String b = s2.toString();
        String c = s3.toString();
        String d = s4.toString();
        HashSet<String> data = new HashSet<>();
        for (int i=0; i<list.length; i++) {
            for (int j=0; j<list.length; j++) {
                for (int k=0; k<list.length; k++) {
                    for (int l=0; l<list.length; l++) {
                        String s = String.valueOf(a.charAt(i)) + String.valueOf(b.charAt(j)) + String.valueOf(c.charAt(k)) + String.valueOf(d.charAt(l));
                        data.add(s);
                    }
                }
            }
        }
        StringBuilder res = new StringBuilder();
        Iterator<String> iterator = data.iterator();
        while (iterator.hasNext()) {
            res.append("<span>").append(iterator.next()).append("  ").append("</span>");
        }
        return res.toString();
    }

    public String compose03(String number) {

        String[] split = number.split(" ");

        String results1000 = "xxxxxxxxxx";
        String results100 = "xxxxxxxxxx";
        String results10 = "xxxxxxxxxx";
        String results1 = "xxxxxxxxxx";
        for (int i=0; i < split.length-1; i++) {
            String num = split[i];
            String nextNum = split[i+1];
            Integer integer1000 = Integer.valueOf(num.substring(0, 1));
            Integer integer100 = Integer.valueOf(num.substring(1, 2));
            Integer integer10 = Integer.valueOf(num.substring(2, 3));
            Integer integer1 = Integer.valueOf(num.substring(3, 4));

            Integer nextNumInteger1000 = Integer.valueOf(nextNum.substring(0, 1));
            Integer nextNumInteger100 = Integer.valueOf(nextNum.substring(1, 2));
            Integer nextNumInteger10 = Integer.valueOf(nextNum.substring(2, 3));
            Integer nextNumInteger1 = Integer.valueOf(nextNum.substring(3, 4));

            int sum1000 = integer1000 + nextNumInteger1000;
            String sumStr1000 = sum1000 + "";
            String last1000 = sumStr1000.substring(sumStr1000.length() - 1);
            if (!results1000.contains(last1000)) {
                results1000 = results1000.replaceFirst("x", last1000);
            }

            int sum100 = integer100 + nextNumInteger100;
            String sumStr100 = sum100 + "";
            String last100 = sumStr100.substring(sumStr100.length() - 1);
            if (!results100.contains(last100)) {
                results100 = results100.replaceFirst("x", last100);
            }

            int sum10 = integer10 + nextNumInteger10;
            String sumStr10 = sum10 + "";
            String last10 = sumStr10.substring(sumStr10.length() - 1);
            if (!results10.contains(last10)) {
                results10 = results10.replaceFirst("x", last10);
            }

            int sum1 = integer1 + nextNumInteger1;
            String sumStr1 = sum1 + "";
            String last1 = sumStr1.substring(sumStr1.length() - 1);
            if (!results1.contains(last1)) {
                results1 = results1.replaceFirst("x", last1);
            }


        }
        StringBuilder res = new StringBuilder();
        res.append("<span style=\"font-size: 22px;\">千位：").append(results1000.toString())
                .append("  <br>百位：").append(results100.toString())
                .append("  <br>十位：").append(results10.toString())
                .append("  <br>个位：").append(results1.toString())
                .append("</span>");
        return res.toString();
    }
}
