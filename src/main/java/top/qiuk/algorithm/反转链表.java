package top.qiuk.algorithm;


public class 反转链表 {

    public static void main(String[] args) throws Exception {


        NodeList nodeList1 = new NodeList("1", null);
        NodeList nodeList2 = new NodeList("2", nodeList1);
        NodeList nodeList3 = new NodeList("3", nodeList2);
        NodeList nodeList4 = new NodeList("4", nodeList3);
        NodeList nodeList5 = new NodeList("5", nodeList4);

        反转链表 dome = new 反转链表();
        dome.print(nodeList5);

        NodeList res = dome.res(nodeList5, nodeList5.next, true);
        dome.print(res);

    }


    public void print(NodeList nodeList) {

        NodeList temp = nodeList;
        while (temp != null) {
            System.out.print(temp.val + " > ");
            temp = temp.next;
        }

        System.out.println();
    }


    public NodeList res(NodeList nodeList, NodeList next, boolean isFirst) {

        NodeList temp;
        if (next == null) {
            return nodeList;
        } else {
            if (isFirst) {
                nodeList.next = null;
            }
            temp = next.next;
            next.next = nodeList;
        }


        NodeList res = res(next, temp, false);
        return res;
    }


}

class NodeList {

    String val;

    NodeList next;

    NodeList(String val, NodeList next) {
        this.val = val;
        this.next = next;
    }

}