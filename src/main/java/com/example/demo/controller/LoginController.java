package com.example.demo.controller;

import com.example.demo.model.News;
import com.example.demo.model.ViewObject;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.example.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/reg"})
   // @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody

    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                      HttpServletResponse response){
        try {
            Map<String,Object> map = userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme > 0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0,"注册成功");
            }else{
                return ToutiaoUtil.getJSONString(0,map);
            }
        }catch (Exception e){
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }
    }


    @RequestMapping(value = {"/login"})
    // @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody

    public String login(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        HttpServletResponse response){
        try {
            Map<String,Object> map = userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme > 0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0,"注册成功");
            }else{
                return ToutiaoUtil.getJSONString(0,map);
            }
        }catch (Exception e){
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }
    }

    @RequestMapping(path = "/logout")

    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
