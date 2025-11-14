package com.example.thodasukoon;

import android.os.Message;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChatResponse {

    @SerializedName("reply")
    private String reply;

    @SerializedName("urgentReferral")
    private boolean urgentReferral;

    @SerializedName("chatHistory")
    private List<Message> chatHistory;

    // Getters and Setters
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isUrgentReferral() {
        return urgentReferral;
    }

    public void setUrgentReferral(boolean urgentReferral) {
        this.urgentReferral = urgentReferral;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<Message> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
