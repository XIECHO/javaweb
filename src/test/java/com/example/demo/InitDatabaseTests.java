package com.example.demo;

import com.example.demo.dao.CommentDao;
import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.NewsDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

	@Autowired
	NewsDao newsDao;
	@Autowired
	UserDao userDao;
	@Autowired
	LoginTicketDao loginTicketDao;
	@Autowired
	CommentDao commentDao;
	@Test
	public void initData() {
		Random random = new Random();
		for(int i=0;i<11;++i){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));

			user.setPassword(" ");
			user.setSalt(" ");
			userDao.addUser(user);


			user.setPassword("newpassword");
			userDao.updatePassword(user);


			News news = new News();
			news.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime() + 1000*3600*5*i);
			news.setCreatedDate(date);
			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
			news.setLikeCount(i+1);
			news.setTitle(String.format("TITLE{%d}",i));
			news.setUserId(i+1);
			news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
			newsDao.addNews(news);


			LoginTicket ticket = new LoginTicket();
			ticket.setStatus(0);
			ticket.setUserId(i+1);
			ticket.setExpired(date);
			ticket.setTicket(String.format("TICKET%d", i+1));
			loginTicketDao.addTicket(ticket);
			loginTicketDao.updateStatus(ticket.getTicket(), 2);



			for(int j = 0; j < 3; ++j){
				Comment comment = new Comment();
				comment.setUserId(i+1);
				comment.setCreatedDate(new Date());
				comment.setStatus(0);
				comment.setContent("这是一个评论!" + String.valueOf(j));
				comment.setEntityId(news.getId());
				comment.setEntityType(EntityType.ENTITY_NEWS);
				commentDao.addComment(comment);
			}

		}

		Assert.assertEquals(userDao.selcctedById(1).getPassword(),"newpassword");

		userDao.deleteById(1);

		Assert.assertNull(userDao.selcctedById(1));

		Assert.assertEquals(1,loginTicketDao.selcctByTicket("TICKET1").getUserId());
		Assert.assertEquals(2,loginTicketDao.selcctByTicket("TICKET1").getStatus());

		Assert.assertNotNull(commentDao.selectByEntity(1,EntityType.ENTITY_NEWS).get(0));
	}

}
