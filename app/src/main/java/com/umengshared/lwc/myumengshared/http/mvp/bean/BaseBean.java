package com.umengshared.lwc.myumengshared.http.mvp.bean;

/**
 * Created by lingwancai on
 * 2018/7/31 13:50
 */
public class BaseBean {
    /**
     * "success": true,
     * "status": 1
     * "message":
     */

    private boolean success;
    private int status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
