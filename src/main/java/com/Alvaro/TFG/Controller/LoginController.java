package com.Alvaro.TFG.Controller;

import com.Alvaro.TFG.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.Alvaro.TFG.Model.User;

import javax.validation.Valid;
import java.util.Collection;

@Controller

public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/Public/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView view = new ModelAndView();
        view.setViewName("Public/login");
        return view;
    }
    @RequestMapping(value = {"/Public/registration"}, method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView view = new ModelAndView();
        User user = new User();
        view.addObject("user", user);
        view.setViewName("Public/registration");
        return view;
    }
    @RequestMapping(value = "/Public/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("user", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/Public/registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public ModelAndView adminIndex(){
        ModelAndView view = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        //System.out.println("nombre2 "+auth.getName());
        view.addObject("user", user);
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if(authorities.iterator().next().getAuthority().equals("ADMIN")){
            User newUser = new User();
            view.addObject("newUser", newUser);
            view.setViewName("Admin/adminIndex");
        }else if(authorities.iterator().next().getAuthority().equals("USER")){
            view.setViewName("User/userIndex");
        }else{
            view.setViewName("error");
        }
        return view;
    }

}
