public class DoubleLinkedList implements LinkedList {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        int value;
        Node next;
        Node prev;
        
        Node(int value) {
            this.value = value;
        }
    }

    @Override
    public int at(int index) {
        // Implementation for accessing an element by its index
        if (index < 0 || index >= size || head == null) return -1;

        Node current;
        
        if (index <= (size -1 )/2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = next(current);
            }
        } else {
            current = tail;
            for (int i = size -1; i > index; i--) {
                current = prev(current);
            }
        }
        return current.value;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next =newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size || head == null) return;

        if (index == 0) {
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
            size--;
            return;
        }

        if (index == size -1) {
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null;
            size--;
            return;
        }

        Node current;
        if (index <= (size - 1) / 2) {
            current = head;
        for (int i = 0; i < index; i++) {
            current = next(current);
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = prev(current);
            }
        }

        size--;
    }

    @Override
    public int size() {
        return size;
    }

    private Node next(Node node) {
        System.out.print("Go to next node\n");
        return node.next;
    }

    private Node prev(Node node) {
        System.out.print("Go to previous node\n");
        return node.prev;
    }
}