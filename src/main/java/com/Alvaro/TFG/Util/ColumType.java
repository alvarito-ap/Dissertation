package com.Alvaro.TFG.Util;

public enum ColumType {
    NAME("Nombre y apellidos (escoge tu nombre de la lista)"), AGE("Edad"), FECHANACIMIENTO ("Fecha de nacimiento"),
    LUGARNACIMIENTO ("Lugar de nacimiento"), SEXO ("Sexo"), CURSO ("Curso"), GRUPO ("Grupo"), INSTITUTO ("Instituto"),

    ALCOHOLFREC ("¿Con qué frecuencia tomas alguna \"bebida\" que contenga alcohol?"), ALCOHOLHOWMANY ("¿Cuántas \"bebidas alcohólicas\" tomas durante un día típico en el que ha bebido?"),
    ALCOHOLFIRST ("¿Qué edad tenías la PRIMERA VEZ que tomaste cualquier tipo de bebida alcohólica? No incluyas sorbos de bebidas de otra persona"),
    TOBACCO ("Tabaco"), CANNABIS ("Cannabis"), STIMULANTS ("Estimulantes de tipo anfetaminas (speed, anfetaminas, éxtasis, etc,...)"),
    SEDATIVES ("Sedantes o pastillas para dormir (diazepam, alprazolam, midazolam, etc,...)"), FIRSTCONSUME ("¿Qué edad tenías la PRIMERA VEZ que consumiste?"),
    CONSUMELAST30DAYS ("¿Cuántos días has consumido en los últimos 30 días?"), ALCOHOLANDSEX ("¿Crees que la ingestión de bebidas alcohólicas facilita cualquier práctica sexual?"),

    PERSONALCONTACT ("Contacto personal"), PHONECALL ("Llamada telefónica"), WHATSAPP ("Whatsapp"), FACEBOOK ("Facebook"), SKYPE ("Skype"), TWITTER ("Twitter"),
    BADOO ("Badoo"), TUENTI ("Tuenti"), INSTAGRAM ("Instagram"), HANGOUTS ("Hangouts"), OTHER ("Otro medio de contacto"),

    BAR ("Bar"), PUB ("Pub/Discoteca"), CAFETERIA ("Cafetería"), CALLE ("Calle"), HOME ("Casa"), PARK ("Parque"), OTHERPLACE ("other"),

    RELATIONHEADER ("En la siguiente lista señala, cuánto tiempo pasas con tus compañeros de clase"),
    RELATIONCONSUME ("De los siguientes compañeros de clase, señala SOLO aquellos con los que saldrías a tomar una bebida alcohólica (vino, cerveza, sidra, coñac, whisky, ron, vodka, ginebra, ...)"),

    FAMILYAFFLUENCY("¿Dónde consumes con tu padre?");


    private final String text;
    private String student;

    /**
     * @param text
     */
    ColumType(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStudentHeader(){
        return this.text+" ("+student+")";
    }

    public String getStudentRelationConsume(){
        return this.text+" ("+student+")";
    }
}
