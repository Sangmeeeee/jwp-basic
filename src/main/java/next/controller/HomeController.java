package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.AbstractNewController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController extends AbstractNewController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
