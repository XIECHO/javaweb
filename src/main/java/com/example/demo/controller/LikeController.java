package com.example.demo.controller;

import com.example.demo.model.EntityType;
import com.example.demo.model.HostHolder;
import com.example.demo.model.News;
import com.example.demo.service.LikeService;
import com.example.demo.service.NewsService;
import com.example.demo.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String like(@Param("newId") int newsId){
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS,newsId);

        //通过这里更新数据库的喜欢数
        News news = newsService.getById(newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String dislike(@Param("newsId") int newsId){
        long likeCount = likeService.dislike(hostHolder.getUser().getId(),EntityType.ENTITY_NEWS,newsId);
        newsService.updateLikeCount(newsId, (int)likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
