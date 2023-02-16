package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql, PreparedStatementSetter setter) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){
            setter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }
    public void update(String sql, Object... objects){
        this.update(sql, pstmt -> {
            for(int i = 0; i < objects.length; i++)
                pstmt.setObject(i, objects[i]);
        });
    }
    public <T> List<T> query(String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        try(Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
            setter.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    result.add(mapper.mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... objects){
        return this.query(sql, pstmt -> {
            for(int i = 0; i < objects.length; i++)
                pstmt.setObject(i, objects[i]);
        } , mapper);
    }
    public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> mapper){
        return query(sql, setter, mapper).get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> mapper, Object... objects){
        return this.query(sql, mapper, objects).get(0);
    }
}
