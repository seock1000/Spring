package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    /**
    // Provider로 실제 http 요청 시까지 지연
    private final MyLogger myLogger;
     **/

    /**
    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void logic(String id) {
        MyLogger myLogger = myLoggerProvider.getObject(); // service에서도 요청에 대해 생성된 bean을 바라봄
        myLogger.log("service id = "+ id);
    }
     **/

    // 프록시 사용
    private final MyLogger myLogger;

    public void logic(String id) {
        myLogger.log("service id = "+ id);
    }
}
