package org.example;
import java.util.*;

public class Student {
    private String nume;
    private String grupa;
    private Map<Examen, Double> exameneDate;

    // constructor
    public Student(String nume, String grupa) {
        this.nume = nume;
        this.grupa = grupa;
        this.exameneDate = new HashMap<>();
    }

    public String getNume() {
        return nume;
    }

    public Map<Examen, Double> getExameneDate() {
        return exameneDate;
    }
}
