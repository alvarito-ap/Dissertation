package com.Alvaro.TFG.Service;

import com.Alvaro.TFG.Model.*;
import com.Alvaro.TFG.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private ProyectRepository proyectRepository;
    private TeenagerRepository teenagerRepository;
    private TeenagerRelationRepository teenagerRelationRepository;
    private EgocentricFreeTimeRepository egocentricFreeTimeRepository;
    private EgocentricConsumeRepository egocentricConsumeRepository;
    private EgocentricExtraConsumeRepository egocentricExtraConsumeRepository;
    private FamilyAffluencyRepository familyAffluencyRepository;

    @Autowired
    public ProjectService(ProyectRepository proyectRepository, TeenagerRepository teenagerRepository, TeenagerRelationRepository teenagerRelationRepository, EgocentricFreeTimeRepository egocentricFreeTimeRepository
                            , EgocentricConsumeRepository egocentricConsumeRepository, EgocentricExtraConsumeRepository egocentricExtraConsumeRepository, FamilyAffluencyRepository familyAffluencyRepository){
        this.proyectRepository = proyectRepository;
        this.teenagerRepository = teenagerRepository;
        this.teenagerRelationRepository = teenagerRelationRepository;
        this.egocentricFreeTimeRepository = egocentricFreeTimeRepository;
        this.egocentricConsumeRepository = egocentricConsumeRepository;
        this.egocentricExtraConsumeRepository = egocentricExtraConsumeRepository;
        this.familyAffluencyRepository = familyAffluencyRepository;
    }

    public List<Map> getUserProjects(User user){
        List<Proyect> proyects = proyectRepository.findProyectsByUsersUsername(user.getUsername());
        ArrayList<Map> info = new ArrayList<>();
        for(Proyect p:proyects){
            Map<String,String> temp = new HashMap();
            temp.put("name", p.getName());
            temp.put("description", p.getDescription());
            temp.put("id", String.valueOf(p.getIdProyect()));
            info.add(temp);
        }
        return info;
    }

    public Map<String, List<Map<String, Object>>> getProjectSigma(int id, int idTeenager, String filter){
        Map<String, List<Map<String, Object>>> data = new HashMap<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        List<Proyect> ps = proyectRepository.findProyectByIdProyect(id);
        Proyect proyect = ps.get(0);
        List<Teenager> projectTeenagers = teenagerRepository.findTeenagersByProyect(proyect);

        for(Teenager t : projectTeenagers){
            Map<String, Object> node = new HashMap<>();

            node.put("id", ""+t.getIdTeenager());
            node.put("label", t.getAlias());
            node.put("x", t.getCoorX());
            node.put("y", t.getCoordY());
            node.put("size", (teenagerRelationRepository.findTeenagerRelationByPrimaryTeenager(t).size()+1));
            node.put("type", getNodeShape(t));
            t.color = getColor();
            node.put("color", t.color);
            nodes.add(node);
            if(idTeenager==-1){
                edges.addAll(teenagerRelations(t, "all"));
            }
        }
        if (idTeenager!=-1){
            List<Teenager> pryms = teenagerRepository.findTeenagerByIdTeenager(idTeenager);
            edges.addAll(teenagerRelations(pryms.get(0), filter));
        }
        data.put("nodes", nodes);
        data.put("edges", edges);
        return data;
    }

    private List<Map<String, Object>> teenagerRelations(Teenager t, String filter){
        String[] tipos = { "arrow"};
        List<TeenagerRelation> teenagerRelation;
        if(filter.equals("all")){
           teenagerRelation = teenagerRelationRepository.findTeenagerRelationByPrimaryTeenager(t);
        }else{
            teenagerRelation = teenagerRelationRepository.findTeenagerRelationsByPrimaryTeenagerAndTime(t, filter);
        }

        List<Map<String, Object>> edges = new ArrayList<>();
        for(TeenagerRelation tr : teenagerRelation){
            Map<String, Object> edge = new HashMap<>();
            edge.put("id", "e"+tr.getIdRelation());
            edge.put("source", ""+t.getIdTeenager());
            edge.put("target", ""+tr.getRelatedTeenager().getIdTeenager());
            edge.put("label", t.getName()+" a "+tr.getRelatedTeenager().getName());
            edge.put("type", tipos[0]);
            edge.put("size", 5);
            edge.put("color", t.color);
            edges.add(edge);
        }
        return edges;
    }

    public Map<String, Object> getNodeInfo(int idNode){
        List<Teenager> ts = teenagerRepository.findTeenagerByIdTeenager(idNode);
        Teenager t = ts.get(0);
        Map<String, Object> info = new HashMap<>();
        info.put("name", t.getAlias());
        info.put("surName", t.getSurName());
        info.put("placeBirth", t.getPlaceBirth());
        info.put("gender", t.getSex());
        info.put("institute", t.getInstitute());
        info.put("groupe", t.getGroupe());
        return info;
    }

    public Map<String, List<Map<String, Object>>> getEgocentricRelations(int idProject, int idNode, String type){
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        if(belongToProject(idProject, idNode)){
            Teenager teenager = teenagerRepository.findTeenagerByIdTeenager(idNode).get(0);
            List<Map<String, Object>> nodes = new ArrayList<>();
            List<Map<String, Object>> edges = new ArrayList<>();
            Map<String, Object> studentNode = new HashMap<>();
            studentNode.put("id", teenager.getIdTeenager());
            studentNode.put("label", teenager.getAlias());
            studentNode.put("x", teenager.getCoorX());
            studentNode.put("y", teenager.getCoordY());
            studentNode.put("size", 10);
            nodes.add(studentNode);
            switch (type){
                case "Comparto tiempo fuera de clase":
                    List<EgocentricFreeTime> list = egocentricFreeTimeRepository.findAllByTeenager(teenager);
                    for(EgocentricFreeTime e : list){
                        Map<String, Object> node =  createNode(teenager, e);
                        nodes.add(node);

                        Map<String, Object> edge  = createEdge(teenager, e);
                        edge.put("label", e.getPlace() + " " + e.getFrecuency());
                        edges.add(edge);
                    }
                    break;
                case "Consumo fuera de clase":
                    List<EgocentricConsume> listEC = egocentricConsumeRepository.findAllByTeenager(teenager);
                    for(EgocentricConsume e : listEC){
                        Map<String, Object> node = createNode(teenager, e);
                        nodes.add(node);

                        Map<String, Object> edge = createEdge(teenager, e);
                        edge.put("label", e.getPlace() + " " + e.getFrecuency()+ " " + e.getType());
                        edges.add(edge);
                    }
                    break;
                case "Algunas personas mas con las que consumo":
                    List<EgocentricExtraConsume> listECE = egocentricExtraConsumeRepository.findAllByTeenager(teenager);
                    for(EgocentricExtraConsume e : listECE){
                        Map<String, Object> node = createNode(teenager, e);
                        nodes.add(node);

                        Map<String, Object> edge = createEdge(teenager, e);
                        edge.put("label", e.getPlace() + " " + e.getType() + " "+e.getPlaceFirstMeet());
                        edges.add(edge);
                    }
                    break;
            }
            result.put("nodes", nodes);
            result.put("edges",edges);
            return result;

        }
        return  result;
    }

    private Map<String, Object> createNode(Teenager teenager, EgocentricRelation e){
        Map<String, Object> node = new HashMap<>();
        node.put("id", e.getId());
        node.put("label", e.getName());
        node.put("x", e.getCoordX());
        node.put("y", e.getCoordY());
        node.put("size", 10);
        return node;
    }

    private Map<String, Object> createEdge(Teenager teenager, EgocentricRelation e){
        Map<String, Object> edge = new HashMap<>();
        edge.put("id", e.getId());
        edge.put("source", teenager.getIdTeenager());
        edge.put("target", e.getId());
        return edge;
    }

    public boolean updateNodePosition(int idProject, int idNode, int x, int y){
        if(belongToProject(idProject, idNode)){
            Teenager t = teenagerRepository.findTeenagerByIdTeenager(idNode).get(0);
            t.setCoorX(x);
            t.setCoordY(y);
            teenagerRepository.save(t);
            return true;
        }
        return false;
    }

    private boolean belongToProject(int idProject, int idNode){
        List<Teenager> l = teenagerRepository.findTeenagerByIdTeenager(idNode);
        if(l.get(0) != null && l.get(0).getProyect().getIdProyect() == idProject){
            return true;
        }
        return false;
    }

    private String getNodeShape(Teenager teenager){
        List<FamilyAffluency> l = familyAffluencyRepository.findAllByTeenager(teenager);
        String res [] = {"star", "circle", "square", "diamond", "equilateral"};
        int grade = 0;
        if (l.size()>0){
            grade = l.size()-1;
        }
        return res[grade];
    }

    private String getColor(){
        String res [] = {"#7DCEA0", "#E74C3C", "#3498DB", "#58D68D", "#F39C12", "#85929E"};
        return res[(int)(Math.random()*6)];
    }
}
