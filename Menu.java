import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Menu extends Postgres {


    private transient Scanner input = new Scanner(System.in);
    private String[] choices = {"1", "2", "3", "4", "5", "6", "7"}; // Array for switch cases
    private boolean running = true; // Boolean for main menu

    static LibrarySystem newLibrarySystem = new LibrarySystem();
    static ManageUserInformation userInformation = new ManageUserInformation();



    public Menu() {
        mainMenu();
    }


    private void mainMenu(){

        String choice = "";

        while (running) {
            System.out.println(
                    "[1] User login" + "\n" +
                            "[2] Create a new user" + "\n" +
                            "[3] Exit from program");

            try {
                // User choice
                choice = input.nextLine();

                // Check if array above contains the choice made by user
                if (Arrays.asList(choices).contains(choice)) {
                    switch (choice) {

                        // Must be chosen to load files with information

                        case "1":
                            System.out.println("User login");
                            adminLogin();
                            break;


                        case "2":
                            System.out.println("Create a new user");
                            createNewMember();
                            break;

                        case "3":
                            System.out.println("Have a nice day! ");
                            System.exit(0);

                            break;

                        default:
                            System.out.println("Default! ");
                            break;
                    }

                } else {
                    System.out.println("You can only make a choice between 1 and 4. Please try again. " + "\n");
                }


            } catch (Exception e) {
                System.out.println("Oops! Something went wrong. ");
            }
        }
    }

    public  void adminLogin() throws SQLException {
        Connection con = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
        Statement stmt = con.createStatement();
        System.out.println("Please enter your user name: ");
        String userName = input.nextLine();
        System.out.println("Please enter your password: ");
        String password = input.nextLine();
        ResultSet rs = stmt.executeQuery("SELECT * FROM login WHERE username='"+userName+"' and pass='"+password+"'");
        if (rs.next()){
            System.out.println("Welcome!" );
            adminMenu();
        }
        else{
            System.out.println("Invalid user name and password");
            con.close();
        }

    }

    private   void adminMenu(){
        String admin = "admin";
        String choice = "";
        boolean run = true;

        while(run) {
            System.out.println("Make one of the following choices: " + "\n" +
                    "1. Add new book" + "\n" +
                    "2. Remove book " + "\n" +
                    "3. See all books in the library " + "\n" +
                    "4. See all available books" + "\n" +
                    "5. List of all users" + "\n" +
                    "6. Remove user" + "\n" +
                    "7. Back to main menu" + "\n");

            try {
                // User choice
                choice = input.nextLine();

                // Check if array above contains the choice made by user
                if (Arrays.asList(choices).contains(choice)) {
                    switch (choice) {
                        case "1":
                            System.out.println("Adding new book");
                            addNewBook();
                            break;
                        case "2":
                            System.out.println("Remove book");
                            newLibrarySystem.removeBookFromArray(searchBook());
                            break;


                        case "3":
                            System.out.println("All books in the library. ");
                            newLibrarySystem.allLibraryBooks();
                            break;

                        case "4":
                            System.out.println("Available books: ");
                            newLibrarySystem.allAvailableBooksToRent();
                            break;

                        case "5":
                            System.out.println("See list of all users");
                            userInformation.seeAllUsers();
                            break;

                        case "6":
                            System.out.println("Remove user ");
                            userInformation.removeUser(searchUser());
                            break;

                        case "7":
                            System.out.println("Go back to main menu. ");
                            run = false;
                            break;

                        default:
                            System.out.println("Default! ");
                            break;
                    }

                } else {
                    System.out.println("You can only make a choice between 1 and 8. Please try again. " + "\n");
                }


            } catch (Exception e) {
                System.out.println("Oops! Something went wrong. ");
            }
        }
    }



    private int searchBook(){
        System.out.println("Search title: ");
        int i = Integer.parseInt(input.nextLine());
        return i;
    }

    private int searchUser(){
        System.out.println("Search user id: ");
        int i = Integer.parseInt(input.nextLine());
        return i;
    }


    private int createNewMember() {
        System.out.println("Please enter a user id: ");
        int userId = Integer.parseInt(input.nextLine());
        System.out.println("Please enter a user name: ");
        String userName = input.nextLine();
        System.out.println("Please enter a password: ");
        String password = input.nextLine();
        int affectedRows=0;
        String SQL = "INSERT INTO login(id, username, pass) "
                + "VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setInt(1, userId);
            stmt.setString(2, userName);
            stmt.setString(3, password);
            affectedRows = stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("Username and password added! ");
        return affectedRows;


    }
    private int addNewBook(){
        int affectedRows=0;
        System.out.println("Please add the book id: ");
        int bookId = Integer.parseInt(input.nextLine());
        System.out.println("Please add the book title: ");
        String bookTitle = input.nextLine();
        System.out.println("Please add the author: ");
        String author = input.nextLine();
        System.out.println("Please add information about the book: ");
        String aboutThisBook = input.nextLine();
        boolean b = true;
        String SQL = "INSERT INTO book(id,bookTitle,author,av,aboutThisBook) "
                + "VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setInt(1, bookId);
            stmt.setString(2, bookTitle);
            stmt.setString(3, author);
            stmt.setBoolean(4, b);
            stmt.setString(5, aboutThisBook);
            affectedRows = stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println(e.toString());
        }
        return affectedRows;
    }


}