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
import java.util.*;

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

    @RequestMapping(value = "cqssc_filter", method = RequestMethod.GET)
    public String cqsscFilterGet() {
        return "cqssc_filter";
    }

    /**
     *
     * @param count 过滤期数
     * @param times 重复次数（最小2）
     * @param remove 移除位数
     * @return
     */
    @RequestMapping(value = "cqssc_filter", method = RequestMethod.POST)
    @ResponseBody
    public String cqsscFilterPost(@RequestParam("count") int count, @RequestParam("times") int times, @RequestParam("remove") int remove) {
        return caipiaoService.cqsscFilter(count, times, remove-1);
    }

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
                                @RequestParam(value = "count", required = false) Integer count) {

        String result = caipiaoService.orderTail(index, code, count);
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
}
