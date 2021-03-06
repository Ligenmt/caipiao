//package com.ligen.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ligen.service.CaipiaoService;
//import com.ligen.service.Md5Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.index.Index;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * Created by ligen on 2018/3/7.
// */
//@Controller
//public class CaipiaoController {
//
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    CaipiaoService caipiaoService;
//
//    @Autowired
//    Md5Service md5Service;
//
//    @RequestMapping(value = "md5", method = RequestMethod.GET)
//    public String md5Get() {
//        return "md5";
//    }
//
//    @RequestMapping(value = "md52num", method = RequestMethod.GET)
//    public String md52numGet() {
//        return "md52num";
//    }
//
//    @RequestMapping(value = "md5", method = RequestMethod.POST)
//    @ResponseBody
//    public String md5Post(HttpServletRequest request) {
//        int count = Integer.parseInt(request.getParameter("count")); //过滤期数
//        String no = request.getParameter("no");  //待过滤号码
//        String result = md5Service.md5(no, count);
//
//        return result;
//    }
//
//    @RequestMapping(value = "pl5", method = RequestMethod.GET)
//    public String pailie5Get() {
//        return "pl5";
//    }
//
//    @RequestMapping(value = "pl5", method = RequestMethod.POST)
//    @ResponseBody
//    public String pailie5Post(HttpServletRequest request) {
//        return caipiaoService.pl5(request);
//    }
//
//    @RequestMapping(value = "ynwfc", method = RequestMethod.GET)
//    public String ynwfcGet() {
//        return "ynwfc";
//    }
//
//    @RequestMapping(value = "ynwfc", method = RequestMethod.POST)
//    @ResponseBody
//    public String ynwfcPost(HttpServletRequest request) {
//        String ynwfc = caipiaoService.ynwfc(request);
//        return ynwfc;
//    }
//
//    @RequestMapping(value = "ynffc", method = RequestMethod.GET)
//    public String ynffcGet() {
//        return "ynffc";
//    }
//
//    @RequestMapping(value = "ynffc", method = RequestMethod.POST)
//    @ResponseBody
//    public String ynffcPost(HttpServletRequest request) {
//        String ynffc = caipiaoService.ynffc(request);
//        return ynffc;
//    }
//
//    @RequestMapping(value = "cqssc", method = RequestMethod.GET)
//    public String cqsscGet() {
//        return "cqssc";
//    }
//
//    @RequestMapping(value = "cqssc", method = RequestMethod.POST)
//    @ResponseBody
//    public String cqsscPost(HttpServletRequest request) {
//        return caipiaoService.cqssc(request);
//    }
//
//    @RequestMapping(value = "cqssc_v2", method = RequestMethod.GET)
//    public String cqsscGetV2() {
//        return "cqssc_v2";
//    }
//
//    @RequestMapping(value = "cqssc_v2", method = RequestMethod.POST)
//    @ResponseBody
//    public String cqsscPostV2(HttpServletRequest request) {
//        return caipiaoService.cqsscV2(request);
//    }
//
//    @RequestMapping(value = "cqssc_filter", method = RequestMethod.GET)
//    public String cqsscFilterGet() {
//        return "cqssc_filter";
//    }
//
//    /**
//     *
//     * @param count 过滤出重复号码
//     * @param times 重复次数（最小2）
//     * @param remove 移除位数
//     * @return
//     */
//    @RequestMapping(value = "cqssc_filter", method = RequestMethod.POST)
//    @ResponseBody
//    public String cqsscFilterPost(@RequestParam("count") int count, @RequestParam("times") int times, @RequestParam("remove") int remove) {
//        return caipiaoService.cqsscFilter(count, times, remove-1);
//    }
//
//    /**
//     * 输入多批数据取交集
//     * @return
//     */
//    @RequestMapping(value = "intersection", method = RequestMethod.GET)
//    public String compareGet() {
//        return "intersection";
//    }
//
//    @RequestMapping(value = "intersection", method = RequestMethod.POST)
//    @ResponseBody
//    public String comparePost(@RequestParam(value = "numbers") String numbers, @RequestParam(value = "repeat") int repeat) {
//
//        String[] split = numbers.split(" ");
//        JSONObject json = new JSONObject();
//
//        StringBuilder sb = new StringBuilder();
//
//        for (String n : split) {
//            json.put(n, json.getIntValue(n) + 1);
//        }
//        for (String n : json.keySet()) {
//            int i = json.getIntValue(n);
//            if (i >= repeat) {
//                sb.append(n);
//                if (n.length() == 2) {
//                    sb.append("XX");
//                } else if (n.length() == 3) {
//                    sb.append("X");
//                }
//                sb.append(" ");
//            }
//        }
//        return sb.toString();
//    }
//
//    @RequestMapping(value = "order_tail", method = RequestMethod.GET)
//    public String orderTailGet() {
//        return "order_tail";
//    }
//
//    @RequestMapping(value = "order_tail", method = RequestMethod.POST)
//    @ResponseBody
//    public String orderTailPost(@RequestParam(value = "index") int index,
//                                @RequestParam(value = "code") String code,
//                                @RequestParam(value = "count", required = false) Integer count,
//                                @RequestParam(value = "collection", required = false, defaultValue = "cqssc") String collection) {
//        String result = caipiaoService.orderTail(index, code, count, collection);
//        return result;
//    }
//
//    @RequestMapping(value = "order_tail_v2", method = RequestMethod.GET)
//    public String orderTailV2Get() {
//        return "order_tail_v2";
//    }
//
//    @RequestMapping(value = "order_tail_v2", method = RequestMethod.POST)
//    @ResponseBody
//    public String orderTailV2Post(@RequestParam(value = "count") Integer count,
//                                  @RequestParam(value = "no") String no,
//                                  @RequestParam(value = "collection") String collection) {
//        String result = caipiaoService.orderTailV2(no, count, collection);
//        return result;
//    }
//
//    @RequestMapping(value = "qishu_intertval", method = RequestMethod.GET)
//    public String qishuIntervalGet() {
//        return "qishu_intertval";
//    }
//
//    @RequestMapping(value = "qishu_intertval", method = RequestMethod.POST)
//    @ResponseBody
//    public String qishuIntervalPost(@RequestParam(value = "interval") int interval,
//                                @RequestParam(value = "qishu") String qishu) {
//        String result = caipiaoService.qishuInterval(interval, qishu);
//        return result;
//    }
//
//    @RequestMapping(value = "qishu_interval_iteration", method = RequestMethod.GET)
//    public String qishuIntervalIterationGet() {
//        return "qishu_interval_iteration";
//    }
//
//    @RequestMapping(value = "qishu_interval_iteration", method = RequestMethod.POST)
//    @ResponseBody
//    public String qishuIntervalIterationPost(@RequestParam(value = "interval") int interval,
//                                             @RequestParam(value = "qishu") String qishu) {
//        JSONArray indexArray = caipiaoService.qishuIntervalIteration(interval, qishu, "cqssc");
//        String result = caipiaoService.qishuIntervalIterationRender(qishu, interval + "", indexArray);
//        return result;
//    }
//
//
//    @RequestMapping(value = "qishu_interval_iteration_xywfc", method = RequestMethod.GET)
//    public String qishuIntervalIterationXywfcGet() {
//        return "qishu_interval_iteration_xywfc";
//    }
//
//    @RequestMapping(value = "qishu_interval_iteration_xywfc", method = RequestMethod.POST)
//    @ResponseBody
//    public String qishuIntervalIterationXywfcPost(@RequestParam(value = "interval") int interval,
//                                             @RequestParam(value = "qishu") String qishu) {
//        JSONArray indexArray = caipiaoService.qishuIntervalIteration(interval, qishu, "xywfc");
//        String result = caipiaoService.qishuIntervalIterationRender(qishu, interval + "", indexArray);
//        return result;
//    }
//
//    @RequestMapping(value = "qishu_interval_iteration_xjssc", method = RequestMethod.GET)
//    public String qishuIntervalIterationXjsscGet() {
//        return "qishu_interval_iteration_xjssc";
//    }
//
//    @RequestMapping(value = "qishu_interval_iteration_xjssc", method = RequestMethod.POST)
//    @ResponseBody
//    public String qishuIntervalIterationXjsscPost(@RequestParam(value = "interval") int interval,
//                                                  @RequestParam(value = "qishu") String qishu) {
//        JSONArray indexArray = caipiaoService.qishuIntervalIteration(interval, qishu, "xjssc");
//        String result = caipiaoService.qishuIntervalIterationRender(qishu, interval + "", indexArray);
//        return result;
//    }
//
//    @RequestMapping(value = "not_same", method = RequestMethod.GET)
//    public String notSameGet() {
//        return "not_same";
//    }
//
//    @RequestMapping(value = "not_same", method = RequestMethod.POST)
//    @ResponseBody
//    public String notSamePost(@RequestParam(value = "no") String no,
//                                @RequestParam(value = "m") Integer m,
//                                @RequestParam(value = "collection") String collection) {
//        String result = caipiaoService.notSame(no, m, collection);
//        return result;
//    }
//
//    @RequestMapping(value = "api/not_same", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiNotSamePost(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        Integer m = params.getInteger("m");
//        String collection = params.getString("collection");
//        String result = caipiaoService.notSame(no, m, collection);
//        return result;
//    }
//
//    @RequestMapping(value = "not_same_plus", method = RequestMethod.GET)
//    public String notSamePlusGet() {
//        return "not_same_plus";
//    }
//
//    @RequestMapping(value = "not_same_plus", method = RequestMethod.POST)
//    @ResponseBody
//    public String notSamePlusPost(@RequestParam(value = "no") String no,
//                              @RequestParam(value = "m") Integer m,
//                              @RequestParam(value = "index") Integer index,
//                              @RequestParam(value = "collection") String collection) {
//        JSONObject result = caipiaoService.notSamePlus(no, m, index, collection);
//        int d = result.getIntValue("d");
//        List<JSONObject> cqssc = (List<JSONObject>) result.get("list");
//        String lastRepeatHtml = result.getString("lastRepeatHtml");
//        StringBuilder sb = new StringBuilder();
//        sb.append("<p>位置").append(index).append("  d:").append(d).append(" m:").append(m).append("</p>");
//        for (int i=(d-1); i<cqssc.size()-d; i+=d) {
//            sb.append("<p>").append("no:").append(cqssc.get(i).getString("no")).append("  res:").append(cqssc.get(i).getString("result")).append("</p>");
//        }
//        sb.append("<p>m+1位：</p>");
//        sb.append(lastRepeatHtml);
//        return sb.toString();
//    }
//
//    @RequestMapping(value = "api/not_same_plus", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiNotSamePlusPost(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        Integer m = params.getInteger("m");
//        Integer index = params.getInteger("index");
//        String collection = params.getString("collection");
//        JSONObject result = caipiaoService.notSamePlus(no, m, index, collection);
//        int d = result.getIntValue("d");
//        List<JSONObject> cqssc = (List<JSONObject>) result.get("list");
//        String lastRepeatHtml = result.getString("lastRepeatHtml");
//        StringBuilder sb = new StringBuilder();
//        sb.append("<p>位置").append(index).append("  d:").append(d).append(" m:").append(m).append("</p>");
//        for (int i=(d-1); i<cqssc.size()-d; i+=d) {
//            sb.append("<p>").append("no:").append(cqssc.get(i).getString("no")).append("  res:").append(cqssc.get(i).getString("result")).append("</p>");
//        }
//        sb.append("<p>m+1位：</p>");
//        sb.append(lastRepeatHtml);
//        return sb.toString();
//    }
//
//    @RequestMapping(value = "not_same_v3", method = RequestMethod.GET)
//    public String notSameV3Get() {
//        return "not_same_v3";
//    }
//
//    @RequestMapping(value = "not_same_v3", method = RequestMethod.POST)
//    @ResponseBody
//    public String notSameV3Post(@RequestParam(value = "no") String no,
//                              @RequestParam(value = "count") Integer count) {
//        String sb = caipiaoService.notSameV3(no, count, "cqssc");
//        return sb;
//    }
//
//    @RequestMapping(value = "not_same_v3_xywfc", method = RequestMethod.GET)
//    public String notSameV3Getxywfc() {
//        return "not_same_v3_xywfc";
//    }
//
//    @RequestMapping(value = "not_same_v3_xywfc", method = RequestMethod.POST)
//    @ResponseBody
//    public String notSameV3Postxywfc(@RequestParam(value = "no") String no,
//                                @RequestParam(value = "count") Integer count) {
//        String sb = caipiaoService.notSameV3(no, count, "xywfc");
//        return sb;
//    }
//
//
//
//
//    @RequestMapping(value = "api/not_same_v3", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiNotSameV3Post(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        int count = params.getIntValue("count");
//        String sb = caipiaoService.notSameV3(no, count, "cqssc");
//        return sb;
//    }
//
//    @RequestMapping(value = "algorithm01", method = RequestMethod.GET)
//    public String algorithm01Get() {
//        return "algorithm01";
//    }
//
//    @RequestMapping(value = "algorithm01", method = RequestMethod.POST)
//    @ResponseBody
//    public String algorithm01Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
//        return caipiaoService.algorithm01(no, count, "cqssc");
//    }
//
//    @RequestMapping(value = "algorithm01_xywfc", method = RequestMethod.GET)
//    public String algorithm01XywfcGet() {
//        return "algorithm01_xywfc";
//    }
//
//    @RequestMapping(value = "algorithm01_xywfc", method = RequestMethod.POST)
//    @ResponseBody
//    public String algorithm01XywfcPost(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
//        return caipiaoService.algorithm01(no, count, "xywfc");
//    }
//
//    @RequestMapping(value = "api/algorithm01_xywfc", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiAlgorithm01XywfcPost(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        int count = params.getIntValue("count");
//        return caipiaoService.algorithm01(no, count, "xywfc");
//    }
//
//    @RequestMapping(value = "algorithm01_pl5", method = RequestMethod.GET)
//    public String algorithm01Pl5Get() {
//        return "algorithm01_pl5";
//    }
//
//    @RequestMapping(value = "algorithm01_pl5", method = RequestMethod.POST)
//    @ResponseBody
//    public String algorithm01Pl5Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
//        return caipiaoService.algorithm01Pl5(no, count);
//    }
//
//    @RequestMapping(value = "algorithm01_pl52", method = RequestMethod.GET)
//    public String algorithm01Pl52Get() {
//        return "algorithm01_pl52";
//    }
//
//    @RequestMapping(value = "algorithm01_pl52", method = RequestMethod.POST)
//    @ResponseBody
//    public String algorithm01Pl52Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count) {
//        return caipiaoService.algorithm01(no, count, "pl5");
//    }
//
//    @RequestMapping(value = "api/algorithm01_pl5", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiAlgorithm01Pl52Post(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        int count = params.getIntValue("count");
//        return caipiaoService.algorithm01(no, count, "pl5");
//    }
//
//    @RequestMapping(value = "abxxn", method = RequestMethod.GET)
//    public String abxxnGet() {
//        return "abxxn";
//    }
//
//    @RequestMapping(value = "abxxn", method = RequestMethod.POST)
//    @ResponseBody
//    public String abxxnPost(@RequestParam(value = "code") String code, @RequestParam(value = "count") int count) {
//        String abxxn = caipiaoService.abxxn(code, count);
//        return abxxn;
//    }
//
//    @RequestMapping(value = "api/abxxn", method = RequestMethod.POST, produces = {"application/json"})
//    @ResponseBody
//    public JSONArray apiabxxnPost(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String code = params.getString("code");
//        int count = params.getIntValue("count");
//        JSONArray array = caipiaoService.abxxnApi(code, count);
//        return array;
//    }
//
//    @RequestMapping(value = "api/abxxn/filt", method = RequestMethod.POST, produces = {"application/json"})
//    @ResponseBody
//    public JSONArray apiabxxnFiltPost(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        return caipiaoService.abxxnApiFilt(params.getJSONArray("filterArray"));
//    }
//
//    @RequestMapping(value = "abxxn2", method = RequestMethod.GET)
//    public String abxxn2Get() {
//        return "abxxn2";
//    }
//
//    @RequestMapping(value = "abxxn2", method = RequestMethod.POST)
//    @ResponseBody
//    public String abxxn2Post(@RequestParam(value = "code") String code) {
//        String abxxn2 = caipiaoService.abxxn2(code);
//        return abxxn2;
//    }
//
//    @RequestMapping(value = "api/abxxn2", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiAbxxn2Post(@RequestBody String body) {
//        logger.info("api/abxxn2, {}", body);
//        JSONObject params = JSON.parseObject(body);
//        String code = params.getString("code");
//        String abxxn2 = caipiaoService.abxxn2(code);
//        return abxxn2;
//    }
//
//    @RequestMapping(value = "abxxnv2", method = RequestMethod.GET)
//    public String abxxnV2Get() {
//        return "abxxnv2";
//    }
//
//    @RequestMapping(value = "abxxnv2", method = RequestMethod.POST)
//    @ResponseBody
//    public String abxxnV2Post(@RequestParam(value = "no") String no, @RequestParam(value = "code") String code, @RequestParam(value = "count") int count) {
//        String abxxn = caipiaoService.abxxnV2(no, code, count);
//        return abxxn;
//    }
//
//    @RequestMapping(value = "api/abxxnv2", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiAbxxnV2Post(@RequestBody String body) {
//        JSONObject params = JSON.parseObject(body);
//        String no = params.getString("no");
//        String code = params.getString("code");
//        int count = params.getIntValue("count");
//        String abxxn = caipiaoService.abxxnV2(no, code, count);
//        return abxxn;
//    }
//
//    @RequestMapping(value = "compose01", method = RequestMethod.GET)
//    public String compose01Get() {
//        return "compose01";
//    }
//
//    @RequestMapping(value = "compose01", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiCompose01Post(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count, @RequestParam(required = false, defaultValue = "3", value = "circle") int circle) {
//        String result = caipiaoService.compose01(no, count, circle);
//        return result;
//    }
//
//    @RequestMapping(value = "compose01xj", method = RequestMethod.GET)
//    public String compose01XJGet() {
//        return "compose01xj";
//    }
//
//    @RequestMapping(value = "compose01xj", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiCompose01XJPost(@RequestParam(value = "no") String no, @RequestParam(value = "count") int count, @RequestParam(required = false, defaultValue = "3", value = "circle") int circle) {
//        String result = caipiaoService.compose01Xj(no, count, circle);
//        return result;
//    }
//
//    @RequestMapping(value = "compose02", method = RequestMethod.GET)
//    public String compose02Get() {
//        return "compose02";
//    }
//
//    @RequestMapping(value = "compose02", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiCompose02Post(@RequestParam(value = "number") String number) {
//        String result = caipiaoService.compose02(number);
//        return result;
//    }
//
//    @RequestMapping(value = "compose03", method = RequestMethod.GET)
//    public String compose03Get() {
//        return "compose03";
//    }
//
//    @RequestMapping(value = "compose03", method = RequestMethod.POST)
//    @ResponseBody
//    public String apiCompose03Post(@RequestParam(value = "number") String number) {
//        String result = caipiaoService.compose03(number);
//        return result;
//    }
//}
