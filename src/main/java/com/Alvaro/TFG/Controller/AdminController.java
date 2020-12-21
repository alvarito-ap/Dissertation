package com.Alvaro.TFG.Controller;

import com.Alvaro.TFG.Model.User;
import com.Alvaro.TFG.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.Alvaro.TFG.Service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Admin/")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @RequestMapping("registration")
    public ModelAndView registerNewUser(){
        ModelAndView view = new ModelAndView();
        User user = new User();
        User newUser = new User();
        view.addObject("user", user);
        view.addObject("newUser", newUser);
        view.setViewName("/Admin/registration");
        return view;
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User newUser, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(newUser.getName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("user", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(newUser);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("newUser", new User());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(auth.getName());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("/Admin/adminIndex");
        }
        return modelAndView;
    }

    @RequestMapping(value = "adminIndex")
    public ModelAndView admin(){
        ModelAndView view = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        User newUser = new User();
        view.addObject("user", user);
        view.addObject("newUser", newUser);
        view.setViewName("Admin/adminIndex");
        return view;
    }

    @RequestMapping(value = "usersInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Map<String,String>> showUsers(){
        List<Map<String, String>> datos = new ArrayList<>();
        for (User u : adminService.findUserUsers()){
            Map<String, String> dato = new HashMap<>();
            dato.put("name",u.getName());
            dato.put("username", u.getUsername());
            dato.put("status", String.valueOf(u.isStatus()));
            datos.add(dato);
        }
        return datos;
    }
}
