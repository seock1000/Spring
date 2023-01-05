package hello.core.singleton;

// 싱글톤 패턴
public class SingletonService {

    // static 영역에 자기 자신 객체 생성해서 해당 객체를 공유하도록
    private static final SingletonService instance = new SingletonService();

    // 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자에 private?
    // 싱글톤 패턴이므로 외부에서 객체 생성하는 것을 방지
    private SingletonService() { }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    // 외에도 여러가지 싱글톤 패턴 구현 방법 존재
    // 해당 방법은 가장 단순하고 안전한 방법

}
