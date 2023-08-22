import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctOption;

    Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

class Quiz {
    Question[] questions;
    int currentQuestionIndex;
    int score;
    Timer timer;
    boolean answered;

    Quiz(Question[] questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
    }

    public void start() {
        if (currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            System.out.println("Question: " + currentQuestion.question);
            for (int i = 0; i < currentQuestion.options.length; i++) {
                System.out.println((i + 1) + ". " + currentQuestion.options[i]);
            }

            answered = false;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answered) {
                        System.out.println("Time's up! Moving to the next question.");
                        currentQuestionIndex++;
                        start();
                    }
                }
            }, 10000); // 10 seconds timer

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice (1-" + currentQuestion.options.length + "): ");
            int userChoice = scanner.nextInt();

            if (userChoice == currentQuestion.correctOption) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect.");
            }

            answered = true;
            currentQuestionIndex++;
            start();
        } else {
            System.out.println("Quiz completed! Your score: " + score + " out of " + questions.length);
            timer.cancel();
        }
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Question[] questions = {
                new Question("What is the capital of France?", new String[] { "London", "Paris", "Berlin", "Rome" }, 2),
                new Question("Which planet is known as the 'Red Planet'?",
                        new String[] { "Venus", "Jupiter", "Mars", "Saturn" }, 3),
                // Add more questions here
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
