package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 롬복으로 여러가지 귀찮은 메서드 자동 생성
// 어노테이션 프로세싱으로 생성
@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("lombok");

        String name = helloLombok.getName();
        System.out.println(name);
        System.out.println(helloLombok.toString());
    }
}
