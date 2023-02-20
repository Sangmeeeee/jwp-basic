package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteQuestionController extends AbstractController {
    private QuestionService service = QuestionService.getInstance();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!UserSessionUtils.isLogined(request.getSession()))
            return jsonView().addObject("result", Result.fail("fail"));
        long questionId = Long.parseLong(request.getParameter("questionId"));
        try {
            service.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jsonView().addObject("result", Result.ok());
        }catch (IllegalStateException e){
            return jsonView().addObject("result", Result.fail("fail"));
        }
    }
}
