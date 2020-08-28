package top.qiuk.algorithm;

public class 两数相加 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {


        ListNode listNode = null;
        ListNode listNodeTemp1 = null;
        int curry = 0;

        while (l1.val != 0 || l2.val != 0) {
            int i = l1.val + l2.val +curry;
            if (i >= 10) {
                i -= 10;
                curry = 1;
            } else {
                curry = 0;
            }
            ListNode listNodeTemp = new ListNode(i);

            if (listNode == null) {
                listNode = listNodeTemp;
                listNodeTemp1 = listNodeTemp;
            } else {
                listNodeTemp1.next = listNodeTemp;
                listNodeTemp1 = listNodeTemp;
            }


            l1 = l1.next == null ? new ListNode(0) : l1.next;
            l2 = l2.next == null ? new ListNode(0) : l2.next;
        }

        if (curry == 1) {
            listNodeTemp1.next = new ListNode(1);
        }

        return listNode;

    }


    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}


