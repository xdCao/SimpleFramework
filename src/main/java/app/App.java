package app;

import annotations.Service;

/*
    created by xdCao on 2017/10/18
*/
@Service
public class App {



    public void hello(){
        System.out.println("hello");
    }

    @Override
    public String toString() {
        return "hahaha";
    }
}
