package com.ym.learn.test.other;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/24 20:27
 * @Desc:
 */
public class OptionalTest {
    @Test
    public void testValue(){
        Optional<String> value = Optional.empty();
        System.out.println("value: "+value);
        value.ifPresent(it->System.out.println(value));
        String newValue = Optional.ofNullable("fasfsf").orElse("哈哈哈哈");
        System.out.println("newValue: "+newValue);
        Assertions.assertEquals(value, newValue);

    }

    @Test
    public void testException(){
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                int i = 5 /(-1);
            }
        });
    }
}
