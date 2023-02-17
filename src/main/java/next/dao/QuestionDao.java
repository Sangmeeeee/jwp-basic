package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.util.List;

public class QuestionDao {
    public void insert(Question question){
        // ToDo
    }

    public List<Question> findAll(){
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS";
        return template.query(sql, rs -> {
            return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                    null, rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
        });
    }

    public Question findById(Long questionId){
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionID = ?";
        return template.queryForObject(sql,  rs -> {
            return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                    rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
        }, questionId);
    }
}
