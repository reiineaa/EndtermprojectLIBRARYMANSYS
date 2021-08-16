import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ManageUserInformation extends Postgres{


    public void seeAllUsers() {
        String SQL_SELECT="SELECT * FROM login";
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                System.out.println(
                        "userName='" + resultSet.getString("username") + '\'' +
                        ", password='" + resultSet.getString("pass") + '\''
                        );
                System.out.println("");
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int removeUser(int removeId){
        String SQL_SELECT="DELETE FROM login WHERE id = ?";
        int rowsAffected = 0;
        try (Connection conn = DriverManager.getConnection(getUrl(), getPostgres(), getPassword());
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)){
                stmt.setInt(1,removeId);
            rowsAffected = stmt.executeUpdate();

        }catch (Exception e) {
            System.out.println(e.toString());
        }
        return rowsAffected;

    }



}

