package com.Alvaro.TFG.Controller;

import com.Alvaro.TFG.Model.Proyect;
import com.Alvaro.TFG.Model.User;
import com.Alvaro.TFG.Service.ProjectService;
import com.Alvaro.TFG.Service.UploadProjectService;
import com.Alvaro.TFG.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/User/")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UploadProjectService uploadProjectService;

    @Autowired
    ProjectService projectService;

    @RequestMapping("userIndex")
    public ModelAndView userIndex(){
        ModelAndView view = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        view.addObject("user", userService.findUserByUsername(auth.getName()));
        return view;
    }

    @PostMapping("userIndex")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("projectName") String projectName, @RequestParam("projectDescription") String projectDescription){
        System.out.println(file.getName());
        ModelAndView view = new ModelAndView();
        view.setViewName("User/userIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        view.addObject("user", userService.findUserByUsername(auth.getName()));
        boolean uploadSuccess = uploadProjectService.upload(auth.getName(), projectName, projectDescription, file);
        String message = "upload of file " + file.getName() + "status:"+ uploadSuccess;
        view.addObject("UploadMessage", message);
        return view;
    }

    @RequestMapping(value = "projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Map> getUserProjects(Principal principal){
        User user = userService.findUserByUsername(principal.getName());
        return projectService.getUserProjects(user);
    }

    @RequestMapping(value = "getProjectData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, List<Map<String, Object>>> getProjectData(@RequestParam("id") int id){
        return projectService.getProjectSigma(id, -1, "all");
    }

    @RequestMapping(value = "getProjectDataTeenager", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, List<Map<String, Object>>> getProjectDataTeenager(@RequestParam("idProject") int idProject, @RequestParam("idTeenager") int idTeenager){
        return projectService.getProjectSigma(idProject, idTeenager, "all");
    }

    @RequestMapping(value = "getNodeInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Object> getNodeInfo(@RequestParam("idNode")int idNode){
        return projectService.getNodeInfo(idNode);
    }

    @RequestMapping(value = "filterNodeClass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, List<Map<String, Object>>> filterNodeClass(@RequestParam("idProject") int idProject, @RequestParam("idNode") int idNode, @RequestParam("filter") String filter){
        return projectService.getProjectSigma(idProject, idNode, filter);
    }

    @RequestMapping(value = "egocentricRelation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, List<Map<String, Object>>> egocentricRelations(@RequestParam("idProject") int idProject, @RequestParam("idNode") int idNode, @RequestParam("type") String type){
        return projectService.getEgocentricRelations(idProject, idNode, type);
    }

    @RequestMapping(value = "updateNodePosition", method = RequestMethod.GET)
    public @ResponseBody String updateNodePosition(@RequestParam("idProject") int idProject, @RequestParam("idNode") int idNode, @RequestParam("x") Double x, @RequestParam("y") Double y){
        System.out.println(idProject+" "+idNode+" "+x+" "+y);
        return String.valueOf(projectService.updateNodePosition(idProject, idNode, x.intValue(), y.intValue()));
    }
}
