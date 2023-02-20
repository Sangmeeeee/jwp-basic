package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(UserSessionUtils.isLogined(request.getSession())) {
            String writer = UserSessionUtils.getUserFromSession(request.getSession()).getName();
            Question question = new Question(writer, request.getParameter("title"), request.getParameter("contents"));
            questionDao.insert(question);
        }
        return jspView("redirect:/");
    }
}
