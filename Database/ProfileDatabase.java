import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLExecption;

public class ProfileDatabase {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:/C:\Users\DELL\Desktop\MotorcycleSync\database\profiles.db";
        
        try {
        Connection conn = DriverManager.getConnection(url);
        String sql = "SELECT * FROM profiles";
        
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String name = result.getString("name");
                Int age = result.getInteger(age);
                String email= result.getString("email");

                System.out.println(name + " | " + age + " | " + email)l
            }
        } catch (SQLExecption e) {
            System.out.println("Error connecting to SQL");
            e.printStackTrace();
        }
    }
}
