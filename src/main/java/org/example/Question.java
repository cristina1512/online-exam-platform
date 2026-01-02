package org.example;

import java.time.LocalDateTime;

public abstract class Question implements Gradable {
    private String textIntrebare;
    private String numeAdaugator;
    private LocalDateTime dataAdaugare;
    private int dificultate;
    private double punctajMaxim;

    // constructor
    public Question(String textIntrebare, String numeAdaugator, LocalDateTime dataAdaugare, int dificultate,
                    double punctajMaxim) {
        this.textIntrebare = textIntrebare;
        this.numeAdaugator = numeAdaugator;
        this.dataAdaugare = dataAdaugare;
        this.dificultate = dificultate;
        this.punctajMaxim = punctajMaxim;
    }

    // gettere si settere
    public String getTextIntrebare() {
        return textIntrebare;
    }
    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }

    public String getNumeAdaugator() {
        return numeAdaugator;
    }
    public void setNumeAdaugator(String numeAdaugator) {
        this.numeAdaugator = numeAdaugator;
    }

    public LocalDateTime getDataAdaugare() {
        return dataAdaugare;
    }
    public void setDataAdaugare(LocalDateTime dataAdaugare) {
        this.dataAdaugare = dataAdaugare;
    }

    public int getDificultate() {
        return dificultate;
    }
    public void setDificultate(int dificultate) {
        this.dificultate = dificultate;
    }

    public double getPunctajMaxim() {
        return punctajMaxim;
    }
    public void setPunctajMaxim(double punctajMaxim) {
        this.punctajMaxim = punctajMaxim;
    }

    public abstract <T> Correctness checkAnswer(T raspuns);
}
