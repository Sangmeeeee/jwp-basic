package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/");
        }
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question target = questionDao.findById(questionId);
        target.update(request.getParameter("title"), request.getParameter("contents"));
        questionDao.update(target);
        return jspView("redirect:/");
    }
}
