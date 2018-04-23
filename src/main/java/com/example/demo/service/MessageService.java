package com.example.demo.service;

import com.example.demo.dao.MessageDao;
import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    public int addMessage(Message message){ return messageDao.addMessage(message);}

    //双方通信内容
    public List<Message> getConversationDetail(String conversationId, int offset, int limit){
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    //单个人的通信列表
    public List<Message> getConversationList(int userId, int offset, int limit){
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getUnreadCount(int userId, String conversationId){
        return messageDao.getConversationUnReadCount(userId,conversationId);
    }
}
