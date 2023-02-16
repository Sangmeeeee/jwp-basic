package core.web.servlet;

import core.web.RequestMapping;
import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        Controller controller = rm.getController(req.getRequestURI());
        try {
            String viewName = controller.execute(req, resp);

            if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
                resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
                return;
            }
            RequestDispatcher rd = req.getRequestDispatcher(viewName);
            rd.forward(req, resp);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
