Online Examination Platform
A robust, Java-based simulation of an online examination system (similar to Moodle) designed 
to manage exams, diverse question types, student enrollments, and automated grading. 

Features
Dynamic Exam Management: Create and schedule exams with specific start and end timestamps.
Diverse Question Types: 
* Multiple Choice: Standard A/B/C/D options
* Fill-in-the-blank: Supports flexible matching with a  +-2 character margin
* Open-Ended: Implements partial grading based on substring matching and length deviation (+-30%)

Automated Grading Engine: Features a Gradable interface that calculates scores (100%, 70%, 50%, or 0%) based on 
response accuracy.
Automated Reporting: Generates chronological question histories, formatted exam papers, and student performance reports.

Strict Validation: Custom exception handling for submissions made outside the allowed exam time window.

The following collections were used:
* HashMap / TreeMap: Used for efficient retrieval of students and exams by name
* TreeSet: Leveraged with custom Comparators to handle complex sorting logic (for example sorting questions by 
difficulty, then alphabetically by text) without manual sorting algorithms

Input Command Format
The system processes commands in the following format:
timestamp | command_name | parameter1 | parameter2 | ...

Example Commands:
* create_exam: Initializes a new exam session
* add_question: Adds a specific question type to an existing exam
* submit_exam: Processes student answers and calculates the final grade
* generate_report: Outputs a sorted list of student results to a file

How to Run
1. Clone the repository:
# git clone https://github.com/your-username/online-exam-platform.git
2. Compile the project:
# javac org/example/*.java
3. Run the application:
Provide the test folder name as a command-line argument. The program expects an input.in file in src/main/resources/<test_folder>/.
# java org.example.Main "00-test"
