package com.example.demo.service;

import com.example.demo.dao.NewsDao;
import com.example.demo.model.News;
import com.example.demo.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

    //增加新闻
    public int addNews(News news){
        newsDao.addNews(news);
        return news.getId();
    }


    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toString();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." +fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" +fileName;
    }

    public News getById(int newsId){return newsDao.getById(newsId);}

    public int updateCommentCount(int entityId, int count){
        return newsDao.updateCommentCount(entityId, count);
    }

    public int updateLikeCount(int id, int count){ return newsDao.updateLikeCount(id,count);}
}
