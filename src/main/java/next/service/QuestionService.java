package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;

public class QuestionService {
    private static QuestionService instance;
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();

    private QuestionService(){}

    public static QuestionService getInstance(){
        if (instance == null)
            instance = new QuestionService();
        return instance;
    }

    public void deleteQuestion(long questionId, User user){
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if(!question.getWriter().equals(user.getName()))
            throw new IllegalStateException();
        for(Answer answer : answers){
            if(!answer.getWriter().equals(user.getName()))
                throw new IllegalStateException();
        }

        answerDao.deleteAllByQuestionId(questionId);
        questionDao.delete(questionId);
    }
}
