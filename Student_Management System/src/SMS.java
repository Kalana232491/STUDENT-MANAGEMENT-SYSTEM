import java.io.*;
import java.util.*;

//Create class for module
class Module {
    private int marks; // Module marks

    public Module() {
        this.marks = 0;
    } // set marks = 0

    public int read_Marks() {
        return marks;
    } // Return value

    public void set_Marks(int marks) {
        this.marks = marks;
    } //set marks
}

// Create a class for students
class Student {
    String student_ID;
    String C_student_name;
    Module[] module_marks; // Array to store 3 marks for module
    String student_grade;


    public Student(String student_ID, String C_student_name) {
        this.student_ID = student_ID;
        this.C_student_name = C_student_name;
        this.module_marks = new Module[3]; //Initialize array for 3 modules
        for (int x = 0; x < 3;x++) {
            this.module_marks[x] = new Module(); // Initialize each module with a new Module object
        }
        this.student_grade = "No grade"; //Set student_grade = "No grade""
    }

    public int read_module_marks(int index) {
        return module_marks[index].read_Marks();
    }

    public void set_module_marks(int index, int marks) {
        module_marks[index].set_Marks(marks); // Set marks for module at specified index
        cal_grade();  // Call grade calculator
    }

    // Cal total
    int cal_Total() {
        return Arrays.stream(module_marks).mapToInt(Module::read_Marks).sum();
    }

    // Cal average
    double cal_Average() {
        return cal_Total()/3.0;
    }

    // Call grade
    void cal_grade () {
        double average = cal_Average();
        if (average >= 80) {
            student_grade = "Distinction";
        } else if (average >= 70) {
            student_grade = "Merit";
        } else if (average >= 40) {
            student_grade = "Pass";
        } else {
            student_grade = "Fail";
        }
    }

    // fromString method
    public static Student fromString(String records) {
        String[] parts =records.split(",");
        Student student_details = new Student(parts[0],parts[1]);
        student_details.set_module_marks(0, Integer.parseInt(parts[2]));
        student_details.set_module_marks(1, Integer.parseInt(parts[3]));
        student_details.set_module_marks(2, Integer.parseInt(parts[4]));
        return student_details;
    }

}


public class SMS {
    static final int max_students = 100; // Maximum students count
    static Student[] students_details = new Student[max_students]; // Array for store students details
    static int students_count = 0; //Current student count

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                // Display the main menu
                System.out.println("|-----------------------------------MAIN MENU------------------------------------|");
                System.out.println("|  1. Check available seats                                                      |");
                System.out.println("|  2. Register student (with ID)                                                 |");
                System.out.println("|  3. Find student (with student ID)                                             |");
                System.out.println("|  4. Delete student                                                             |");
                System.out.println("|  5. Store student details into a text file                                     |");
                System.out.println("|  6. Load student details from the file to the system                           |");
                System.out.println("|  7. View the list of students based on their names                             |");
                System.out.println("|  8. Add student marks and update student name                                  |");
                System.out.println("|  0. Exit                                                                       |");
                System.out.println("|--------------------------------------------------------------------------------|");
                System.out.print("Select your choice :"); //Get user's choice

                int choice = input.nextInt();

                if (choice == 1) {
                    available_seat_count();
                } else if (choice == 2) {
                    register_student(input);
                } else if (choice == 3) {
                    find_student(input);
                } else if (choice == 4) {
                    delete_student(input);
                } else if (choice == 5) {
                    save_student_details();
                } else if (choice == 6) {
                    load_student_details();
                } else if (choice == 7) {
                    view_students_names();
                } else if (choice == 8) {
                    manage_results(input);
                } else if (choice == 0) {
                    System.out.println("Exit program");
                    break;
                } else {
                    System.out.println("Invalid choice..!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input...! Enter correct input");
                input.next(); // Clear input..!
            }
        }
    }

    // Display available seats
    public static void available_seat_count(){
        System.out.println("|---------------------------------AVAILABLE SEATS--------------------------------|");
        System.out.println("Available seat count is "+(max_students-students_count)+" seat(s)"); // Check available seats
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Register students
    public static void register_student(Scanner input) {
        System.out.println("|--------------------------------REGISTER STUDENT--------------------------------|");
        if (students_count >= max_students) {
            System.out.println("No available seats..!");
        }
        else {
            System.out.print("Enter student ID number (Ex:0001/0002) :");
            String student_ID = input.next(); // Get student ID
            input.nextLine();
            System.out.print("Enter student name :");
            String C_student_name = capital_first(input.next()); // Get converted capitalize name
            students_details[students_count] = new Student(student_ID , C_student_name);
            students_count++;
            System.out.println("|----------------------------REGISTRATION SUCCESSFULLY---------------------------|");
            System.out.println("|--------------------------------------------------------------------------------|");
        }
    }

    // Search index by student ID
    public static int search_array_element_index (String student_ID){
        for (int count_index = 0; count_index < students_count; count_index++){
            if(students_details[count_index].student_ID.equals(student_ID)) {
                return count_index;
            }
        }
        return -1;
    }

    // Find student by student ID
    public static void find_student (Scanner input) {
        System.out.println("|----------------------------------FIND STUDENT----------------------------------|");
        System.out.print("Enter search student ID number :");
        String student_ID = input.next();
        int find_student_ID = search_array_element_index(student_ID); // Check index of student
        if (find_student_ID == -1){
            System.out.println("Student ID is not found..!");
        }
        else{
            System.out.println("This student name is "+students_details[find_student_ID].C_student_name);
        }
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Delete students details
    public static void delete_student(Scanner input) {
        System.out.println("|---------------------------------DELETE STUDENT---------------------------------|");
        System.out.print("Enter delete student ID number :");
        String student_ID = input.next();
        int delete_student_ID = search_array_element_index(student_ID);
        if (delete_student_ID == -1) {
            System.out.println("Student ID is not found..!");
        }
        else {
            students_details[delete_student_ID] = students_details[--students_count]; // Delete student ID and readjust array
            System.out.println("|-------------------------------DELETE SUCCESSFULLY------------------------------|");
        }
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Backup data to text file
    public static void save_student_details() {
        System.out.println("|----------------------------------BACKUP DATA-----------------------------------|");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("record.txt"))) {
            writer.write(students_count + "\n");
            for (int x = 0; x < students_count; x++) {
                writer.write(students_details[x].student_ID+"," + students_details[x].C_student_name + "," + students_details[x].module_marks[0].read_Marks() + "," + students_details[x].module_marks[1].read_Marks() + "," + students_details[x].module_marks[2].read_Marks() +"\n"); //Write data line by line
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("|----------------------------DATA BACKUP SUCCESSFULLY----------------------------|");
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Load data from text file
    public static void load_student_details () {
        System.out.println("|------------------------------------LOAD DATA------------------------------------|");
        students_count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("record.txt"))) {
            students_count = Integer.parseInt(reader.readLine()); // Read 1st line in text file
            for (int x = 0; x < students_count; x++) {
                String records = reader.readLine(); // Read data line by line
                students_details[x] = Student.fromString(records);
            }
            System.out.println("|-----------------------------DATA LOAD SUCCESSFULLY------------------------------|");

        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'record.txt' not found..!");
        } catch (IOException e) {
            System.err.println("Reeading file error");
        }
        catch (NumberFormatException e) {
            System.err.println("Error parsing student count from file: " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Data error in file");
        }
        System.out.println("|---------------------------------------------------------------------------------|");
    }

    // View students names
    public static void view_students_names() {
        System.out.println("|-----------------------------VIEW STUDENT NAME LIST------------------------------|");
        Arrays.sort(students_details,0,students_count, Comparator.comparing(s -> s.C_student_name)); //Sort array
        for (int x = 0; x < students_count; x++){
            System.out.println("  "+(x+1)+") "+students_details[x].C_student_name);
        }
        System.out.println("|---------------------------------------------------------------------------------|");
    }

    // Capitalize the first of a word
    public static String capital_first(String word) {
        String firstletter = word.substring(0,1).toUpperCase();
        String lastletters = word.substring(1).toLowerCase();
        return firstletter+lastletters;
    }

    // Sub menu
    public static void manage_results (Scanner input) {
        while (true){
            // Display sub menu
            System.out.println("|------------------------------------SUB MENU------------------------------------|");
            System.out.println("|  a. Update student names                                                       |");
            System.out.println("|  b. Enter your module marks                                                    |");
            System.out.println("|  c. Create summary report                                                      |");
            System.out.println("|  d. Create complete report                                                     |");
            System.out.println("|  e. Exit sub main menu                                                         |");
            System.out.println("|--------------------------------------------------------------------------------|");

            System.out.print("Select your choice :");
            String choice = input.next(); // Get user's choice sub menu

            if(choice.equals("a")) {
                update_student_name(input);
            }
            else if (choice.equals("b")) {
                add_module_marks(input);
            }
            else if (choice.equals("c")) {
                summary_report();
            }
            else if (choice.equals("d")) {
                complete_report();
            }
            else if (choice.equals("e")) {
                System.out.println("Exit sub main menu");
                break;
            }
            else {
                System.out.println("Invalid input..!");
            }
        }
    }

    // update a students names
    public static void update_student_name (Scanner input) {
        System.out.println("|------------------------------UPDATE STUDENT NAME-------------------------------|");
        System.out.print("Enter student ID :");
        String student_ID = input.next();
        input.nextLine();
        System.out.print("Enter update student name :");
        String C_student_name = capital_first(input.next());
        int update_student_id = search_array_element_index(student_ID);
        if (update_student_id == -1) {
            System.out.println("Student ID not found..!");
        }
        else {
            students_details[update_student_id].C_student_name = C_student_name;
            System.out.println("|------------------------------UPDATE SUCCESSFULLY-------------------------------|");
        }
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Add module marks for a student
    public static void add_module_marks (Scanner input) {
        System.out.println("|------------------------------ADD STUDENTS MARKS--------------------------------|");
        System.out.print("Enter student ID :");
        String student_ID = input.next();
        int marks_ID = search_array_element_index(student_ID);
        if (marks_ID == -1) {
            System.out.println("Student ID not found..!");
        }
        else {
            System.out.print("Enter module 1 mark :");
            students_details[marks_ID].set_module_marks(0, input.nextInt()); // Add marks to array
            System.out.print("Enter module 2 mark :");
            students_details[marks_ID].set_module_marks(1, input.nextInt());
            System.out.print("Enter module 3 mark :");
            students_details[marks_ID].set_module_marks(2, input.nextInt());
            System.out.println("|-----------------------------ADD MARKS SUCCESSFULLY-----------------------------|");
        }
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    //Create asummary report
    public static void summary_report() {
        int[] pass_students = new int[3];

        for (int x = 0; x < students_count; x++) {
            for (int y = 0; y < 3; y++) {
                if (students_details[x].read_module_marks(y) >= 35) { //calculate pass students
                    pass_students[y]++;
                }
            }
        }
        System.out.println("|--------------------------------SUMMARY REPORT----------------------------------|");
        System.out.println("Total number of registrations :"+students_count);
        System.out.println("Module 1 pass total number of students :"+pass_students[0]);
        System.out.println("Module 2 pass total number of students :"+pass_students[1]);
        System.out.println("Module 3 pass total number of students :"+pass_students[2]);
        System.out.println("|--------------------------------------------------------------------------------|");
    }

    // Complete reports
    public static void complete_report() {
        System.out.println("|----------------------------------------------------------------------------FULL REPORT----------------------------------------------------------------------------|");
        Arrays.sort(students_details, 0,students_count, (a, z) -> Double.compare(z.cal_Average(), a.cal_Average())); // Sort data
        System.out.printf("%-10s %-20s %10s %20s %20s %20s %20s %20s\n", "Student ID", "Student Name", "Module 1 marks", "Module 2 marks", "Module 3 marks", "Total marks", "Average mark", "Student Grade"); // Report heading
        for(int x = 0; x < students_count; x++) {
            System.out.printf("%-10s %-20s %10d %20d %20d %20d %20.2f %20s\n", students_details[x].student_ID, students_details[x].C_student_name, students_details[x].read_module_marks(0), students_details[x].read_module_marks(1), students_details[x].read_module_marks(2), students_details[x].cal_Total(), students_details[x].cal_Average(), students_details[x].student_grade);
        }
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    }

}
