import java.util.*;

class User {
    String userId;
    String password;
    String name;

    User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    boolean login(String id, String pass) {
        return userId.equals(id) && password.equals(pass);
    }

    void updateProfile(Scanner sc) {
        System.out.println("\n--- Update Profile ---");
        System.out.print("Enter new name: ");
        sc.nextLine(); // consume newline
        name = sc.nextLine();
        System.out.println("Profile updated successfully!");
    }

    void updatePassword(Scanner sc) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter old password: ");
        String oldPass = sc.next();
        if (oldPass.equals(password)) {
            System.out.print("Enter new password: ");
            password = sc.next();
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Incorrect old password!");
        }
    }
}

class Exam {
    Map<String, String> questions = new LinkedHashMap<>();
    Map<String, String> userAnswers = new LinkedHashMap<>();
    int score = 0;

    Exam() {
        // Add MCQs
        questions.put("Q1. Java is a ___ language?\nA) High level\nB) Low level\nC) Assembly\nD) Machine", "A");
        questions.put("Q2. Which keyword is used to inherit a class in Java?\nA) this\nB) super\nC) extends\nD) implements", "C");
        questions.put("Q3. Which of these is not an OOP concept?\nA) Inheritance\nB) Encapsulation\nC) Compilation\nD) Polymorphism", "C");
        questions.put("Q4. Which method is the entry point of a Java program?\nA) start()\nB) main()\nC) init()\nD) run()", "B");
        questions.put("Q5. Java compiler converts code into?\nA) Bytecode\nB) Machine code\nC) Assembly\nD) Source code", "A");
    }

    void startExam(Scanner sc) {
        System.out.println("\n--- Exam Started ---");
        System.out.println("You have 60 seconds to complete the test. The test will auto-submit when time is over.\n");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\n⏰ Time's up! Auto-submitting your test...");
                evaluate();
                System.exit(0);
            }
        };
        timer.schedule(task, 60000); // 60 seconds timer

        for (Map.Entry<String, String> entry : questions.entrySet()) {
            System.out.println(entry.getKey());
            System.out.print("Your answer: ");
            String ans = sc.next().toUpperCase();
            userAnswers.put(entry.getKey(), ans);
        }

        timer.cancel();
        evaluate();
    }

    void evaluate() {
        score = 0;
        for (Map.Entry<String, String> entry : questions.entrySet()) {
            String correct = entry.getValue();
            String given = userAnswers.getOrDefault(entry.getKey(), "");
            if (correct.equals(given)) score++;
        }
        System.out.println("\n--- Exam Completed ---");
        System.out.println("Your Score: " + score + "/" + questions.size());
    }
}

public class OnlineExamSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = new User("12345", "1234", "Anjali");

        System.out.println("===== Welcome to Online Examination System =====");
        System.out.print("Enter User ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        if (user.login(id, pass)) {
            System.out.println("\nLogin Successful! Welcome, " + user.name + "!");
            int choice;
            do {
                System.out.println("\n===== Main Menu =====");
                System.out.println("1. Update Profile");
                System.out.println("2. Change Password");
                System.out.println("3. Start Exam");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        user.updateProfile(sc);
                        break;
                    case 2:
                        user.updatePassword(sc);
                        break;
                    case 3:
                        Exam exam = new Exam();
                        exam.startExam(sc);
                        break;
                    case 4:
                        System.out.println("Logging out... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again!");
                }
            } while (choice != 4);
        } else {
            System.out.println("Invalid login details!");
        }
        sc.close();
    }
}
