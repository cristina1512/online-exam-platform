package org.example;

import java.time.LocalDateTime;

public class FillInTheBlankQuestion extends Question {
    private String raspunsCorect;
    private int intervalMinim;
    private int intervalMaxim;

    // constructor
    public FillInTheBlankQuestion(String textIntrebare, String numeAdaugator, LocalDateTime dataAdaugare,
                                  int dificultate, double punctajMaxim, String raspunsCorect) {
        super(textIntrebare, numeAdaugator, dataAdaugare, dificultate, punctajMaxim);
        this.raspunsCorect = raspunsCorect;
        intervalMinim = raspunsCorect.length() - 2;
        intervalMaxim = raspunsCorect.length() + 2;
    }

    public String getRaspunsCorect() {
        return raspunsCorect;
    }

    @Override
    public <T> Correctness checkAnswer(T raspunsStudent) {
        String rsp = (String)raspunsStudent;

        if (raspunsStudent.equals(raspunsCorect))
            return Correctness.CORECT;

        if (rsp.length() >= intervalMinim && rsp.length() <= intervalMaxim &&
                (rsp.contains(raspunsCorect) || raspunsCorect.contains(rsp)))
            return Correctness.PARTIAL_CORECT;

        return Correctness.GRESIT;
    }

    @Override
    public double procentReturnat(Correctness corectitudine) {
        switch (corectitudine) {
            case CORECT:
                return 1;
            case PARTIAL_CORECT:
                return 0.5;
            case GRESIT:
            default:
                return 0;
        }
    }
}
