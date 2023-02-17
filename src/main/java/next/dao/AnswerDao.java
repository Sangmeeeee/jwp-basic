package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.util.List;

public class AnswerDao {

    public Answer findByAnswerId(Long answerId){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
        return jdbcTemplate.queryForObject(sql, rs -> {
            return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getTimestamp("createdDate"), rs.getLong("questionId"));
        }, answerId);
    }

    public List<Answer> findAllByQuestionId(Long questionId){
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ?";
        return template.query(sql, rs -> {
            return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getTimestamp("createdDate"), questionId);
        }, questionId);
    }
}
