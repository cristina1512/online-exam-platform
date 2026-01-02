package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubmissionOutsideTimeIntervalException extends RuntimeException {
    private LocalDateTime timp;
    private String numeStudent;

    // constructor
    public SubmissionOutsideTimeIntervalException(LocalDateTime timp, String numeStudent)
    {
        this.timp = timp;
        this.numeStudent = numeStudent;
    }

    @Override
    public String toString() {
        DateTimeFormatter modelData = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
        return timp.format(modelData) + " | Submission outside of time interval for student " + numeStudent;
    }
}
