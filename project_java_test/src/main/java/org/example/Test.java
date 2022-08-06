package org.example;

import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class Test {
    public static void main( String[] args ) {
        List list = new ArrayList<>();
        list.add("A");
        list.add("b");
        list.add("A");
        Set set = new HashSet();
        set.add("A");
        set.add("b");
        set.add("A");
        System.out.println(list.size() + "," + set.size());
        Test test = new Test();
        System.out.println(test instanceof Test);
    }
}
