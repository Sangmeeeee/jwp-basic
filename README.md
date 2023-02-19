#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* WebListener Annotation을 적용한 ContextLoadListener 클래스에서 contextInitialized 메소드는 톰캣 시작시 실행된다.
  * 톰캣은 WebServerLauncher 클래스의 메인메소드에서 실행시켜준다.
* contextInitialized 메소드 내부에서 ResourceDatabasePopulator 클래스를 통해 jwp.sql 스크립트를 실행시켜준다.
  * ConnectionManager를 통해 미리 DB에 DataSource를 설정해두고 getConnection을 통해 Connection을 얻는다.
* 톰캣이 실행되며 사용자로부터 Request를 받을때까지 대기한다.
  * 여기서 원래대로라면 Servlet은 사용자 요청을 최초로 받기 전까지 생성되지 않지만 loadOnStartup 속성값을 설정해주면 톰캣 실행시 사용자 요청과 관계없이 서블릿의 생성과 초기화를 진행한다.
* DispatcherServlet의 loadOnStartup 속성값이 1이기 때문에 초기화를 진행해준다.
  * HttpServlet을 상속받았기 때문에 생성 -> init -> servie -> destroy의 라이프 사이클을 따른다.
* DispatcherServlet 내부의 init 메소드를 실행하여 RequestMapping 객체를 생성하고 내부 url에 따른 Controller들을 생성하고 Mapping 해준다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
