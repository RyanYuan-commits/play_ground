package org.ryan;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Ryan Yuan
 * @Description
 * @Create 2025-01-13 12:17
 */
public class CopyPropertiesTests {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        A a = new A();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        a.setList(strings);
        a.setC(31231);
        B b = new B();
        BeanUtils.copyProperties(b, a);
        System.out.println(b.getList());
        System.out.println(a.getList());
        System.out.println(b.getC());
    }

    @Data
    public static class A {
        private List<String> list;
        private int c;
    }

    @Data
    public static class B {
        private String list;
        private int c;
    }
}
