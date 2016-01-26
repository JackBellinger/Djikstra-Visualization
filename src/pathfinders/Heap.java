package pathfinders;

public class Heap {

    private Node[] data;
    private int last = 0;

    public Heap(int numElements) {//this constructor will make a heap with size that will fit at least that many elements, and have room to be complete ie the closest power of 2 that's greater than the number of elements + 1
        double log2 = Math.ceil(Math.log(numElements-1)/Math.log(2));//it uses numElements - 1 so that if the number is exactly a power of 2 + 1, the log should be that power so it doesn't go up an extra power just for that one element, which will fit in anyways
        int size = (int) Math.pow(2, log2) + 1;
        data = new Node[size];
    }
    public Heap() {
        data = new Node[65];
    }

    public void push(Node newNode) {
        if (last != data.length) {
            int current = last;
            data[current] = newNode;
            bubbleUp(current);
            last++;
        }
    }

    public Node pop() {
        Node top = data[0];
        if (last > 0) {
            data[0] = data[last - 1];
            data[last - 1] = null;
            last--;
            bubbleDown(0);
        }
        return top;
    }
    public void reheap(int index){
        bubbleUp(index);
        bubbleDown(index);
    }
    private void bubbleUp(int current){
        int pIndex = getParent(current);
        while (true) {
            if(current < 1 || pIndex < 0 || current > last|| data[current] == null)
                return;
            if(data[pIndex].score < data[current].score)
                break;
            Node temp = data[current];
            data[current] = data[pIndex];
            data[pIndex] = temp;
            current = pIndex;
            pIndex = getParent(current);
        }
        data[current].heapIndex = current;//the index of where the node ends up
    }
    private void bubbleDown(int current) {
        int min;
        int left = getLeft(current);
        int right = getRight(current);
        if (right >= last) {
            if (left >= last) {
                return;
            } else {
                min = left;
            }
        } else {
            if (data[left].score <= data[right].score) {
                min = left;
            } else {
                min = right;
            }
        }
        if (data[current].score > data[min].score) {
            Node temp = data[min];
            data[min] = data[current];
            data[current] = temp;
            data[current].heapIndex = min;
            bubbleDown(min);
        }
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    private int getLeft(int index) {
        return (index * 2) + 1;
    }

    private int getRight(int index) {
        return (index * 2) + 2;
    }
}
