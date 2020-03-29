import java.util.Scanner;

/**
 * @author Davis Jeffrey
 * <p>
 * Basically, UnionFind covers:
 * Union command: Connect two objects.
 * Find query: Is there a path connecting the two objects?
 * <p>
 * Improvements: Weighting(connect smaller tree to larger tree)
 * Path compression: During Find, set every root to root of parent
 */
public class UnionFind {
    public static void main(String[] args) {
        new UnionFind().testClient();
    }

    private int[] a, weight;

    /**
     * Here, a[i] == root of i
     * Initialize all values to that index, since no objects are connected initially
     * Initialize all weights to 1
     */
    public void initializeUnionFind(int size) {
        a = new int[size];
        weight = new int[size];

        for (int i = 0; i < size; i++) {
            a[i] = i;
            weight[i] = 1;
        }
    }

    /**
     * Set root of object1 to root of object2
     */
    public void union(int object1, int object2) {
        //a[root(object1)] = object2; //Works, but Find becomes more expensive
        //a[root(object1)] = root(object2); //Better Find, but slower Union
        if (weight[object1] > weight[object2]) { //Better in the long run
            a[object2] = object1;
        } else {
            a[object1] = object2;
        }
    }

    /**
     * Checks if root of object1 == root of object2
     */
    public boolean isConnected(int object1, int object2) {
        int root1 = root(object1);
        int root2 = root(object2);
        return (root1 == root2);
    }

    /**
     * Root object has been found when an object's root points to itself(object == a[object])
     */
    private int root(int object) {
        System.out.print("Root of " + object + ": ");
        while (object != a[object]) {
            a[object] = a[a[object]];
            object = a[object];
            System.out.print("-->" + object);
        }
        System.out.println("");
        return object;
    }

    /**
     * Util method to interact with the Data Structure
     */
    private void testClient() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter number of objects: ");
            int size = Integer.parseInt(sc.nextLine());
            initializeUnionFind(size);
            char option;
            System.out.println("Enter commands \"u object1 object2\" or \"f object1 object2\"");
            do {

                String input = sc.nextLine();
                String[] inputArr;
                option = input.charAt(0);
                switch (option) {
                    case 'u':
                        inputArr = input.split("\\s");
                        union(Integer.parseInt(inputArr[1]), Integer.parseInt(inputArr[2]));
                        System.out.println(inputArr[1] + " joined to " + inputArr[2]);
                        break;
                    case 'f':
                        inputArr = input.split("\\s");
                        boolean connected = isConnected(Integer.parseInt(inputArr[1]), Integer.parseInt(inputArr[2]));
                        System.out.println("Are " + inputArr[1] + " and " + inputArr[2] + " connected: " + connected);
                        break;
                    default:
                        System.out.println("Exiting");
                        option = 'e';
                        break;
                }
            } while (option != 'e');
        } catch (Exception e) {
            System.out.println("Caught exception, exiting. ");
            e.printStackTrace();
        }
    }
}
