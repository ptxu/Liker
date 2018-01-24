/**
 * Copyright: Copyright (c) 2018 
 * Company:深圳市深网视界科技有限公司
 * 
 * @author dell
 * @date 2018年1月23日 下午4:55:51
 * @version V1.0
 */
package com.liker.services.thrift.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.liker.core.FullTextIndexSearcher;
import com.liker.core.FullTextIndexWriter;
import com.liker.model.Document;
import com.liker.model.Field;
import com.liker.model.FullTextIndexRequestParam;
import com.liker.model.FullTextIndexTargetResult;
import com.liker.model.IndexTask;
import com.liker.model.Keyword;
import com.liker.model.TaskType;

import liker.AddTaskResponseResult;
import liker.ResponseCode;
import liker.SearchResponseResult;

/**
 * @ClassName: FullTextIndexServiceImpl
 * @Description: FullTextIndexServiceImpl
 * @author xupengtao
 * @date 2018年1月23日 下午4:55:51
 *
 */
public class FullTextIndexServiceImpl implements liker.FullTextIndexService.Iface {

    /*
     * <p>Title: addIndexTask</p> <p>Description: </p>
     * 
     * @param tasks
     * 
     * @return
     * 
     * @throws TException
     * 
     * @see liker.FullTextIndexService.Iface#addIndexTask(java.util.List)
     */
    @Override
    public AddTaskResponseResult addIndexTask(List<liker.IndexTask> tasks) throws TException {
        for (liker.IndexTask task : tasks) {
            IndexTask indexTask = new IndexTask();
            indexTask.setType(TaskType.findByValue(task.getType().getValue()));

            if (task.getTargetField() != null) {
                Field targetField = new Field();
                targetField.setFieldName(task.getTargetField().getFieldName());
                targetField.setFieldValue(task.getTargetField().getFieldValue());
                targetField.setIndexed(task.getTargetField().isIndexed());
                indexTask.setTargetField(targetField);
            }

            if (task.getDoc() != null && task.getDoc().getFields() != null) {
                Document doc = new Document();
                for (liker.Field field : task.getDoc().getFields()) {
                    Field _feild = new Field();
                    _feild.setFieldName(field.getFieldName());
                    _feild.setFieldValue(field.getFieldValue());
                    _feild.setIndexed(field.isIndexed());
                    doc.addField(_feild);
                }
                indexTask.setDoc(doc);
            }
            FullTextIndexWriter.addTask(indexTask);
        }
        AddTaskResponseResult response = new AddTaskResponseResult();
        response.setCode(ResponseCode.Success.getValue());
        response.setMessage(com.liker.services.http.common.ResponseCode.getTypeByValue(ResponseCode.Success.getValue()).getDesc());
        return response;
    }

    /*
     * <p>Title: fullTextSearch</p> <p>Description: </p>
     * 
     * @param param
     * 
     * @return
     * 
     * @throws TException
     * 
     * @see liker.FullTextIndexService.Iface#fullTextSearch(liker.IndexRequestParam)
     */
    @Override
    public SearchResponseResult fullTextSearch(liker.IndexRequestParam param) throws TException {
        FullTextIndexRequestParam searchParam = new FullTextIndexRequestParam();
        searchParam.setCurrentPage(param.getCurrentPage());
        List<Keyword> keywords = new ArrayList<>();
        for (liker.Keyword keyword : param.getKeywords()) {
            Keyword _keyword = new Keyword();
            _keyword.setFieldName(keyword.getFieldName());
            _keyword.setFieldValue(keyword.getFieldValue());
            _keyword.setTokenized(keyword.isTokenized());
            keywords.add(_keyword);
        }
        searchParam.setKeywords(keywords.toArray(new Keyword[] {}));
        searchParam.setPageSize(searchParam.getPageSize());
        searchParam.setResponseFieldNames(param.getResponseFieldNames().toArray(new String[] {}));

        try {
            List<FullTextIndexTargetResult> resultList = FullTextIndexSearcher.search(searchParam);
            List<liker.IndexTargetResult> targetList = new ArrayList<>();
            for (FullTextIndexTargetResult result : resultList) {
                liker.IndexTargetResult _result = new liker.IndexTargetResult();
                _result.setScore(result.getScore());
                List<liker.Field> fields = new ArrayList<>();
                for (Field field : result.getDoc().getFields()) {
                    fields.add(new liker.Field(field.getFieldName(), field.getFieldValue(), field.isIndexed()));
                }
                _result.setDoc(new liker.Document(fields));
                targetList.add(_result);
            }
            return new SearchResponseResult(ResponseCode.Success.getValue(), targetList, com.liker.services.http.common.ResponseCode.getTypeByValue(ResponseCode.Success.getValue()).getDesc());
        }
        catch (Exception e) {
            SearchResponseResult response = new SearchResponseResult();
            response.setCode(ResponseCode.UnknowErr.getValue());
            response.setMessage(com.liker.services.http.common.ResponseCode.getTypeByValue(ResponseCode.UnknowErr.getValue()).getDesc());
            return response;
        }
    }
}
