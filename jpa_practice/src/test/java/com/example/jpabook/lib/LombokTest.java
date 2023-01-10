package com.example.jpabook.lib;

import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
@Setter
public class LombokTest {
    private int testField1;
    private int testField2;

    @Test
    @DisplayName("롬복 Getter, Setter 테스트")
    void lombokWorkTest() {
        // given
        int test1 = 1, test2 = 2;

        // when
        setTestField1(2);
        setTestField2(1);

        // then
        test1 = getTestField1();
        test2 = getTestField2();

        assertThat(test1).isEqualTo(2);
        assertThat(test2).isEqualTo(1);
    }
}
