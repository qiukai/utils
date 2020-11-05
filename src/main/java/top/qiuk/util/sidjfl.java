package top.qiuk.util;

public class sidjfl {



    private String createBeanNameByServiceName(String serviceName) {
        StringBuilder sb = new StringBuilder();
        sb.append("zhsd");
        serviceName = serviceName.toLowerCase();
        char c2 = serviceName.charAt(0);
        char start = (char)( ((int)c2) - 32);
        String s2 = new String(new char[]{start});
        String substring = serviceName.substring(1);
        serviceName = s2+substring;
        while (serviceName.contains("_")) {
            int i = serviceName.indexOf("_");
            char c = serviceName.charAt(i + 1);
            char k = (char) (((int) c) - 32);
            char c1 = serviceName.charAt(i + 1);
            char[] ch = new char[2];
            ch[0] = '_';
            ch[1] = c1;
            String s = new String(ch);
            String s1 = new String(new char[]{k});
            serviceName = serviceName.replaceAll(s, s1);
        }
        sb.append(serviceName);
        return sb.toString();
    }

    public static void main(String[] args) {



//        String s = "asdfsdf".toUpperCase();
//        System.out.println(s);


        Integer integer = 1;
        Integer integer1 = 1;
        System.out.println(integer==integer1);

//        int i1 = (i ^ (i >>> 16)) ;
//        System.out.println(i1);

    }

}
