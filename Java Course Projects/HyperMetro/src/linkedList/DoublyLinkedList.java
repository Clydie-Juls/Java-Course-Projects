package metro.linkedList;

import java.util.function.Consumer;

public class DoublyLinkedList <T> {
    private Node head, tail; // Head and Tail of the linked List.
    private int size;

    public DoublyLinkedList() {
        size = 0;
    }

    // Node which represents an element of the linked list. It has a value and a reference to the next node.
    public class Node {
        private T value;
        private Node previous;
        private Node next;

        public Node(T value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    // Insert value at the last index.
    public void insertToLast(T value) {
        if (head == null) {
            head = new Node(value, null, null);
            tail = head;
        } else {
            Node newNode = new Node(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    // Insert value at the first index.
    public void insertToFirst(T value) {
        if (head == null) {
            head = new Node(value, null, null);
            tail = head;
        } else {
            head = new Node(value, null, head);
            head.getNext().setPrevious(head);
        }

        size++;
    }

    public void insertByIndex(int index, T value) {
        if (head == null) {
            head = new Node(value, null,  null);
            tail = head;
            size++;
        } else {
            if (index == 0) {
                insertToFirst(value);
            } else if (index == size) {
                insertToLast(value);
            } else {
                Node previousNode = head;
                Node currentNode = head;
                for (int i = 0; i < index; i++) {
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }

                Node newNode = new Node(value, previousNode, currentNode);
                previousNode.next = newNode;
                currentNode.previous = newNode;
                size++;
            }
        }
    }

    public void forEach(Consumer<T> method){
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            method.accept(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    public T getValue(int index) {
        Node currentNode = head;
        for (int i = 0; i < index ; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    public void remove(T value) {
        Node currentNode = head;
        for (int i = 0; i < size ; i++) {
            if(currentNode.value == value) {
                currentNode.next.previous = currentNode.previous;
                currentNode.previous.next = currentNode.next;
                break;
            }
            currentNode = currentNode.next;
        }
    }

    public int getIndex(T value) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == value) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    public Node getNode(T value) {
        Node currentNode = head;
        for (int i = 0; i < size ; i++) {
            if (currentNode.value == value) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }


    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
