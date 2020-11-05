package top.qiuk.secure;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 对接消费贷  java Dome
 * <dependency>
 * <groupId>cn.hutool</groupId>
 * <artifactId>hutool-all</artifactId>
 * <version>5.4.2</version>
 * </dependency>
 */
public class Dome {

    private static String privateKey = "xxxxxxxxxxxxxxxxxxxx";
    private static String publicKey = "xxxxxxxxxxxxxxxxxxxxx";

    private static String clientId = "clientId";

    public static void main(String[] args) throws UnsupportedEncodingException {


//        // rsa 算法dome
//        rsa();

        // 对接消费代付请求报文 -----------------开始-----------------------

        /*
         *
         * 建议引入下面jar
         * <dependency>
         *    <groupId>cn.hutool</groupId>
         *    <artifactId>hutool-all</artifactId>
         *    <version>5.4.2</version>
         * </dependency>
         *
         * */


        // 构建 3要素信息对象
        PersonInfo personInfo = new PersonInfo();
        personInfo.setIdCard("410823199205180219");
        personInfo.setPhone("13141276334");
        personInfo.setUserName("张三");

        // 生成 3要素信息json 串，
        String personInfoData = JSONUtil.parse(personInfo).toString();

        // 打印需要传输的3要素信息
        System.out.println(personInfoData);

        // 创建rsa 算法对象
        RSA rsa = new RSA(privateKey, null);

        // 将3要素json 串进行加密
        byte[] encrypt = rsa.encrypt(personInfoData.getBytes("utf-8"), KeyType.PrivateKey);

        // 将密文进行base64 转码
        String data = Base64.encode(encrypt);

        // 创建 发送信息对象
        ConsumerParam consumerParam = new ConsumerParam();

        // 传入 客户id
        consumerParam.setClientId(clientId);

        // 传入 base64 后的密文
        consumerParam.setData(data);

        // 将传输对象 转出json串
        String request = JSONUtil.parse(consumerParam).toString();

        System.out.println(request);

        // 对接消费代付请求报文 -----------------结束-----------------------


        // 请求方式  https   head 需要设置  [{"key":"Content-Type","value":"application/json","description":""}]



    }

    /**
     * rsa 算法 dome
     * <p>
     * <p>
     * 需要引入 hutool-all.jar
     * 建议版本 5.4.2
     *
     *
     * <dependency>
     * <groupId>cn.hutool</groupId>
     * <artifactId>hutool-all</artifactId>
     * <version>5.4.2</version>
     * </dependency>
     */
    public static void rsa() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA", 2048);
        PrivateKey aPrivate = pair.getPrivate();

        byte[] encoded = aPrivate.getEncoded();
        privateKey = Base64.encode(encoded);

        System.out.println("----------privateKey-----start--------\n" + privateKey);
        System.out.println("----------privateKey-----end---------\n");
        PublicKey aPublic = pair.getPublic();
        publicKey = Base64.encode(aPublic.getEncoded());
        System.out.println("----------publicKey-----start--------\n" + publicKey);
        System.out.println("----------publicKey-----end---------\n");


        RSA rsa = new RSA(privateKey, publicKey);

        byte[] encrypt = rsa.encrypt("12345678".getBytes(), KeyType.PrivateKey);
        String encode = Base64.encode(encrypt);

        System.out.println("----------密文-----start--------\n" + encode);
        System.out.println("----------密文-----end---------\n");

        byte[] decrypt = rsa.decrypt(encode, KeyType.PublicKey);
        System.out.println("----------明文-----start--------\n" + new String(decrypt));
        System.out.println("----------明文-----end---------\n");
    }
}

class ConsumerParam {

    private String clientId;

    private String data;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

class PersonInfo {

    private String phone;

    private String idCard;

    private String userName;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

