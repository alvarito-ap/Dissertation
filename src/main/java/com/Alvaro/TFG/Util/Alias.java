package com.Alvaro.TFG.Util;

import java.util.Stack;

public class Alias {
    private String [] posibilities = {"Abraham", "Clemente", "Santos", "Alfredo", "Claudio", "Dario", "German", "Ana", "Elena", "Barbara", "Isaac", "Benito", "Benita", "Esteban", "Ignacio", "Bernardo"
            , "Bernarda", "Pilar", "Monserrat", "Leandro", "Leonidad", "Leonor", "Napoleon", "Florentino", "Mariano", "Pedro", "Pablo", "Nicolas", "Maria", "Marco", "Pio", "Navor", "Sisto", "Romeo"
            , "Anita", "Hugo", "Carla", "David", "Alberto"};
    private Stack<String> pila = new Stack<>();

    public Alias(int numero){
        int pattern = (int)(Math.random()*500%posibilities.length);
        for (int i = 0 ; i < posibilities.length && i < numero; i++){
            pila.push(posibilities[(i*pattern)%posibilities.length]);
        }
    }

    public String get(){
        return pila.peek();
    }
}
