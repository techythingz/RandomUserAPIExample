package me.afua.randomuserexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
public class TestController {

    @Autowired
    RandomUserRepository randomUsers;

    @RequestMapping("/")
    public String retreiveUsersfromAPI(Model model)
    {
        RestTemplate fromAPI = new RestTemplate();
//        String theUserDetails = fromAPI.getForObject("https://randomuser.me/api",String.class);


        RandomUsers users =   fromAPI.getForObject("https://randomuser.me/api?results=10",RandomUsers.class);

        for (RandomUser eachuser: users.results) {
            System.out.println("Image:"+eachuser.getImage().get("large"));
            System.out.println("Username:"+eachuser.getUsername());
            System.out.println("Password:"+eachuser.getUnEncryptedPassword());
            System.out.println("Email:"+eachuser.getEmail());

              //Save information to the database
              randomUsers.save(eachuser);
        }

        model.addAttribute("userList",randomUsers.findAll());
        System.out.println("You now have "+randomUsers.count()+" users in the database");
        return "profile";
    }
}
