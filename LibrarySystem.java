import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LibrarySystem extends Postgres{


    // Removing books both from all books in the library as well as the available books list
    public int removeBookFromArray(int removeBook){
        String SQL_SELECT="DELETE FROM book WHERE id = ?";
        int rowsAffected = 0;
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)){
            stmt.setInt(1,removeBook);
            rowsAffected = stmt.executeUpdate();

        }catch (Exception e) {
            System.out.println(e.toString());
        }
        return rowsAffected;
    }


    // All library books in the system (both available and non available)
    public void allLibraryBooks(){
        String SQL_SELECT="SELECT * FROM book";
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                System.out.println(
                "TITLE: '" + resultSet.getString("bookTitle") + '\''  + "\n" +
                "AUTHOR: '" + resultSet.getString("author") + '\''  + "\n" +
                "AVAILABLE: '" + resultSet.getBoolean("av") + '\''  + "\n" +
                "ABOUT: '" + resultSet.getString("aboutThisBook") + '\''  + "\n"
                );
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    // See all available books to rent
    public void allAvailableBooksToRent(){
        String SQL_SELECT="SELECT * FROM book WHERE av=true";
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                System.out.println(
                        "TITLE: '" + resultSet.getString("bookTitle") + '\''  + "\n" +
                        "AUTHOR: '" + resultSet.getString("author") + '\''  + "\n" +
                        "AVAILABLE: '" + resultSet.getBoolean("av") + '\''  + "\n" +
                        "ABOUT: '" + resultSet.getString("aboutThisBook") + '\''  + "\n"
                );
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }




}


