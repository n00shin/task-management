package ir.chica.task.aspect;

import ir.chica.task.dto.UserDto;
import ir.chica.task.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Component
public class DbAccess<MyConnection> {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MyAop.class);
    MyConnection conn;

    private final WebApplicationContext context;

    public DbAccess(WebApplicationContext context) {
        this.context = context;
    }

    public void initCon() {
        conn = (MyConnection) context.getBean(DbAccess.class);
        conn.openConnection();
    }

    public void closeCon() {
        conn.closeConnection();
    }



    public synchronized List<UserDto> getResultList(String sql) {

        ResultSet rs = null;
        List<UserDto> users = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.getCon().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new UserDto(rs.getLong( 1 ),rs.getString( 2 ),rs.getString( 3 ),rs.getString( 4 )));

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }

        return users;

    }


    public void executeUpdate(String sql, List<String> args) {
        try {
            PreparedStatement stmt = conn.getCon().prepareStatement(sql);
            for (int i = 0; i < args.size(); i++) {

                stmt.setString(i + 1, args.get(i));
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.info("Throwing exception from dbaccess" + ex.getMessage());
            throw new RuntimeException("SQLException:" + ex.getMessage());
        }

    }
    public void beginTransaction() {
        try {
            conn.getCon().setAutoCommit(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.info("Cannot begin transaction: ");
        }
    }

    /**
     * method for commit transaction in manual transaction management invoked from
     * AOP class after all database update is done
     */
    public void commitTransaction() {

        try {
            conn.getCon().commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.info("Cannot commit transaction: " + e.getCause().toString());
        }
    }


    public void rollBackTransaction() {

        try {
            conn.getCon().rollback();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.info("Cannot rollback transaction: " + e.getCause().toString());
        }
    }
}