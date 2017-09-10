// Disclaimer: This sample Java class is downloaded from http://cogsci.ucsd.edu/~batali/cogsci109/searches.html
//             and used as a case study for Introduction to AI module taught in the University of Nottingham,  
//             Malaysia Campus for the 2007/2008 session   

import java.util.*;

public class State {

    // stores the tile at each position    
    int[] tiles = new int[9];

    // records the moves that got to here from the scrambled puzzle
    Ivector moves = new Ivector();

    // estimated distance to the goal configuration
    double value;

    // Default constructor puts the tiles in the solved configuration, 
    // sets the value to zero, and leaves the moves vector empty.
    State () {
        value = 0;
        goalTiles(tiles);
        ++count; // count each state as it is created
    }


    // This constructor takes an array of tile positions, 
    // uses 0 for the value, and leaves the moves vector empty.
    State (int[] ta) {
        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = ta[i]; 
        }
        value = 0;
        ++count; // count each state as it is created
    }

    // This constructor takes an array of tile positions, an Ivector of moves, 
    // and a value, and stores them in the appropriate slots.
    State (int[] ta, Ivector mv, double v) {
        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = ta[i]; 
        }
        moves = new Ivector(mv);
        value = v;
        ++count; // count each state as it is created
    }

    // Returns the label that should be at position p in a solved puzzle.
    // (0 is used as the label of the blank tile.)
    public static int goalTile (int p) {
        // set goal state: empty square at index 8 and the rest at index+1
        // meaning: index 0 holds tile 1. index 1 holds tile 2
        if (p == 8) return 0;
        else return p + 1; 
    }

    // Returns the position of the tile with the given label in the solved puzzle.
    // (0 is used as the label of the blank tile.)
    public static int goalPos (int t) {
        if (t == 0) return 4;
        else if (t < 5) return t-1;
        else return t;
    }

    // Store the solved puzzle configuration into the array.
    public static void goalTiles (int[] ia) { 
        for (int p = 0; p < 9; ++p) { 
            ia[p] = goalTile(p); 
        }
    }

    // Find the position of the tile with the given label in the array.
    // Returns -1 if no such tile is found.
    public static int findTile (int[] ia, int t) { 
        for (int p = 0; p < ia.length; ++p) { 
            if (ia[p] == t) return p;
        }
        return -1;
    }

    // Return the row corresponding to the position.
    public static int getRow (int p) { 
        return p/3;
    }

    // Return the column corresponding to the position.
    public static int getCol (int p) { 
        return p%3;
    }

    // Make an integer position corresponding to a given row and column.
    public static int makePos (int r, int c) { 
        return r*3+c;
    }

    // Test if the tile at the row tr and column tc can move if the
    // blank tile is at row br and column bc.
    public static boolean canMove (int tr, int tc, int br, int bc) { 
        int dx = tc - bc, dy = tr - br;
        return (dx==0&&Math.abs(dy)==1)||(dy==0&&Math.abs(dx)==1);
    }

    // Test if the tile at position tp can move if the blank tile is
    // at position bp.
    public static boolean canMove (int tp, int bp) {
        int tr=getRow(tp),tc=getCol(tp),br=getRow(bp),bc=getCol(bp);
        return canMove(tr,tc,br,bc);
    }

    // Test if the tile with label t can move in the tile
    // configuration given by ia.
    public static boolean canMove (int[] ia, int t) { 
        int tp = findTile(ia, t), bp = findTile(ia, 0);
        return canMove(tp,bp);
    }

    // Required by the Comparable interface - compares the value slots.
    public int compareTo (Object o) { 
        State s = (State) o;
        if (value < s.value) return -1; 
        else if (value > s.value) return 1; 
        else return 0;
    }

    // Return the number of tiles not in their goal position.
    public static int countDiff (State s) {
        int d = 0; 
        for (int p = 0; p < 9; ++p) { 
            if (goalTile(p) != s.tiles[p]) ++d;
        }
        return d;
    }

    // Returns true if the tiles of the state are in the goal configuration.
    public static boolean isGoal (State s) { 
        return countDiff(s) == 0; 
    }

    // Modify the tile configuration in ia to record that the tile
    // with label t was moved into the blank position.  
    // Signals an error if the tile can't be moved.
    public static void doMove (int[] ia, int t) {
        int tp = findTile(ia, t), bp = findTile(ia, 0);
        if (!canMove(tp,bp)) throw new Error("Bad move");
        ia[bp] = ia[tp]; 
        ia[tp] = 0; 
    }

    // Modify the tile configuration in ia to record the sequence of
    // moves specified by the sequence in iv.
    public static void doMoves (int[] ia, Ivector iv) {
        int i, s = iv.size();
        for (i = 0; i < s; ++i) { 
            doMove(ia, iv.at(i));
        }
    }

    // Print the tiles and value of the state.
    public static void printState (State s) {
        for (int i = 0; i < 3; ++i) { 
            for (int j = 0; j < 3; ++j) { 
                int t = s.tiles[makePos(i,j)];
                if(t==0) System.out.print(" "); 
                else System.out.print(t); 
            }
            if(i==2) System.out.print(" "+s.value);
            System.out.println(); 
        }
    }

    public static void decrementCount (){
        --count;
    }

    public static void incrementCount (){
        ++count;
    }

    public static void setCount(int in){
        count = in;
    }
    
    // ================================================================

    // Keep track of the total number of states that have been created.
    private static int count = 0;

    // Returns the value of the static count slot.
    public static int getCount() { 
        return count; 
    }

    // ================================================================

    // Test the code in this class.

    public static void main (String[] args) { 
        int [] ma = { 2, 3, 5, 8, 7, 2 };
        Ivector iv = new Ivector (ma); 
        State s = new State();

        s.value = countDiff(s);
        System.out.println("Solved configuration:");
        printState(s);
        System.out.println(); 

        System.out.println("Scrambling moves:"); 
        System.out.println(iv);
        System.out.println(); 
        doMoves(s.tiles,iv);

        s.value = countDiff(s);
        System.out.println("Scrambled state:"); 
        printState(s);
        System.out.println();
        
        System.out.println("Total states created: "+getCount());
    }
}


