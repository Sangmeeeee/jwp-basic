package next.controller.user;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.AbstractNewController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController extends AbstractNewController {
    private UserDao userDao =  UserDao.getInstance();

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ModelAndView listUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/login")
    public ModelAndView loginUser(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = userDao.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/profile")
    public ModelAndView profileUser(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        return jspView("/user/profile.jsp").addObject("user", userDao.findByUserId(userId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/form")
    public ModelAndView userForm(HttpServletRequest request, HttpServletResponse response) {
        return jspView("/user/form.jsp");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/loginForm")
    public ModelAndView userLoginForm(HttpServletRequest request, HttpServletResponse response){
        return jspView("/user/login.jsp");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/logout")
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/create")
    public ModelAndView userCreate(HttpServletRequest request, HttpServletResponse response){
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        userDao.insert(user);
        return jspView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/updateForm")
    public ModelAndView userUpdateForm(HttpServletRequest request, HttpServletResponse response){
        User user = userDao.findByUserId(request.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        ModelAndView mav = jspView("/user/updateForm.jsp");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView userUpdate(HttpServletRequest request, HttpServletResponse response){
        User user = userDao.findByUserId(request.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));
        user.update(updateUser);
        return jspView("redirect:/");
    }
}
