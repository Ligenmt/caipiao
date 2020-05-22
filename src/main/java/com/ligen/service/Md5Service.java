package com.ligen.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Md5Service {

    @Autowired
    MongoTemplate mongoTemplate;

    public String md5(String no, Integer count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").lte(no));
        query.with(new Sort(Sort.Direction.DESC, "_id")).limit(count);
        List<JSONObject> dataList = mongoTemplate.find(query, JSONObject.class, "cqssc");

        StringBuilder sb = new StringBuilder();
        for (JSONObject data : dataList) {
            String numbers = data.getString("result");
            String dataNo = data.getString("no");
            String md5 = MD5(numbers.substring(1));
            Pattern p = Pattern.compile("[^0-9]");
            Matcher m32 = p.matcher(md5);
            String md532Num = m32.replaceAll("").trim();
            String md516 = md5.substring(8, 24);

            Matcher m16 = p.matcher(md516);
            String md516Num = m16.replaceAll("").trim();

            sb.append("<p>期数:").append(dataNo).append("   号码:").append(numbers).append("   32位: ").append(md532Num.substring(0, 4)).append("   16位: ").append(md516Num.substring(0, 4)).append("</p>");
        }
        return sb.toString();
    }

    private static String MD5(String sourceStr) {
        String result = "";//通过result返回结果值
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//1.初始化MessageDigest信息摘要对象,并指定为MD5不分大小写都可以
            md.update(sourceStr.getBytes());//2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],将字符串转换为字节数组使用getBytes()方法完成
            byte b[] = md.digest();//3.计算信息摘要digest()方法,返回值为字节数组

            int i;//定义整型
            //声明StringBuffer对象
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];//将首个元素赋值给i
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");//前面补0
                buf.append(Integer.toHexString(i));//转换成16进制编码
            }
            result = buf.toString();//转换成字符串
            System.out.println("MD5(" + sourceStr + ",32) = " + result);//输出32位16进制字符串
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));//输出16位16进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;//返回结果
    }
}
