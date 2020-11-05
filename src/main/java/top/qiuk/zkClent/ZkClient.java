package top.qiuk.zkClent;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZkClient {

    public CuratorFramework newClient;

    private ZkClient(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        newClient = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        newClient.start();
    }

    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient();




        String path = "/qiutest";


        zkClient.createNode(path,CreateMode.EPHEMERAL_SEQUENTIAL,"123");
//        zkClient.createNode(path,CreateMode.PERSISTENT,"123");
//        String data = zkClient.getData(path);
//        System.out.println(data);
//
//
//        zkClient.updateNodeDate(path,"1234");
//        String data1 = zkClient.getData(path);
//        System.out.println(data1);
//        zkClient.deleteNode(path);
//        System.out.println(zkClient.getData(path));

    }

    public void createNode(String path, CreateMode createMode, String data) {
        try {
            String s = newClient.create().withMode(createMode).forPath(path, data.getBytes());
            System.out.println(s);
        } catch (Exception e) {
            System.out.println("创建节点失败, elog=" + e.getMessage());
        }
    }

    public String getData(String path) {
        try {
            return new String(newClient.getData().forPath(path));
        } catch (Exception e) {
            System.out.println("获取数据失败, elog=" + e.getMessage());
        }
        return null;
    }

    public void updateNodeDate(String path, String data) {
        try {
            newClient.setData().forPath(path, data.getBytes());
        } catch (Exception e) {
            System.out.println("更新节点数据失败, elog=" + e.getMessage());
        }
    }

    public void deleteNode( String path) {
        try {
            newClient.delete().forPath(path);
        } catch (Exception e) {
            System.out.println("删除节点失败, elog=" + e.getMessage());
        }
    }

    public void getChildren( String path) {
        try {
            newClient.getChildren().forPath("/");
        } catch (Exception e) {
            System.out.println("获取自己节点失败, elog=" + e.getMessage());
        }
    }
}
