package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", (pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        }));
    }

    public void update(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        template.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid = ?", (pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        }));
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        return template.query("SELECT userId, password, name, email FROM USERS", pstmt -> {}, rs -> {
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
        });
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        return template.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", pstmt -> {
            pstmt.setString(1, userId);
        }, rs -> {
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
        });
    }
}
