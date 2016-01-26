package pathfinders;

public class Node {
    public int index = 0;
    public int score = Integer.MAX_VALUE;
    public Node prev = null;
    public int state = 0;
    public int heapIndex = 0;
    
    public static final int INVISIBLE = 0;
    public static final int OPEN = 1;
    public static final int CLOSED = 2;
    
    public Node(){
        
    }
    public Node(int i){
        index = i;
        score = Integer.MAX_VALUE;
        prev = null;
        state = 0;
    }
    public Node(int i, int s){
        index = i;
        score = s;
        prev = null;
        state = 0;
    }
    public Node(int i, int sc, int st){
        index = i;
        score = sc;
        prev = null;
        state = st;
    }
    public Node(int i, int sc, int st, Node p){
        index = i;
        score = sc;
        prev = p;
        state = st;
    }
    public String toString(){
        return "index: " + index + " score: " + score + " state: " + state;
    }
}