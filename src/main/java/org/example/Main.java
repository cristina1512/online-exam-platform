package org.example;

import java.io.IOException;
import java.util.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String informatiiPrintare = args[0];
        Platform platforma = new Platform(informatiiPrintare);

        File fisierIn = new File("src/main/resources/" + informatiiPrintare + "/input.in");
        try (Scanner scanner = new Scanner(fisierIn)) {
            while (scanner.hasNextLine()) {
                String comanda = scanner.nextLine();
                String[] segmenteComanda = comanda.split(" \\| ");
                String timeStamp = segmenteComanda[0];
                String numeComanda = segmenteComanda[1];

                if (numeComanda.equals("create_exam")) {
                    platforma.createExam(segmenteComanda[2], segmenteComanda[3], segmenteComanda[4]);

                } else if (numeComanda.equals("add_question")) {
                    int dificultate = Integer.parseInt(segmenteComanda[5]);
                    double punctajMaxim = Double.parseDouble(segmenteComanda[6]);
                    platforma.addQuestion(timeStamp, segmenteComanda[2], segmenteComanda[3], segmenteComanda[4],
                            dificultate, punctajMaxim, segmenteComanda[7], segmenteComanda[8]);

                } else if (numeComanda.equals("list_questions_history")) {
                    platforma.listQuestionsHistory(timeStamp, segmenteComanda[2]);

                } else if (numeComanda.equals("print_exam")) {
                    platforma.printExam(timeStamp, segmenteComanda[2]);

                } else if (numeComanda.equals("register_student")) {
                    platforma.registerStudent(segmenteComanda[2], segmenteComanda[3]);

                } else if (numeComanda.equals("submit_exam")) {
                    ArrayList<String> raspunsuri = new ArrayList<>();
                    for (int i = 4; i < segmenteComanda.length; i++)
                        raspunsuri.add(segmenteComanda[i]);
                    platforma.submitExam(timeStamp, segmenteComanda[2], segmenteComanda[3], raspunsuri);

                } else if (numeComanda.equals("show_student_score")) {
                    platforma.showStudentScore(timeStamp, segmenteComanda[2], segmenteComanda[3]);

                } else if (numeComanda.equals("generate_report")) {
                    platforma.generateReport(timeStamp, segmenteComanda[2]);

                } else if (numeComanda.equals("exit")) {
                    return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}