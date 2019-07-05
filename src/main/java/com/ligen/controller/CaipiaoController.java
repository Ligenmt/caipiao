package com.ligen.controller;

import com.alibaba.fastjson.JSONObject;
import com.ligen.service.CaipiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ligen on 2018/3/7.
 */
@Controller
public class CaipiaoController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CaipiaoService caipiaoService;

    @RequestMapping(value = "pl5", method = RequestMethod.GET)
    public String pailie5Get() {
        return "pl5";
    }

    @RequestMapping(value = "pl5", method = RequestMethod.POST)
    @ResponseBody
    public String pailie5Post(HttpServletRequest request) {
        return caipiaoService.pl5(request);
    }

    @RequestMapping(value = "ynwfc", method = RequestMethod.GET)
    public String ynwfcGet() {
        return "ynwfc";
    }

    @RequestMapping(value = "ynwfc", method = RequestMethod.POST)
    @ResponseBody
    public String ynwfcPost(HttpServletRequest request) {
        String ynwfc = caipiaoService.ynwfc(request);
        return ynwfc;
    }

    @RequestMapping(value = "ynffc", method = RequestMethod.GET)
    public String ynffcGet() {
        return "ynffc";
    }

    @RequestMapping(value = "ynffc", method = RequestMethod.POST)
    @ResponseBody
    public String ynffcPost(HttpServletRequest request) {
        String ynffc = caipiaoService.ynffc(request);
        return ynffc;
    }

    @RequestMapping(value = "cqssc", method = RequestMethod.GET)
    public String cqsscGet() {
        return "cqssc";
    }

    @RequestMapping(value = "cqssc", method = RequestMethod.POST)
    @ResponseBody
    public String cqsscPost(HttpServletRequest request) {
        return caipiaoService.cqssc(request);
    }

    @RequestMapping(value = "cqssc_v2", method = RequestMethod.GET)
    public String cqsscGetV2() {
        return "cqssc_v2";
    }

    @RequestMapping(value = "cqssc_v2", method = RequestMethod.POST)
    @ResponseBody
    public String cqsscPostV2(HttpServletRequest request) {
        return caipiaoService.cqsscV2(request);
    }

    @RequestMapping(value = "cqssc_filter", method = RequestMethod.GET)
    public String cqsscFilterGet() {
        return "cqssc_filter";
    }

    /**
     *
     * @param count 过滤出重复号码
     * @param times 重复次数（最小2）
     * @param remove 移除位数
     * @return
     */
    @RequestMapping(value = "cqssc_filter", method = RequestMethod.POST)
    @ResponseBody
    public String cqsscFilterPost(@RequestParam("count") int count, @RequestParam("times") int times, @RequestParam("remove") int remove) {
        return caipiaoService.cqsscFilter(count, times, remove-1);
    }

    /**
     * 输入多批数据取交集
     * @return
     */
    @RequestMapping(value = "intersection", method = RequestMethod.GET)
    public String compareGet() {
        return "intersection";
    }

    @RequestMapping(value = "intersection", method = RequestMethod.POST)
    @ResponseBody
    public String comparePost(@RequestParam(value = "numbers") String numbers, @RequestParam(value = "repeat") int repeat) {

        String[] split = numbers.split(" ");
        JSONObject json = new JSONObject();

        StringBuilder sb = new StringBuilder();

        for (String n : split) {
            json.put(n, json.getIntValue(n) + 1);
        }
        for (String n : json.keySet()) {
            int i = json.getIntValue(n);
            if (i >= repeat) {
                sb.append(n);
                if (n.length() == 2) {
                    sb.append("XX");
                } else if (n.length() == 3) {
                    sb.append("X");
                }
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @RequestMapping(value = "order_tail", method = RequestMethod.GET)
    public String orderTailGet() {
        return "order_tail";
    }

    @RequestMapping(value = "order_tail", method = RequestMethod.POST)
    @ResponseBody
    public String orderTailPost(@RequestParam(value = "index") int index,
                                @RequestParam(value = "code") String code,
                                @RequestParam(value = "count", required = false) Integer count,
                                @RequestParam(value = "collection", required = false, defaultValue = "cqssc") String collection) {
        String result = caipiaoService.orderTail(index, code, count, collection);
        return result;
    }

    @RequestMapping(value = "qishu_intertval", method = RequestMethod.GET)
    public String qishuIntervalGet() {
        return "qishu_intertval";
    }

    @RequestMapping(value = "qishu_intertval", method = RequestMethod.POST)
    @ResponseBody
    public String qishuIntervalPost(@RequestParam(value = "interval") int interval,
                                @RequestParam(value = "qishu") String qishu) {
        String result = caipiaoService.qishuInterval(interval, qishu);
        return result;
    }

    @RequestMapping(value = "not_same", method = RequestMethod.GET)
    public String notSameGet() {
        return "not_same";
    }

    @RequestMapping(value = "not_same", method = RequestMethod.POST)
    @ResponseBody
    public String notSamePost(@RequestParam(value = "no") String no,
                                @RequestParam(value = "m") Integer m,
                                @RequestParam(value = "no") String collection) {
        String result = caipiaoService.notSame(no, m, collection);
        return result;
    }

    @RequestMapping(value = "algorithm01", method = RequestMethod.GET)
    public String algorithm01Get() {
        return "algorithm01";
    }

    @RequestMapping(value = "algorithm01", method = RequestMethod.POST)
    @ResponseBody
    public String algorithm01Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
        return caipiaoService.algorithm01(no, count, "cqssc");
    }

    @RequestMapping(value = "algorithm01_xywfc", method = RequestMethod.GET)
    public String algorithm01XywfcGet() {
        return "algorithm01_xywfc";
    }

    @RequestMapping(value = "algorithm01_xywfc", method = RequestMethod.POST)
    @ResponseBody
    public String algorithm01XywfcPost(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
        return caipiaoService.algorithm01(no, count, "xywfc");
    }

    @RequestMapping(value = "algorithm01_pl5", method = RequestMethod.GET)
    public String algorithm01Pl5Get() {
        return "algorithm01_pl5";
    }

    @RequestMapping(value = "algorithm01_pl5", method = RequestMethod.POST)
    @ResponseBody
    public String algorithm01Pl5Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
        return caipiaoService.algorithm01Pl5(no, count);
    }

    @RequestMapping(value = "algorithm01_pl52", method = RequestMethod.GET)
    public String algorithm01Pl52Get() {
        return "algorithm01_pl52";
    }

    @RequestMapping(value = "algorithm01_pl52", method = RequestMethod.POST)
    @ResponseBody
    public String algorithm01Pl52Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
        return caipiaoService.algorithm01(no, count, "pl5");
    }

    @RequestMapping(value = "abxxn", method = RequestMethod.GET)
    public String abxxnGet() {
        return "abxxn";
    }

    @RequestMapping(value = "abxxn", method = RequestMethod.POST)
    @ResponseBody
    public String abxxnPost(@RequestParam(value = "code") String code, @RequestParam(value = "count") int count) {
        String abxxn = caipiaoService.abxxn(code, count);
        return abxxn;
    }

    @RequestMapping(value = "abxxn2", method = RequestMethod.GET)
    public String abxxn2Get() {
        return "abxxn2";
    }

    @RequestMapping(value = "abxxn2", method = RequestMethod.POST)
    @ResponseBody
    public String abxxn2Post(@RequestParam(value = "code") String code) {
        String abxxn2 = caipiaoService.abxxn2(code);
        return abxxn2;
    }

    @RequestMapping(value = "abxxnv2", method = RequestMethod.GET)
    public String abxxnV2Get() {
        return "abxxnv2";
    }

    @RequestMapping(value = "abxxnv2", method = RequestMethod.POST)
    @ResponseBody
    public String abxxnV2Post(@RequestParam(value = "code") String code, @RequestParam(value = "count") int count) {
        String abxxn = caipiaoService.abxxnV2(code, count);
        return abxxn;
    }
}
