package org.example;

import java.time.LocalDateTime;

public class MultipleChoiceQuestion extends Question {
    private ResponseOption raspunsCorect;

    // constructor
    public MultipleChoiceQuestion(String textIntrebare, String numeAdaugator, LocalDateTime dataAdaugare,
                                  int dificultate, double punctajMaxim, ResponseOption raspunsCorect ) {
        super(textIntrebare, numeAdaugator, dataAdaugare, dificultate, punctajMaxim);
        this.raspunsCorect = raspunsCorect;
    }

    public String getRaspunsCorect() {
        return raspunsCorect.toString();
    }

    @Override
    public <T> Correctness checkAnswer(T raspunsStudent) {
        ResponseOption valoare = ResponseOption.valueOf((String) raspunsStudent);

        if (valoare.equals(raspunsCorect))
            return Correctness.CORECT;

        return Correctness.GRESIT;
    }

    @Override
    public double procentReturnat(Correctness corectitudine) {
        switch (corectitudine) {
            case CORECT:
                return 1;
            case GRESIT:
            default:
                return 0;
        }
    }
}
