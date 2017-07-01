package org.sunhp.rcampus.vo;

/**
 * Created by yufeng.fyf on 17/4/29.
 */
public class ReturnMsg {
    private Boolean status;
    private String data;

    public ReturnMsg(){}
    public ReturnMsg(Boolean status,String data){
        this.status = status;
        this.data = data;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
