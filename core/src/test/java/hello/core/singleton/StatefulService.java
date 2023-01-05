package hello.core.singleton;

// stateful하게 설계하는 경우, 싱글톤에서 장애 발생 가능성 높음
public class StatefulService {
    // 상태를 유지하는(stateful) 필드
    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        this.price = price; // 문제 지점
    }

    public int getPrice() {
        return price;
    }
}
