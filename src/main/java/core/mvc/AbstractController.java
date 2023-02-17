package core.mvc;

public abstract class AbstractController implements Controller{
    protected ModelAndVIew jspView(String forwardUrl){
        return new ModelAndVIew(new JspView(forwardUrl));
    }

    protected ModelAndVIew jsonView(){
        return new ModelAndVIew(new JsonView());
    }
}
