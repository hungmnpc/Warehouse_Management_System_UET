package com.monopoco.common.model;



public class CommonResponse<T> {

    protected Result result;

    protected T data;

    protected Boolean success;

    protected HistoryEvent history;

    public CommonResponse history(HistoryEvent historyDTO) {
        this.history = historyDTO;
        return this;
    }

    public HistoryEvent getHistory() {
        return this.history;
    }

    public CommonResponse() {
    }

    public CommonResponse success(){
        this.result = new Result().errorCode("00").message("Success").isOk(true);
        this.success = true;
        return this;
    }

    public CommonResponse badRequest(String message){
        this.result = new Result().errorCode("400").message(message).isOk(false);
        this.success = false;
        return this;
    }

    public CommonResponse badRequest(){
        this.result = new Result().errorCode("400").message("Bad Request").isOk(false);
        this.success = false;
        return this;
    }


    public CommonResponse notFound(String message){
        this.result = new Result().errorCode("404").message(message).isOk(false);
        this.success = false;
        return this;
    }

    public CommonResponse notFound(){
        this.result = new Result().errorCode("404").message("Not Found").isOk(false);
        this.success = false;
        return this;
    }

    public CommonResponse success(String message){
        this.result = new Result().errorCode("00").message(message).isOk(true);
        this.success = true;
        return this;
    }



    public Boolean isSuccess() {
        return this.success;
    }

    public CommonResponse result(String errorCode,String errorMessage, boolean ok){
        this.result = new Result().errorCode(errorCode).message(errorMessage).isOk(ok);
        return this;
    }

    public CommonResponse errorCode(String errorCode){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.errorCode(errorCode);
        return this;
    }

    public CommonResponse message(String message){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.message(message);
        return this;
    }

    public CommonResponse isOk(boolean ok){
        if(this.result == null){
            this.result = new Result();
        }
        this.result.isOk(ok);
        this.success = ok;
        return this;
    }
    public CommonResponse data(T data){
        this.data = data;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
