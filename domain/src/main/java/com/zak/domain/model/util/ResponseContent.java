package com.zak.domain.model.util;

import java.util.List;

public class ResponseContent <T>{
        private T body;
        private List<T> bodyList;
        private String message;
        private String status;
        private List<String> messageList;

    public ResponseContent(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public ResponseContent(T body, List<String> messageList) {
        this.body = body;
        this.messageList = messageList;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<T> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<T> bodyList) {
        this.bodyList = bodyList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }
}
