package com.tom.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
public class Tester {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("ABC");
        list.add("DDD");
        list.add("EEE");
        System.out.println(list.get(0));
        System.out.println(list.size());
        Map<String, String> stocks = new HashMap<>();
        stocks.put("2330", "台積電");
        stocks.put("2474", "華亞科");
        System.out.println(stocks.get("2330"));
    }
}

