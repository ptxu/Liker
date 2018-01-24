namespace java liker

/*索引任务类型*/
enum IndexTaskType
{
    Add = 1, /*增加索引*/
    Update = 0, /*修改索引*/
    Delete = -1, /*删除索引*/
}

/*返回状态码*/
enum ResponseCode
{
    UnknowErr = 500,/*未知错误*/
    Success = 200,/*成功*/
}

/*文档字段*/
struct Field {
    1:string fieldName;
    2:string fieldValue;
    3:bool indexed;
}

/*检索关键字*/
struct Keyword {
    1:string fieldName;
    2:string fieldValue;
    3:bool tokenized;
}

/*文档*/
struct Document {
    1:list<Field> fields;
}

/*索引任务*/
struct IndexTask {
    1:IndexTaskType type;
    2:Document doc;
    3:Field targetField;
}

/*全文检索的参数*/
struct IndexRequestParam {
    1:i32 currentPage;
    2:i32 pageSize = 1000;
    3:list<Keyword> keywords;
    4:list<string> responseFieldNames;
}

/*全文检索命中的结果*/
struct IndexTargetResult {
    1:double score;
    2:Document doc;
}

/*全文检索返回的结果*/
struct SearchResponseResult {
    1:i32 code;
    2:list<IndexTargetResult> data;
    3:string message;
}

/*添加索引任务返回的结果*/
struct AddTaskResponseResult {
    1:i32 code;
    2:string message;
}

/*服务接口定义*/
service FullTextIndexService
{
    AddTaskResponseResult addIndexTask(1:list<IndexTask> tasks);
    SearchResponseResult fullTextSearch(1:IndexRequestParam param);

}

