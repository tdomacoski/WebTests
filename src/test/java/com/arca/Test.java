package com.arca;

public class Test  {

    public static void main(String[] args) {

        B b = new C();
        b.quem();
    }

    static class C implements B{

        @Override
        public String quem() {
            return "C";
        }
    }
    static class A implements B{
        @Override
        public String quem() {
            return "Sou A";
        }
    }
    static interface B{
        public String quem();
    }
}
