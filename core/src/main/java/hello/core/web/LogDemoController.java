package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    /**
    // myLogger는 scope가 request -> http 요청부터 응답까지만 생존주기
    // 따라서, 이런식으로 빈으로 잡으면 스프링 올릴때에 오류 발생(생존주기도 아닌데 달라고 하니깐)
    // 실제 http 요청이 왔을 때까지 지연시켜야 함
    private final MyLogger myLogger;
     **/

    /**
    // Provider로 http 요청 시까지 지연
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    // HttpServletRequest : 자바에서 제공하는 표준 Servlet 규약에 의한 http request 정보 받을 수 있음
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestUrl = request.getRequestURL().toString();
        // 이런 부분은 스프링 인터셉터나 서블릿 필터 같은 곳을 활용하는 것이 좋음
        // 인터셉터 : http 요청에 대해 컨트롤러 호출 직전에 인터셉트해서 공통적으로 처리할 수 있도록 하는 것
        MyLogger myLogger = myLoggerProvider.getObject(); // myLogger 빈 최초 생성 시점
        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller test");
        Thread.sleep(1000); // 빈이 요청에 따라 각각 관리되는 것 확인
        logDemoService.logic("testId");

        return "OK";
    }
    **/

    // 프록시 사용
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestUrl = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller test");
        Thread.sleep(1000); // 빈이 요청에 따라 각각 관리되는 것 확인
        logDemoService.logic("testId");

        return "OK";
    }
}
