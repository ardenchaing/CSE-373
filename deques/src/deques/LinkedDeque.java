package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.
    Node<T> sentinelFront;
    Node<T> sentinelBack;
    Node<T> temporary;

    public LinkedDeque() {
        size = 0;
        sentinelFront = new Node<>(null);
        sentinelBack = new Node<>(null);
        temporary = new Node<>(null);
        sentinelFront.next = sentinelBack;
        sentinelBack.prev = sentinelFront;
        front = sentinelFront;
        back = sentinelBack;
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> addedFirst = new Node<>(item);
        if (sentinelFront.next == sentinelBack) {
            addedFirst.prev = sentinelFront;
            addedFirst.next = sentinelBack;
            sentinelFront.next = addedFirst;
            sentinelBack.prev = addedFirst;
        } else {
            temporary = sentinelFront.next;
            sentinelFront.next = addedFirst;
            addedFirst.next = temporary;
            addedFirst.next.prev = addedFirst;
            addedFirst.prev = sentinelFront;
        }

    }

    public void addLast(T item) {
        size += 1;
        Node<T> addedLast = new Node<>(item);
        if (sentinelFront.next == sentinelBack) {
            addedLast.prev = sentinelFront;
            addedLast.next = sentinelBack;
            sentinelFront.next = addedLast;
            sentinelBack.prev = addedLast;
        } else {
            temporary = sentinelBack.prev;
            sentinelBack.prev = addedLast;
            addedLast.next = sentinelBack;
            addedLast.prev = temporary;
            temporary.next = addedLast;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toReturn = front.next.value;
        if (size == 1) {
            sentinelFront.next.prev = null;
            sentinelFront.next.next = null;
            sentinelFront.next = sentinelBack;
            sentinelBack.prev = sentinelFront;
        } else {
            sentinelFront.next.prev = null;
            sentinelFront.next = sentinelFront.next.next;
            sentinelFront.next.prev.next = null;
            sentinelFront.next.prev = sentinelFront;
        }
        size -= 1;
        return toReturn;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toReturn = back.prev.value;
        if (size == 1) {
            sentinelBack.prev.next = null;
            sentinelBack.prev.prev = null;
            sentinelBack.prev = sentinelFront;
            sentinelFront.next = sentinelBack;
        } else {
            sentinelBack.prev.next = null;
            sentinelBack.prev = sentinelBack.prev.prev;
            sentinelBack.prev.next.prev = null;
            sentinelBack.prev.next = sentinelBack;
        }
        size -= 1;
        return toReturn;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        T toReturn;
        Node<T> pointer;
        if (index < size / 2) {
            pointer = sentinelFront.next;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
        } else {
            pointer = sentinelBack.prev;
            for (int j = size - 1; j > index; j--) {
                pointer = pointer.prev;
            }
        }
        toReturn = pointer.value;
        return toReturn;
    }

    public int size() {
        return size;
    }
}
