package org.example;

import java.time.LocalDateTime;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class Examen {
    private String denumire;
    private LocalDateTime dataInceput;
    private LocalDateTime dataFinal;
    private Set<Question> intrebariExamen;
    private Map<Student, Double> scoruriStudenti;

    // constructor
    public Examen(String denumire, LocalDateTime dataInceput, LocalDateTime dataFinal) {
        this.denumire = denumire;
        this.dataInceput = dataInceput;
        this.dataFinal = dataFinal;
        this.intrebariExamen = new HashSet<>();
        this.scoruriStudenti = new HashMap<>();
    }

    // gettere si settere
    public String getDenumire() {
        return denumire;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public LocalDateTime getDataInceput() {
        return dataInceput;
    }
    public void setDataInceput(LocalDateTime dataInceput) {
        this.dataInceput = dataInceput;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }
    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Map<Student, Double> getScoruriStudenti() {
        return scoruriStudenti;
    }

    public Set<Question> getIntrebariExamen() {
        return intrebariExamen;
    }

    public void adaugaIntrebare(Question intrebari) {
        intrebariExamen.add(intrebari);
    }

    // metoda prin care se sorteaza intrebarile pentru comanda list_questions_history
    public Set<Question> sortareCronologicSiAlfabeticAutor() {
        Set<Question> intrebariSortate = new TreeSet<>(
                Comparator.comparing(Question::getDataAdaugare)
                        .thenComparing(Question::getNumeAdaugator));
        for (Question intr : intrebariExamen)
            intrebariSortate.add(intr);
        return intrebariSortate;
    }

    // metoda prin care se sorteaza intrebarile pentru comanda print_exam
    public Set<Question> sortareDificultateSiAlfabeticText() {
        Set<Question> intrebariSortate = new TreeSet<>(
                Comparator.comparing(Question::getDificultate)
                        .thenComparing(Question::getTextIntrebare));
        for (Question intr : intrebariExamen)
            intrebariSortate.add(intr);
        return intrebariSortate;
    }

    // metoda pentru comanda generate_report
    public void generateReport(String informatiiPrintare, String timeStamp) throws IOException {
        String fisierOut = informatiiPrintare + "/exam_report_" + denumire + "_" + timeStamp + ".out";

        Map<Student, Double> listaSortataScoruri = new TreeMap<>(Comparator.comparing(Student::getNume));
        for (Map.Entry<Student, Double> pereche : scoruriStudenti.entrySet())
            listaSortataScoruri.put(pereche.getKey(), pereche.getValue());

        File fileOut = new File(fisierOut);
        try (PrintWriter output = new PrintWriter(fileOut)) {
            for (Map.Entry<Student, Double> pereche : listaSortataScoruri.entrySet()) {
                String linie = pereche.getKey().getNume() + " | " + String.format("%.2f", pereche.getValue());
                output.println(linie);
            }
        }
    }
}
