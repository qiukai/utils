package top.qiuk;


import top.qiuk.constant.StringType;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) {


        String s = StringType.STRING + "123";
        System.out.println(s);

        TreeMap<String, String> stringStringHashMap = new TreeMap<>();


        stringStringHashMap.put("123","xcv");
        stringStringHashMap.put("qe","xcv");
        stringStringHashMap.put("df","xcv");
        stringStringHashMap.put("sdf","xcv");
        stringStringHashMap.put("aer","xcv");

        Set<Map.Entry<String, String>> entries = stringStringHashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey());
        }


    }
}