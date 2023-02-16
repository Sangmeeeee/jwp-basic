package core.jdbc;

import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdateJdbcTemplate {
    private final static Logger log = LoggerFactory.getLogger(UpdateJdbcTemplate.class);

    public void update(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            pstmt = con.prepareStatement(sql);
            setValueForUpdate(user, pstmt);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    abstract String createQueryForUpdate();

    private void setValueForUpdate(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
    }
}
