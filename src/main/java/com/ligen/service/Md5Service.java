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

    public static String MD5(String sourceStr) {
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

    public static String md5tonum(String source) {

        String md5 = MD5(source);
//        let v1 = value.slice(0, 8)
//        let v2 = value.slice(8, 16)
//        let v3 = value.slice(16, 24)
//        let v4 = value.slice(24, 32)
        String v1 = md5.substring(0, 8);
        String v2 = md5.substring(8, 16);
        String v3 = md5.substring(16, 24);
        String v4 = md5.substring(24, 32);
//        console.log(v1, v2, v3, v4)
//        v1 = v1.replace(/[^0-9]/ig,"")
//        v2 = v2.replace(/[^0-9]/ig,"")
//        v3 = v3.replace(/[^0-9]/ig,"")
//        v4 = v4.replace(/[^0-9]/ig,"")

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);

        Matcher m1 = p.matcher(v1);
        Matcher m2 = p.matcher(v2);
        Matcher m3 = p.matcher(v3);
        Matcher m4 = p.matcher(v4);
        v1 = m1.replaceAll("");
        v2 = m2.replaceAll("");
        v3 = m3.replaceAll("");
        v4 = m4.replaceAll("");

        int t1 = 0;
        int t2 = 0;
        int t3 = 0;
        int t4 = 0;
//        console.log(v1, v2, v3, v4)
//        let t1 = 0
//        let t2 = 0
//        let t3 = 0
//        let t4 = 0
//        for (let i=0; i<v1.length; i++) {
//            let v = parseInt(v1.charAt(i))
//            t1 += v
//        }
        for (int i=0; i<v1.length(); i++) {
            int v = Integer.parseInt(String.valueOf(v1.charAt(i)));
            t1 += v;
        }
//        for (let i=0; i<v2.length; i++) {
//            let v = parseInt(v2.charAt(i))
//            t2 += v
//        }
        for (int i=0; i<v2.length(); i++) {
            int v = Integer.parseInt(String.valueOf(v2.charAt(i)));
            t2 += v;
        }
//        for (let i=0; i<v3.length; i++) {
//            let v = parseInt(v3.charAt(i))
//            t3 += v
//        }
        for (int i=0; i<v3.length(); i++) {
            int v = Integer.parseInt(String.valueOf(v3.charAt(i)));
            t3 += v;
        }
//        for (let i=0; i<v4.length; i++) {
//            let v = parseInt(v4.charAt(i))
//            t4 += v
//        }
        for (int i=0; i<v4.length(); i++) {
            int v = Integer.parseInt(String.valueOf(v4.charAt(i)));
            t4 += v;
        }
//        let r1 = t1 + ""
//        let r2 = t2 + ""
//        let r3 = t3 + ""
//        let r4 = t4 + ""
        String r1 = t1 + "";
        String r2 = t2 + "";
        String r3 = t3 + "";
        String r4 = t4 + "";
//        r1 = r1.charAt(r1.length - 1)
//        r2 = r2.charAt(r2.length - 1)
//        r3 = r3.charAt(r3.length - 1)
//        r4 = r4.charAt(r4.length - 1)

        r1 = r1.charAt(r1.length() - 1) + "";
        r2 = r2.charAt(r2.length() - 1) + "";
        r3 = r3.charAt(r3.length() - 1) + "";
        r4 = r4.charAt(r4.length() - 1) + "";
//        document.getElementById("result").value=r1 + r2 + r3 + r4;
        return r1 + r2 + r3 + r4;
    }


}
