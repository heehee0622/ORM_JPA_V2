package com.example.syshology.jpa;

import com.example.syshology.jpa.entity.Member;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-04
 * Time: 오후 4:24
 * Project : IntelliJ IDEA
 */
public class LamdaTest {



    @Test
    public void testEachString(){
        List<Member> arraysList = Arrays.asList(Member.builder().name("TEST1").build(),Member.builder().name("TEST2").build());
        arraysList.stream().forEach(System.out::println);
        arraysList.stream().forEach(s -> System.out.println(s.getName() + s.getId()) );

    }
    @Test
    public void testConversion(){
        List<Integer> integers = Arrays.asList(5, 12, 3);
        Collections.sort(integers, (o1, o2) -> o1 > o2 ? 1 :0 );
         integers.stream().forEach(integer -> System.out.println(integer.toString()));
        Collections.sort(integers, Integer::compareTo);
        integers.stream().forEach(System.out::println);
    }

    @Test
    public void testUppercase(){
        Function<String, String> uppercase = v -> v.toUpperCase();
    }

}
