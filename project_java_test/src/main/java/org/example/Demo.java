package org.example;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        /*Demo demo = new Demo();
        demo.test();*/
        Collection<?>[] collections =
                {new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values()};
        Super subToSuper = new Sub();
        for(Collection<?> collection: collections) {
            System.out.println(subToSuper.getType(collection));
        }
    }
    public void test() {
        Collection<?>[] collections =
                {new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values()};
        Super subToSuper = new Sub();
        for(Collection<?> collection: collections) {
            System.out.println(subToSuper.getType(collection));
        }
    }
    abstract static class Super {
        public static String getType(Collection<?> collection) {
            return "“Super:collection” " + collection.getClass().getSimpleName();
        }
        public  String getType(List<?> list) {
            return "“Super:list”";
        }
        public String getType(ArrayList<?> list) {
            return "“Super:arrayList”";
        }
        public  String getType(Set<?> set) {
            return "“Super:set”";
        }
        public String getType(HashSet<?> set) {
            return "“Super:hashSet”";
        }
    }
     static class Sub extends Super {
        public Sub() {
            
        }
        public  static String getType(Collection<?> collection) {
            return "Sub " + collection.getClass().getSimpleName(); }
    }
}
