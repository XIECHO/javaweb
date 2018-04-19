package com.example.demo.service;

import com.example.demo.dao.NewsDao;
import com.example.demo.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }
}
