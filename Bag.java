// Disclaimer: This sample Java class is downloaded from http://cogsci.ucsd.edu/~batali/cogsci109/searches.html
//             and used as a case study for Introduction to AI module taught in the University of Nottingham,  
//             Malaysia Campus for the 2007/2008 session   

// A "Bag" is a linked-list of States, it can push and pop from the front or back, 
// and can sort its contents.

import java.util.*;

public class Bag { 

    // Stores the States in a LinkedList.
    private LinkedList<State> data = new LinkedList<State>(); 

    // Returns the number of States.
    public int size() { 
        return data.size(); 
    }

    // Add to the front of the list.
    public void pushFront (State s) {
        data.addFirst(s);
    }

    // Add to the end of the list.
    public void pushBack (State s) { 
        data.addLast(s); 
    }

    // Return the first State in the list
    // Doesn't change the list.
    public State getFront () { 
        return data.getFirst(); 
    }

    // Return the last State in the list.
    // Doesn't change the list.
    public State getBack () { 
        return data.getLast(); 
    }

    // Return the first State in the list, 
    // after removing it.
    public State popFront () { 
        State s = getFront(); 
        data.removeFirst(); 
        return s; 
    }

    // Returns the last State in the list, 
    // after removing it.
    public State popBack () { 
        State s = getBack(); 
        data.removeLast(); 
        return s;
    }   

/*
    public boolean visited(int[] inArray){
        for (Iterator<State> i = data.iterator(); i.hasNext();){
            State s = i.next();
            if (s.tiles == inArray) return true;
        }
        return false;
    }
*/


    // Print the states in the bag.
    public void printStates () { 
        for (Iterator<State> i = data.iterator(); i.hasNext();) {
            State s = i.next(); 
            State.printState(s); 
            if (i.hasNext()) System.out.println(); 
        }
    }
}

