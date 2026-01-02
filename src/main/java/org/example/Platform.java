package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Platform {
    private Map<String, Student> colectieStudenti;
    private Map<String, Examen> colectieExamene;
    private String informatiiPrintare;
    private DateTimeFormatter modelData = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");

    // constructor
    public Platform(String informatiiPrintare) {
        this.colectieExamene = new HashMap<>();
        this.colectieStudenti = new HashMap<>();
        this.informatiiPrintare = informatiiPrintare;
    }

    // metoda ce returneaza raspunsul corect al unei intrebari
    private String raspunsCorect(Question intrebare) {
        if (intrebare instanceof FillInTheBlankQuestion) {
            FillInTheBlankQuestion intr = (FillInTheBlankQuestion) intrebare;
            return intr.getRaspunsCorect();

        } else if (intrebare instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion intr = (MultipleChoiceQuestion) intrebare;
            return intr.getRaspunsCorect();
            
        } else {
            OpenEndedQuestion intr = (OpenEndedQuestion) intrebare;
            return intr.getRaspunsCorect();
        }
    }

    // metoda pentru comanda create_exam
    public void createExam(String denumireExamen, String dataStart, String dataEnd) {
        LocalDateTime dataInceput = LocalDateTime.parse(dataStart, modelData);
        LocalDateTime dataFinal = LocalDateTime.parse(dataEnd, modelData);
        Examen examen = new Examen(denumireExamen, dataInceput, dataFinal);
        colectieExamene.put(examen.getDenumire(), examen);
    }

    // metoda pentru comanda add_question
    public void addQuestion(String timeStamp, String tipIntrebare, String numeExamen, String autorIntrebare, int
                                    dificultateIntrebare, double punctajMaximIntrebare, String textIntrebare,
                            String raspunsCorectIntrebare) {
        LocalDateTime dataAdaugare = LocalDateTime.parse(timeStamp, modelData);
        Examen examenSpecific = colectieExamene.get(numeExamen);
        Question intrebareDeAdaugat = null;

        if (tipIntrebare.equals("open_ended"))
            intrebareDeAdaugat = new OpenEndedQuestion(textIntrebare, autorIntrebare, dataAdaugare,
                    dificultateIntrebare, punctajMaximIntrebare, raspunsCorectIntrebare);

        else if (tipIntrebare.equals("fill_in_the_blank"))
            intrebareDeAdaugat = new FillInTheBlankQuestion(textIntrebare, autorIntrebare, dataAdaugare,
                    dificultateIntrebare, punctajMaximIntrebare, raspunsCorectIntrebare);

        else {
            intrebareDeAdaugat = new MultipleChoiceQuestion(textIntrebare, autorIntrebare, dataAdaugare,
                    dificultateIntrebare, punctajMaximIntrebare,
                    ResponseOption.valueOf(raspunsCorectIntrebare));
        }

        examenSpecific.adaugaIntrebare(intrebareDeAdaugat);
    }

    // metoda pentru comanda list_questions_history
    public void listQuestionsHistory(String timeStamp, String numeExamen) throws IOException {
        String fisierOut = "src/main/resources/" + informatiiPrintare +
                "/questions_history_" + numeExamen + "_" + timeStamp + ".out";
        Examen examen = colectieExamene.get(numeExamen);
        Set<Question> intrebariSortate = examen.sortareCronologicSiAlfabeticAutor();

        File fileOut = new File(fisierOut);
        try (PrintWriter output = new PrintWriter(fileOut)) {
            for (Question intr : intrebariSortate) {
                LocalDateTime data = intr.getDataAdaugare();
                String dataAdaugare = data.format(modelData);

                output.println(dataAdaugare + " | " + intr.getTextIntrebare() + " | " +
                            raspunsCorect(intr) + " | " + intr.getDificultate() + " | " +
                            intr.getPunctajMaxim() + " | " + intr.getNumeAdaugator());
            }
        }
    }

    // metoda pentru comanda print_exam
    public void printExam(String timeStamp, String numeExamen) throws IOException {
        String fisierOut = "src/main/resources/" + informatiiPrintare +
                "/print_exam_" + numeExamen + "_" + timeStamp + ".out";
        Examen examen = colectieExamene.get(numeExamen);
        Set<Question> intrebariSortate = examen.sortareDificultateSiAlfabeticText();

        File fileOut = new File(fisierOut);
        try (PrintWriter output = new PrintWriter(fileOut)) {
            for (Question intr : intrebariSortate) {
                output.println(intr.getPunctajMaxim() + " | " + intr.getTextIntrebare() + " | " +
                                intr.getDificultate() + " | " + raspunsCorect(intr));
            }
        }
    }

    // metoda pentru comanda register_student
    public void registerStudent(String numeStudent, String grupa) {
        Student studentNou = new Student(numeStudent, grupa);
        colectieStudenti.put(numeStudent, studentNou);
    }

    // metoda pentru comanda submit_exam
    public void submitExam(String timeStamp, String numeExamen, String numeStudent,
                           ArrayList<String> raspunsuri) throws IOException {
        LocalDateTime dataSubmit = LocalDateTime.parse(timeStamp, modelData);
        Examen examenSpecific =  colectieExamene.get(numeExamen);
        Student studentSpecific =  colectieStudenti.get(numeStudent);

        if (dataSubmit.isBefore(examenSpecific.getDataInceput()) ||
                dataSubmit.isAfter(examenSpecific.getDataFinal())) {
            String fisierOut = "src/main/resources/" + informatiiPrintare + "/console.out";

            // exceptia la submit
            SubmissionOutsideTimeIntervalException exceptieSubmit =
                    new SubmissionOutsideTimeIntervalException(dataSubmit, numeStudent);

            File fileOut = new File(fisierOut);
            try (PrintWriter output = new PrintWriter(fileOut)) {
                output.println(exceptieSubmit);
            }

            examenSpecific.getScoruriStudenti().put(studentSpecific, 0.0);
            studentSpecific.getExameneDate().put(examenSpecific, 0.0);
            return;
        }

        Set<Question> intrebariSortate = examenSpecific.sortareDificultateSiAlfabeticText();

        double punctajExamen = 0.0;
        int nrRasp = 0;

        // trecem prin intrebari si calculam punctajul
        for (Question intr : intrebariSortate) {
            if (nrRasp < raspunsuri.size()) {
                String raspunsStudent = raspunsuri.get(nrRasp);
                Correctness rezultat = intr.checkAnswer(raspunsStudent);

                double punctajIntrebare = intr.procentReturnat(rezultat) * intr.getPunctajMaxim();
                punctajExamen += punctajIntrebare;
            }
            nrRasp++;
        }

        studentSpecific.getExameneDate().put(examenSpecific, punctajExamen);
        examenSpecific.getScoruriStudenti().put(studentSpecific, punctajExamen);
    }

    // metoda pentru comanda show_student_score
    public void showStudentScore(String timeStamp, String numeStudent, String numeExamen) throws IOException {
        Student studentSpecific =  colectieStudenti.get(numeStudent);
        Examen examenSpecific =  colectieExamene.get(numeExamen);

        double scorStudent = studentSpecific.getExameneDate().get(examenSpecific);

        String mesajAfisare = timeStamp + " | The score of " + numeStudent + " for exam " +
                numeExamen + " is " + String.format("%.2f", scorStudent);

        String fisierOut = "src/main/resources/" + informatiiPrintare + "/console.out";

        File fileOut = new File(fisierOut);
        try (PrintWriter output = new PrintWriter(new FileWriter(fileOut, true))) {
            output.println(mesajAfisare);
        }
    }

    // metoda pentru comanda generate_report
    public void generateReport(String timeStamp, String numeExamen) throws IOException {
        Examen examen = colectieExamene.get(numeExamen);
        String caleFolder = "src/main/resources/" + informatiiPrintare;

        examen.generateReport(caleFolder, timeStamp);
    }
}
