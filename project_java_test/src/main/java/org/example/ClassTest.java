package org.example;

public class ClassTest{
    String str = new String("hello");
    char[] ch = {'a','b','c'};
    public void fun(String str, char ch[]){
        str="world";
        // ch[0]='d';
        // ch = new char[2];
    }
    public static void main(String[] args) {
        ClassTest test1 = new ClassTest();// 初始化ClassTest，此时str=“hello”,ch不变
        test1.fun(test1.str,test1.ch);// 调用方法时，test1.str="hello",test1.ch={a,b,c}
        // 方法执行结束后，str不变,fun方法内部的变了。
        System.out.print(test1.str + " and ");
        System.out.print(test1.ch);
    }
}
