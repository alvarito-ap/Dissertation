package com.Alvaro.TFG.Service;

import com.Alvaro.TFG.Model.*;
import com.Alvaro.TFG.Repository.*;
import com.Alvaro.TFG.Util.ColumType;
import com.Alvaro.TFG.Util.ContactFrecJSON;
import com.Alvaro.TFG.Util.Excel;
import com.Alvaro.TFG.Util.PlacesFrecJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadProjectService {

    private ProyectRepository proyectRepository;
    private UserRepository userRepository;
    private TeenagerRepository teenagerRepository;
    private TeenagerRelationRepository teenagerRelationRepository;
    private ContactFrecRepository contactFrecRepository;
    private PlacesFrecrepository placesFrecrepository;
    private ConsumeRepository consumeRepository;
    private EgocentricFreeTimeRepository egocentricFreeTimeRepository;
    private EgocentricConsumeRepository egocentricConsumeRepository;
    private EgocentricExtraConsumeRepository egocentricExtraConsumeRepository;
    private FamilyAffluencyRepository familyAffluencyRepository;

    @Autowired
    public UploadProjectService(ProyectRepository proyectRepository, UserRepository userRepository,TeenagerRepository teenagerRepository, TeenagerRelationRepository teenagerRelationRepository, ContactFrecRepository contactFrecRepository,
                                PlacesFrecrepository placesFrecrepository, ConsumeRepository consumeRepository, EgocentricFreeTimeRepository egocentricFreeTimeRepository, EgocentricConsumeRepository egocentricConsumeRepository, EgocentricExtraConsumeRepository egocentricExtraConsumeRepository,
                                FamilyAffluencyRepository familyAffluencyRepository){
        this.proyectRepository = proyectRepository;
        this.userRepository = userRepository;
        this.teenagerRepository = teenagerRepository;
        this.teenagerRelationRepository = teenagerRelationRepository;
        this.contactFrecRepository = contactFrecRepository;
        this.placesFrecrepository = placesFrecrepository;
        this.consumeRepository = consumeRepository;
        this.egocentricFreeTimeRepository = egocentricFreeTimeRepository;
        this.egocentricConsumeRepository = egocentricConsumeRepository;
        this.egocentricExtraConsumeRepository = egocentricExtraConsumeRepository;
        this.familyAffluencyRepository = familyAffluencyRepository;
    }

    public boolean upload(String username, String projectName, String projectDescription, MultipartFile file){
        boolean success = false;
        try {

            Excel excelProcessser = new Excel(file);
            User user = userRepository.findUserByUsername(username);
            Proyect proyect = new Proyect();
            proyect.setName(projectName);
            proyect.setDescription(projectDescription);
            proyectRepository.save(proyect);
            user.addProyect(proyect);
            userRepository.save(user);
            ArrayList<Teenager> projectTeenagers = saveTeenagers(excelProcessser, proyect);
            projectTeenagers = saveConsumes(excelProcessser, projectTeenagers);
            projectTeenagers = saveFamilyAffluency(excelProcessser, projectTeenagers);
            projectTeenagers = saveContactAndPlacesFrec(excelProcessser, projectTeenagers);
            projectTeenagers = saveTeenagerRelation(excelProcessser, projectTeenagers);
            saveEgocentricRelations(excelProcessser, projectTeenagers);
            success = true;
        }catch (Exception e){
            System.err.println(e.getCause() + e.getMessage() + e.getStackTrace()[0].getLineNumber());
            e.printStackTrace(new java.io.PrintStream(System.out));
        }
        return success;
    }

    private ArrayList<Teenager> saveTeenagers(Excel excelProcesser, Proyect project) throws Exception{
        ArrayList<String[]> names = excelProcesser.getTeenagersCol(ColumType.NAME);
        ArrayList<String[]> ages = excelProcesser.getTeenagersCol(ColumType.AGE);
        ArrayList<String[]> fechasNacimiento = excelProcesser.getTeenagersCol(ColumType.FECHANACIMIENTO);
        ArrayList<String[]> lugaresNacimiento = excelProcesser.getTeenagersCol(ColumType.LUGARNACIMIENTO);
        ArrayList<String[]> sexos = excelProcesser.getTeenagersCol(ColumType.SEXO);
        ArrayList<String[]> cursos = excelProcesser.getTeenagersCol(ColumType.CURSO);
        ArrayList<String[]> grupos = excelProcesser.getTeenagersCol(ColumType.GRUPO);
        ArrayList<String[]> institutos = excelProcesser.getTeenagersCol(ColumType.INSTITUTO);



        ArrayList<Teenager> teenagers = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){
            Teenager teenager = new Teenager();
            teenager.setName(names.get(i)[1]);
            teenager.setSurName(names.get(i)[0]);
            teenager.setAge((int)(Double.parseDouble(ages.get(i)[0])));
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(fechasNacimiento.get(i)[0]);
            teenager.setDateBirth(date);
            teenager.setPlaceBirth(lugaresNacimiento.get(i)[0]);
            teenager.setSex(sexos.get(i)[0]);
            teenager.setCourse(cursos.get(i)[0]);
            teenager.setGroupe(grupos.get(i)[0]);
            teenager.setInstitute(institutos.get(i)[0]);
            teenager.setProyect(project);
            teenager.setCoorX((int)(Math.random()*100));
            teenager.setCoordY((int)(Math.random()*100));
            teenager.setAlias(teenager.getName().substring(0,2)+teenager.getSurName().substring(0,2));
            teenagerRepository.save(teenager);
            teenagers.add(teenager);
        }
        return teenagers;
    }

    private ArrayList<Teenager> saveConsumes(Excel excelProcesser, ArrayList<Teenager> teenagers){

        ArrayList<String[]> tobacco = excelProcesser.getTeenagersCol(ColumType.TOBACCO);
        ArrayList<String[]> cannabis = excelProcesser.getTeenagersCol(ColumType.CANNABIS);
        ArrayList<String[]> stimulants = excelProcesser.getTeenagersCol(ColumType.STIMULANTS);
        ArrayList<String[]> sedatives = excelProcesser.getTeenagersCol(ColumType.SEDATIVES);
        ArrayList<String[]> alcoholFirst = excelProcesser.getTeenagersCol(ColumType.ALCOHOLFIRST);
        ArrayList<String[]> alcoholFrec = excelProcesser.getTeenagersCol(ColumType.ALCOHOLFREC);
        ArrayList<String[]> alcoholHowMany = excelProcesser.getTeenagersCol(ColumType.ALCOHOLHOWMANY);
        ArrayList<String[]> firstConsume = excelProcesser.getTeenagersCol(ColumType.FIRSTCONSUME);
        ArrayList<String[]> consumeLast30Days = excelProcesser.getTeenagersCol(ColumType.CONSUMELAST30DAYS);
        ArrayList<String[]> alcoholAndSex = excelProcesser.getTeenagersCol(ColumType.ALCOHOLANDSEX);

        for (int i = 0; i < tobacco.size(); i++){
            Consume consume = new Consume();
            consume.setTobacco(tobacco.get(i)[0].equals("SI"));
            consume.setCannabis(cannabis.get(i)[0].equals("SI"));
            consume.setStimulants(stimulants.get(i)[0].equals("SI"));
            consume.setSedatives(sedatives.get(i)[0].equals("SI"));
            if(!alcoholFirst.get(i)[0].equals("BLANK")){
                String[]  edad= alcoholFirst.get(i)[0].split("\\s+");
                consume.setAlcoholFirst(Integer.valueOf(edad[0]));
            }
            consume.setAlcoholfrequency(alcoholFrec.get(i)[0]);
            consume.setAlcoholHowMany(alcoholHowMany.get(i)[0]);
            if (!firstConsume.get(i)[0].equals("BLANK")){
                String[] edad = firstConsume.get(i)[0].split("\\s+");
                consume.setAgeFirstConsume(Integer.valueOf(edad[0]));
            }
            consume.setDaysConsumedLast30Days(consumeLast30Days.get(i)[0]);
            consume.setAlcoholAndSex(alcoholAndSex.get(i)[0].equals("SI"));
            consumeRepository.save(consume);
            teenagers.get(i).setConsume(consume);
            teenagerRepository.save(teenagers.get(i));
        }
        return teenagers;
    }

    private ArrayList<Teenager> saveFamilyAffluency(Excel excelProcesser, ArrayList<Teenager> teenagers){
        ArrayList<String[]> familyAffluency = excelProcesser.getFamilyAffluency(ColumType.FAMILYAFFLUENCY);
        String members [] = {"Padre", "Madre", "Hermanos", "Pareja", "Otros familiares"};
        for (int pos = 0; pos<teenagers.size(); pos++){
            for (int i = 0; i<familyAffluency.get(pos).length; i++){
                if(!familyAffluency.get(pos)[i].equals("BLANK") && !familyAffluency.get(pos)[i].equals("")){
                    FamilyAffluency f = new FamilyAffluency();
                    f.setMember(members[i]);
                    f.setPlace(familyAffluency.get(pos)[i]);
                    f.setTeenager(teenagers.get(pos));
                    familyAffluencyRepository.save(f);
                }
            }
        }

        return teenagers;
    }

    private ArrayList<Teenager> saveContactAndPlacesFrec(Excel excelProcesser, ArrayList<Teenager> teenagers) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        //ContactFrecJSON
        ArrayList<String[]> personal = excelProcesser.getTeenagersCol(ColumType.PERSONALCONTACT);
        ArrayList<String[]> whatsapp = excelProcesser.getTeenagersCol(ColumType.WHATSAPP);
        ArrayList<String[]> facebook = excelProcesser.getTeenagersCol(ColumType.FACEBOOK);
        ArrayList<String[]> skype = excelProcesser.getTeenagersCol(ColumType.SKYPE);
        ArrayList<String[]> twitter = excelProcesser.getTeenagersCol(ColumType.TWITTER);
        ArrayList<String[]> badoo = excelProcesser.getTeenagersCol(ColumType.BADOO);
        ArrayList<String[]> tuenti = excelProcesser.getTeenagersCol(ColumType.TUENTI);
        ArrayList<String[]> instagram = excelProcesser.getTeenagersCol(ColumType.INSTAGRAM);
        ArrayList<String[]> hangouts = excelProcesser.getTeenagersCol(ColumType.HANGOUTS);
        ArrayList<String[]> other = excelProcesser.getTeenagersCol(ColumType.OTHER);

        //PlacesFrecJSON
        ArrayList<String[]> bar = excelProcesser.getPlacesFrec(ColumType.BAR); Map<String, String> bapMap = new HashMap<>();
        ArrayList<String[]> pub = excelProcesser.getPlacesFrec(ColumType.PUB); Map<String, String> pubMap = new HashMap<>();
        ArrayList<String[]> cafeteria = excelProcesser.getPlacesFrec(ColumType.CAFETERIA); Map<String, String> cafeteriaMap = new HashMap<>();
        ArrayList<String[]> street = excelProcesser.getPlacesFrec(ColumType.CALLE); Map<String, String> streetMap = new HashMap<>();
        ArrayList<String[]> home = excelProcesser.getPlacesFrec(ColumType.HOME); Map<String, String> homeMap = new HashMap<>();
        ArrayList<String[]> park = excelProcesser.getPlacesFrec(ColumType.PARK); Map<String, String> parkMap = new HashMap<>();
        ArrayList<String[]> otherPlace = excelProcesser.getPlacesFrec(ColumType.OTHERPLACE); Map<String, String> otherMap = new HashMap<>();
        String[] headers = {"Lugar en que quedas para tomar una copa", "Frecuencia con la que vas", "¿A qué distancia está de tu casa?", "¿A qué distancia está de tu instituto?"};
        for (int i = 0 ; i < personal.size() ; i++){
            //contact frec
            ContactFrecJSON contactFrec = new ContactFrecJSON();
            contactFrec.setPersonal(personal.get(i)[0]);
            contactFrec.setWhatsapp(whatsapp.get(i)[0]);
            contactFrec.setFacebook(facebook.get(i)[0]);
            contactFrec.setSkype(skype.get(i)[0]);
            contactFrec.setTwitter(twitter.get(i)[0]);
            contactFrec.setBadoo(badoo.get(i)[0]);
            contactFrec.setTuenti(tuenti.get(i)[0]);
            contactFrec.setInstagram(instagram.get(i)[0]);
            contactFrec.setHangouts(hangouts.get(i)[0]);
            contactFrec.setOther(other.get(i)[0]);
            com.Alvaro.TFG.Model.ContactFrec contactFrecModel = new com.Alvaro.TFG.Model.ContactFrec();
            contactFrecModel.setDatos(mapper.writeValueAsString(contactFrec));
            contactFrecRepository.save(contactFrecModel);
            //places frec
            PlacesFrecJSON placesFrec = new PlacesFrecJSON();
            for (int j = 0; j < bar.get(0).length; j++){
                bapMap.put(headers[j], bar.get(i)[j]);
                pubMap.put(headers[j], pub.get(i)[j]);
                parkMap.put(headers[j], park.get(i)[j]);
                cafeteriaMap.put(headers[j], cafeteria.get(i)[j]);
                streetMap.put(headers[j], street.get(i)[j]);
                homeMap.put(headers[j], home.get(i)[j]);
                otherMap.put(headers[j], otherPlace.get(i)[j]);
            }
            placesFrec.setBarResults(bapMap);
            placesFrec.setPubResults(pubMap);
            placesFrec.setParkResults(parkMap);
            placesFrec.setCafeteriaResults(cafeteriaMap);
            placesFrec.setStreetResults(streetMap);
            placesFrec.setHomeResults(homeMap);
            placesFrec.setOtherResults(otherMap);
            PlacesFrec placesFrecModel = new PlacesFrec();
            placesFrecModel.setDatos(mapper.writeValueAsString(placesFrec));
            placesFrecrepository.save(placesFrecModel);

            //savisg data for student
            teenagers.get(i).setContactFrec(contactFrecModel);
            teenagers.get(i).setPlacesFrec(placesFrecModel);
            teenagerRepository.save(teenagers.get(i));
        }
        return teenagers;
    }

    private ArrayList<Teenager> saveTeenagerRelation(Excel excelProcesser, ArrayList<Teenager> teenagers){
        ArrayList<String[]> names = excelProcesser.getTeenagersCol(ColumType.NAME);
        ColumType header = ColumType.RELATIONHEADER;
        ColumType headerConsume = ColumType.RELATIONCONSUME;
        for (int i=0; i<teenagers.size(); i++){
            String name = teenagers.get(i).getSurName()+", "+teenagers.get(i).getName();
            header.setStudent(name);
            headerConsume.setStudent(name);
            ArrayList<String[]> relationsToThisStudent = excelProcesser.relationCol(header, headerConsume);
            for (int j=0; j<teenagers.size(); j++){
                if(i!=j){
                    //discar null relations
                    if(!relationsToThisStudent.get(j)[0].equals("Nunca comparto mi tiempo libre")){
                        TeenagerRelation relation = new TeenagerRelation();
                        relation.setPrimaryTeenager(teenagers.get(j));
                        relation.setRelatedTeenager(teenagers.get(i));
                        relation.setTime(relationsToThisStudent.get(j)[0]);
                        if(relationsToThisStudent.get(j)[1].equals("Sí saldría a tomar una bebida con él/ella")){
                            relation.setConsume("SI");
                        }else{
                            relation.setConsume("NO");
                        }

                        teenagerRelationRepository.save(relation);
                    }

                }
            }
        }
        return teenagers;
    }
    private void saveEgocentricRelations(Excel excelProcesser, ArrayList<Teenager> teenagers){
        ArrayList<ArrayList<String>> datos = excelProcesser.getEgocentric();
        for (int i=0; i<datos.size(); i++){
            int pos=0;
            for(int j = 0; j<10; j++){
                if(!datos.get(i).get(pos).equals("BLANK")){
                    EgocentricFreeTime eft = new EgocentricFreeTime();
                    eft.setName(datos.get(i).get(pos++));
                    eft.setFrecuency(datos.get(i).get(pos++));
                    eft.setPlace(datos.get(i).get(pos++));
                    eft.setTeenager(teenagers.get(i));
                    eft.setCoordX((int)(Math.random()*100));
                    eft.setCoordY((int)(Math.random()*100));
                    egocentricFreeTimeRepository.save(eft);
                }else{
                    pos += 3;
                }
            }
            for(int j = 0; j<10; j++){
                if(!datos.get(i).get(pos).equals("BLANK")){
                    EgocentricConsume ec = new EgocentricConsume();
                    ec.setName(datos.get(i).get(pos++));
                    ec.setType(datos.get(i).get(pos++));
                    ec.setFrecuency(datos.get(i).get(pos++));
                    ec.setPlace(datos.get(i).get(pos++));
                    ec.setTeenager(teenagers.get(i));
                    ec.setCoordX((int)(Math.random()*100));
                    ec.setCoordY((int)(Math.random()*100));
                    egocentricConsumeRepository.save(ec);
                }else{
                    pos += 4;
                }
            }
            for(int j = 0; j<10; j++){
                if(!datos.get(i).get(pos).equals("BLANK")){
                    EgocentricExtraConsume eec = new EgocentricExtraConsume();
                    eec.setName(datos.get(i).get(pos++));
                    eec.setType(datos.get(i).get(pos++));
                    eec.setPlaceFirstMeet(datos.get(i).get(pos++));
                    eec.setPlace(datos.get(i).get(pos++));
                    eec.setTeenager(teenagers.get(i));
                    eec.setCoordX((int)(Math.random()*100));
                    eec.setCoordY((int)(Math.random()*100));
                    egocentricExtraConsumeRepository.save(eec);
                }else{
                    pos += 4;
                }
            }
        }


    }
}
