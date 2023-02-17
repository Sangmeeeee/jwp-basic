package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndVIew;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao = new AnswerDao();
    @Override
    public ModelAndVIew execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));

        answerDao.delete(answerId);
        return jsonView().addObject("message", Result.ok().getMessage());
    }
}
