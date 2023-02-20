package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!UserSessionUtils.isLogined(request.getSession()))
            return jspView("redirect:/");
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        if(question.getWriter().equals(UserSessionUtils.getUserFromSession(request.getSession()).getName())){
            return jspView("/qna/update.jsp").addObject("question", question);
        }
        return jspView("redirect:/");
    }
}
