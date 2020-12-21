package com.Alvaro.TFG.Util;

import java.util.ArrayList;

public class ExcelColumInfo {
    private ArrayList<String[]> datos;

    public ExcelColumInfo(ArrayList<String[]> datos){
        this.datos = datos;
    }

    public int basica(ColumType tipo){
        for (int i = 0; i<datos.get(0).length; i++){
            if (datos.get(0)[i].equals(tipo.toString())){
                return i;
            }
        }
        return -1;
    }

    public int placesFrec(ColumType tipo){
        for (int i = 0; i<datos.get(1).length; i++){
            if (datos.get(1)[i].equals(tipo.toString())){
                return i;
            }else if(tipo.equals(ColumType.OTHERPLACE) && datos.get(1)[i].equals(ColumType.PARK.toString())){
                return i+4;
            }
        }
        return -1;
    }

    public int columRelationClassmate(String header){
        for (int i = 0; i<datos.get(0).length; i++){
            if (datos.get(0)[i].equals(header)){
                return i;
            }
        }
        return -1;
    }

    public int [] egocentricColums(){
        int pos = 0; int res[] = new int[3];
        for (int i = 0; i<datos.get(0).length; i++){
            if(datos.get(0)[i].equals("1-") || datos.get(0)[i].equals("1_") || datos.get(0)[i].equals("1.")){
                res[pos] = i;
                pos++;
            }
        }
        System.out.println(pos);
        return res;
    }

    public int familyAffluency(String header){
        for (int i = 0; i<datos.get(0).length; i++){
            if (datos.get(0)[i].equals(header)){
                return i;
            }
        }
        return -1;
    }

}
