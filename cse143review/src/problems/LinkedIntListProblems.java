package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode temp = list.front;
        ListNode current = temp.next.next;
        current.next = temp.next;
        current.next.next = temp;
        current.next.next.next = null;
        list.front = current;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        ListNode current = list.front;
        if (current != null && current.next != null) {
            ListNode first = list.front;
            list.front = current.next;
            current = current.next;
            first.next = null;
            while (current.next != null) {
                current = current.next;
            }
            current.next = first;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        LinkedIntList toReturn = new LinkedIntList();
        if (a.front == null) {
            toReturn.front = b.front;
            return toReturn;
        }
        if (b.front == null) {
            toReturn.front = a.front;
            return toReturn;
        } else {
            ListNode temp = new ListNode(a.front.data);
            toReturn.front = temp;
            ListNode current = a.front;
            ListNode location = temp;
            while (current.next != null) {
                location.next = new ListNode(current.next.data);
                location = location.next;
                current = current.next;
            }
            location.next = b.front;
        }
        return toReturn;
    }
}
