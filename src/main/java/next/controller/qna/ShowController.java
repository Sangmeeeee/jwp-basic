package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.*;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    @Override
    public ModelAndVIew execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        return jspView("/qna/show.jsp").addObject("question", questionDao.findById(questionId)).addObject("answers", answerDao.findAllByQuestionId(questionId));
    }
}
