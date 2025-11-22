public class CircularLinkedList implements LinkedList {
    private Node head;
    private int size;

    private class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public int at(int index) {
        // If the list is empty or index invalid
        if (index < 0 || head == null) {
            return -1;
        }

        // Start from the head node
        Node current = head;

        // Move forward 'index' times using our next() helper
        // Note: The list is circular, so it wraps around if index > size
        for (int i = 0; i < index; i++) {
            current = next(current);
        }

        return current.value;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);

        // Case 1: If list is empty, new node becomes head and points to itself
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            // Case 2: Traverse to last node (whose next points to head)
            Node current = head;
            while (current.next != head) {
                current = next(current);
            }

            // Insert the new node after the last node
            current.next = newNode;
            newNode.next = head; // close the circle
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (head == null || index < 0) {
            return;
        }

        // Case 1: Remove head (index == 0)
        if (index == 0) {
            // If only one node → clear list
            if (size == 1) {
                head = null;
            } else {
                // Otherwise, find the tail node (the one that points to head)
                Node tail = head;
                while (tail.next != head) {
                    tail = next(tail);
                }

                // Move head to the next node, and link tail back to new head
                head = head.next;
                tail.next = head;
            }
            size--;
            if (size == 0) head = null;
            return;
        }

        // Case 2: Remove any non-head node
        Node prev = head;
        Node curr = head;

        // Move 'index' times to find the target node
        for (int i = 0; i < index; i++) {
            prev = curr;
            curr = next(curr);
        }

        // Bypass the target node
        prev.next = curr.next;

        // If we removed what used to be head after wrapping
        if (curr == head) {
            head = curr.next;
        }

        size--;
        if (size == 0) head = null;
    }

    @Override
    public int size() {
        return size;
    }

    private Node next(Node node) {
        System.out.print("Go to next node\n");
        return node.next;
    }
}
