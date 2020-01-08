package com.hu;
import javax.swing.*;
import	java.util.List;
//两个链表数相加
public class leetCode2 {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode(0);
        int sum = 0;
        ListNode cur = dummy;
        ListNode p1 = l1,p2=l2;
        while(p1 != null || p2 != null){
            if(p1 != null){
                sum+=p1.val;
                p1 = p1.next;
            }
            if( p2 !=null){
                sum+=p2.val;
                p2 = p2.next;
            }
            cur.next = new ListNode(sum%10);
            sum /=10;
            cur = cur.next;
        }
        if(sum == 1){
            cur.next = new ListNode(1);
        }
        return dummy.next;
    }


    public static void main(String[] args) {
        ListNode l1 = new ListNode(3);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(7);
        ListNode listNode = addTwoNumbers(l1, l2);
        while (listNode != null){
            System.out.print(listNode.val+"-->");
            listNode = listNode.next;
        }
    }
}
