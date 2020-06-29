package top.qiuk.util;

public class IPv4 implements Comparable {
    protected IPv4() {
    }

    protected int address;

    /**
     * 是否是IPv4的地址
     *
     * @param ip
     * @return
     */
    public static boolean isIPv4(String ip) {
//        if (null == ip) {
//            return false;
//        }
//        String matche = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\{1\\}";
//        return ip.matches(matche);
        return true;
    }

    /**
     * 构造函数
     *
     * @param ip ip地址
     */
    public IPv4(String ip) {
        if (isIPv4(ip)) {
            String[] addr = ip.split("\\.");
            if (addr.length == 4) {
                address = Integer.parseInt(addr[3]) & 0xFF;
                address |= ((Integer.parseInt(addr[2]) << 8) & 0xFF00);
                address |= ((Integer.parseInt(addr[1]) << 16) & 0xFF0000);
                address |= ((Integer.parseInt(addr[0]) << 24) & 0xFF000000);
            }
        }
    }

    /**
     * 构造函数
     *
     */
    public IPv4(byte[] addr) {
        if (addr.length == 4) {
            address = addr[3] & 0xFF;
            address |= ((addr[2] << 8) & 0xFF00);
            address |= ((addr[1] << 16) & 0xFF0000);
            address |= ((addr[0] << 24) & 0xFF000000);
        }
    }

    /**
     * 将long型ip地址值转换为ip地址
     *
     * @param ipValue
     * @return
     */
    public IPv4(long ipValue) {
        this.address = (int) ipValue;
    }

    /**
     * 将IP地址转换成long型
     *
     * @return
     */
    public long longValue() {
        return address & 0xFFFFFFFFL;
    }

    /**
     * 类函数将IP地址转换成long型
     *
     * @param ip
     * @return
     */
    public static long longValue(String ip) {
        return new IPv4(ip).longValue();
    }

    /**
     * 判断两个ip地址是否相等
     *
     * @return
     */
    public boolean equals(Object obj) {
        return (obj != null) && (obj instanceof IPv4) && (address == ((IPv4) obj).address);
    }

    /**
     * 比较两个ip地址
     *
     * @param object
     * @return
     */
    public int compareTo(Object object) {
        return compareTo((IPv4) object);
    }

    public byte[] getAddress() {
        byte[] addr = new byte[4];
        addr[0] = (byte) ((address >>> 24) & 0xFF);
        addr[1] = (byte) ((address >>> 16) & 0xFF);
        addr[2] = (byte) ((address >>> 8) & 0xFF);
        addr[3] = (byte) (address & 0xFF);
        return addr;
    }

    /**
     * 比较两个ip地址
     *
     * @param ipv4
     * @return
     */
    public int compareTo(IPv4 ipv4) {
        return (longValue() < ipv4.longValue() ? -1 : (longValue() == ipv4.longValue() ? 0 : 1));
    }

    /**
     * 转换成字符串
     *
     * @return
     */
    public String toString() {
        byte[] addr = getAddress();
        return (addr[0] & 0xFF) + "." + (addr[1] & 0xFF) + "." + (addr[2] & 0xFF) + "." + (addr[3] & 0xFF);
    }

    public static String toString(long ip) {
        return new IPv4(ip).toString();
    }

    public static IPv4 getStartIp(IPv4 ip, IPv4 mask) {
        return new IPv4(getStartIp(ip.address, mask.address));
    }

    public static IPv4 getEndIp(IPv4 ip, IPv4 mask) {
        return new IPv4(getEndIp(ip.address, mask.address));
    }

    public static long getStartIp(long ip, long mask) {
        return (ip & mask) & 0xFFFFFFFFL;
    }

    public static long getEndIp(long ip, long mask) {
        return (ip | ~mask) & 0xFFFFFFFFL;
    }

    public static long[] getIpSec(String ip, String mask) {
        long[] ipSec = new long[2];
        long ipValue = new IPv4(ip).longValue();
        long maskValue = new IPv4(mask).longValue();
        ipSec[0] = getStartIp(ipValue, maskValue);
        ipSec[1] = getEndIp(ipValue, maskValue);
        return ipSec;
    }

    public static void main(String[] args) {
        IPv4 ip = new IPv4("192.168.0.1");
        IPv4 mask = new IPv4("255.255.255.240");
//        System.out.println(getStartIp(ip, mask));
//        System.out.println(getEndIp(ip, mask));

        System.out.println(ip.longValue());
    }
}