package com.liker.services.http.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liker.core.FullTextIndexSearcher;
import com.liker.core.FullTextIndexWriter;
import com.liker.model.FullTextIndexRequestParam;
import com.liker.model.FullTextIndexTargetResult;
import com.liker.model.IndexTask;
import com.liker.services.http.common.ResponseCode;
import com.liker.services.http.common.ResponseResult;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/FullTextIndex")
public class FullTextController {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(FullTextController.class);

    @ApiOperation(value = "新增任务")
    @RequestMapping(value = "/addIndexTask", method = RequestMethod.POST)
    public @ResponseBody Object addIndexTask(@RequestBody IndexTask[] tasks, HttpServletRequest request) {
        FullTextIndexWriter.addTask(tasks);
        ResponseResult<?> response = new ResponseResult<>(ResponseCode.Success);
        return response;
    }

    @ApiOperation(value = "全文检索")
    @RequestMapping(value = "/fullTextSearch", method = RequestMethod.POST)
    public @ResponseBody Object fullTextSearch(@RequestBody FullTextIndexRequestParam param, HttpServletRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        ResponseResult<List<FullTextIndexTargetResult>> response = new ResponseResult<>(ResponseCode.Success);
        response.setData(FullTextIndexSearcher.search(param));
        LOGGER.info(String.format("Request %s use time %d ms.", param.toString(), System.currentTimeMillis() - startTime));
        return response;
    }
}
