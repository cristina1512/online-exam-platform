CORRECTNESS - am scris gradele de corectitudine ale unui raspuns (corect, partial corect, gresit)


EXAMEN - reprezinta un examen
    - am folosit Set<Question> pentru intrebarile examenului deoarece colectia
Set garanteaza unicitatea elementelor
    - am folosit Map<Student, Double> pentru scorurile studentilor deoarece colectia Map este
ce avem nevoie pentru astfel de situatii, in care vrem sa asociem 2 elemente ("o colecție de 
studenți care au participat la examen, împreună cu scorurile obținute de fiecare"). Cheia e
studentul, valoarea e scorul luat de acesta
    - sortareCronologicSiAlfabeticAutor = metoda ce returneaza intrebarile examenului sortate 
cronologic si, daca e cazul, alfabetic dupa autor. Avem nevoie de aceasta pentru comanda
list_questions_history. Am folosit TreeSet deoarece vrem sa stocam ordinat intrebarile + ne ajuta
comparatorul pentru criteriile de sortare
    - sortareDificultateSiAlfabeticText = metoda ce returneaza intrebarile examenului sortate
dupa dificultate si, daca e cazul, alfabetic dupa textul intrebarii. Avem nevoie de aceasta 
pentru comanda  print_exam. Analog sortareCronologicSiAlfabeticAutor ...
    - generateReport = metoda pentru comanda generate_report. Am constuit catre fisierului de 
output, am sortat scorurile studentilor (am folosit TreeMap, din nou, pentru ca ne ajuta la
sortare, in cazul acesta sortare dupa cheia student, folosind comparatorul) si am scris in
fisierul de raport


FILLINTHEBLANKQUESTION , MUTLTIPLECHOICEQUESTION, OPENENDEDQUESTION- extensii a clasei QUESTION
    - implementeaza metoda checkAnswer(din clasa QUESTION, verifica daca un raspuns e corect,
partial corect sau gresit) si metoda procentReturnat(din interfata GRADABLE, returneaza procentul
ce ajuta la calcularea scorului) in concordanta cu cerinta temei


GRADABLE - interfata pentru intrebari, contine metoda abstracta procentReturnat


QUESTION - clasa abstracta pentru tipurile de intrebari, contine atributele comune, gettere,
settere si metoda abstracta checkAnswer


RESPONSEOPTION - am scris optiunile unei intrebari de tip grila (A, B, C, D)


STUDENT - clasa pentru student
    - am folosit Map<Examen, Double> pentru examenele date deoarece colectia Map ne ajuta in
asocierea unui examen cu o nota/un scor


SUBMISSIONOUTSIDETIMEINTERVALEXCEPTION - clasa de tip exceptie care  arunca o exceptie cand 
raspunsurile  unui examen au fost trimise in afara intervalului permis
    - in metoda toString construim mesajul ce va fi afisat, formatam timpul folosind un 
DateTimeFormatter


PLATFORM - clasa care contine metodele principale asociate comenzilor
    - prin modelData definestim formatul standard de data
    - pentru colectiile de studenti si examene folosim Hashmap pentru ca ne ajuta la cautare
dupa cheie, ne ofera acces rapid la date, este eficient
    - prin metoda raspunsCorect gasim raspunsul corect al unei intrebari, metoda primeste ca 
parametru intrebarea sub forma de Question si o converteste pe tipul de intrebare pentru
a accesa raspunsul corect prin getter
    - la metodele ce presupun scrierea intr-un fisier am folosit "throws IOException" 
deoarece pot aparea probleme in operatiile cu fisiere


MAIN - unde se citesc comenzile
    - preluam fisierul de output din argumente
    - creem obiectul Platform care gestioneaza logica aplicatiei
    - deschidem fisierul input.in si se citim comenzile linie cu linie folosind Scanner
    - separam comanda in segmente folosind delimitatorul " | " si in functie de comanda,
apelam metoda corespunzatoare din Platform
