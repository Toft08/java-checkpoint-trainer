public class SingleLinkedList implements LinkedList {
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
        if (index < 0 || index >= size || head == null) return -1;

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = next(current); // move forward
        }
        return current.value;
    }

    @Override
    public void add(int value) {
        // Implementation for adding an element at the end of the list
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = next(current); // move through list
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        // Implementation for removing an element by its index
        if (index < 0 || index >= size || head == null) return;

        if (index == 0) {
            head = head.next;
            size--;
            return;
        } 

        Node previous = head;
        Node current = head;
        for (int i = 0; i < index; i++) {
            previous = current;
            current = next(current);
        }
        previous.next = current.next; // unlink the node
        size--;
    }

    @Override
    public int size() {
        // Implementation for accessing an element by its index
        return size;
    }

    private Node next(Node node) {
        System.out.print("Go to next node\n");
        return node.next;
    }
}