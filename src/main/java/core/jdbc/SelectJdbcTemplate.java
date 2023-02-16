package core.jdbc;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {
    public List query(String sql) throws SQLException {
        List result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public Object queryForObject(String sql) throws SQLException{
        List list = query(sql);
        return list.get(0);
    }

    abstract void setValues(PreparedStatement pstmt) throws SQLException;
    abstract Object mapRow(ResultSet rs) throws SQLException;
}
