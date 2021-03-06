package com.ligen.controller;

import com.ligen.service.HszsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HszsController {

    @Autowired
    HszsService hszsService;

    @RequestMapping(value = "hszs", method = RequestMethod.GET)
    public String hszsGet() {
        return "hszs";
    }

    @RequestMapping(value = "hszs", method = RequestMethod.POST)
    @ResponseBody
    public String hszsPost(@RequestParam(value = "time") String time, @RequestParam(value = "count", defaultValue = "1") int count, @RequestParam(value = "interval", defaultValue = "1") int interval) {
        return hszsService.getData(time, count, interval);
    }
}
