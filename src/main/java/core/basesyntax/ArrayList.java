package core.basesyntax;

import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
    static final int DEFAULT_CAPACITY = 10;
    static final float INCREASE_CAPACITY_COEFFICIENT = 1.5f;
    private Object[] elementData;
    private int size;

    public ArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        checkCapacity(1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Object[] newElements = new Object[list.size()];
        checkCapacity(list.size());
        for (int i = 0; i < newElements.length; i++) {
            newElements[i] = list.get(i);
        }
        System.arraycopy(newElements, 0, elementData, size, newElements.length);
        size += newElements.length;
    }

    @Override
    public T get(int index) {
        indexCheck(index + 1);
        return (T) elementData[index];
    }

    @Override
    public void set(T value, int index) {
        indexCheck(index + 1);
        elementData[index] = value;
    }

    @Override
    public T remove(int index) {
        indexCheck(index + 1);
        T element = (T) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        size--;
        return element;
    }

    @Override
    public T remove(T element) {
        int index = getIndexByValue(element);
        return remove(index);
    }

    private int getIndexByValue(T value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, elementData[i])) {
                return i;
            }
        }
        throw new NoSuchElementException("No such element");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexCheck(int index) {
        if (index < 0 || index > elementData.length) {
            throw new ArrayListIndexOutOfBoundsException("Index is out of bounds");
        }
        if (index > size) {
            throw new ArrayListIndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkCapacity(int numbOfNewElements) {
        if (size + numbOfNewElements > elementData.length) {
            growCapacity();
        }
    }

    private void growCapacity() {
        int newCapacity = (int) (elementData.length * INCREASE_CAPACITY_COEFFICIENT);
        Object[] newElementData = new Object[newCapacity];
        System.arraycopy(elementData, 0, newElementData, 0, size);
        elementData = newElementData;
    }
}
