package com.company;

public class SinglyLinkedList<T> {
    private int size = 0;
    private Link<T> lastLink = new Link<>(null, null);
    private Link<T> firstLink = new Link<>(null, null);

    public int size() {
        return size;
    }

    public void add(T element) {
        if (size == 0) {
            lastLink.element = element;
            firstLink.element = element;
            size++;
        } else if (size == 1) {
            lastLink.element = element;
            firstLink.next = lastLink;
            size++;
        } else {
            addLast(element);
        }
    }

    public void add(int index, T element) {
        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            getLink(index - 1).next = new Link<>(element, getLink(index));
            size++;
        }
    }

    public void addFirst(T element) {
        firstLink = new Link<>(element, firstLink);
        size++;
    }

    public void addLast(T element) {
        Link<T> newLink = new Link<>(element, null);
        lastLink.next = newLink;
        lastLink = newLink;
        size++;
    }

    public void remove(int index) {
        if (index == 0) {
            removeFirst();
        } else if (index == size - 1) {
            removeLast();
        } else {
            getLink(index - 1).next = getLink(index + 1);
            size--;
        }
    }

    public void remove(T element) {
        remove(getIndex(element));
    }

    public void removeFirst() {
        firstLink = firstLink.next;
        size--;
    }

    public void removeLast() {
        lastLink = getLink(size - 2);
        lastLink.next = null;
        size--;
    }

    public T get(int index) {
        return getLink(index).element;
    }

    public int getIndex(T element) {
        Link<T> link = firstLink;
        for (int i = 0; i < size; i++) {
            if (link.element.equals(element)) {
                return i;
            }
            link = link.next;
        }
        return -1;
    }

    public void flipListItems() {
        int countOfIteration = (size % 2 == 0) ? size / 2 : (size - 1) / 2;
        flipListItems(countOfIteration, 0);
    }

    private Link<T> getLink(int index) {
        if (index >= 0 && index < size) {
            Link<T> requiredElement = firstLink;
            int i = 0;
            while (i < index) {
                requiredElement = requiredElement.next;
                i++;
            }
            return requiredElement;
        } else {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    private void flipListItems(int countOfIteration, int iteration) {
        Link<T> beforeRoamingItem = firstLink;
        Link<T> beforeReplaceableItem = firstLink;
        for (int i = 0; i < size - 2 - iteration; i++) {
            beforeReplaceableItem = beforeReplaceableItem.next;
            if (i < iteration - 1) {
                beforeRoamingItem = beforeRoamingItem.next;
            }
        }
        Link<T> roamingItem = (iteration == 0) ? firstLink : beforeRoamingItem.next;
        Link<T> replaceableItem = beforeReplaceableItem.next;
        Link<T> nextRoamingItem = roamingItem.next;
        Link<T> nextReplaceableItem = replaceableItem.next;
        replaceableItem.next = nextRoamingItem;
        roamingItem.next = nextReplaceableItem;
        beforeReplaceableItem.next = roamingItem;
        if (iteration != 0) {
            beforeRoamingItem.next = replaceableItem;
        } else {
            firstLink = replaceableItem;
        }
        if (iteration < countOfIteration && iteration + 2 != countOfIteration) {
            flipListItems(countOfIteration, ++iteration);
        } else {
            if (countOfIteration * 2 == size) {
                replaceableItem.next = nextRoamingItem.next;
                beforeReplaceableItem.next = nextRoamingItem;
                nextRoamingItem.next = roamingItem;
            } else if (iteration < countOfIteration) {
                flipListItems(countOfIteration, ++iteration);
            }
        }
    }

    private static class Link<T> {
        T element;
        Link<T> next;

        Link(T element, Link<T> next) {
            this.element = element;
            this.next = next;
        }
    }
}
