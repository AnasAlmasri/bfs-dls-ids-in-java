// Disclaimer: This sample Java class is downloaded from http://cogsci.ucsd.edu/~batali/cogsci109/searches.html
//             and used as a case study for Introduction to AI module taught in the University of Nottingham,  
//             Malaysia Campus for the 2007/2008 session   

import java.util.*;

public class searchers { 
	               // indices :      0  1  2  3  4  5  6  7  8
    public static int [] tiles1 =  { 1, 0, 3, 4, 2, 5, 6, 7, 8 };
    public static int [] tiles2 =  { 1, 2, 3, 4, 7, 5, 0, 6, 8 };
    public static int [] tiles4 =  { 4, 1, 3, 2, 0, 5, 6, 7, 8 };
    public static int [] tiles6 =  { 2, 4, 3, 1, 5, 8, 6, 7, 0 };
    public static int [] tiles8 =  { 0, 3, 5, 1, 4, 8, 6, 2, 7 };
    public static int [] tiles10 = { 1, 3, 5, 6, 2, 8, 0, 7, 4 };
    public static int [] tiles11 = { 4, 0, 3, 6, 1, 7, 8, 2, 5 };
    
    public static int [] initialState =  { 0, 1, 3, 4, 2, 5, 7, 8, 6 };
    
    public static Ivector bfs (State s0) { // implementation of Breadth-First Search
    	List<int[]> visitedStates = new ArrayList<int[]>(); // list of visited tile arrays
		Bag b = new Bag(); // create bag (indirect diagram that withholds states)
		boolean visited = false; // visitation indicator
		boolean foundGoal = false; // indicates whether the goal was found

		b.pushBack(s0); // push initial state into the back of the queue
		visitedStates.add(s0.tiles); // mark state as visited
		State ns = s0;
		State.decrementCount(); // exclude the root node from the state count
		while (b.size() > 0) { // loop while the queue is not empty
		    ns = b.popFront(); // remove the queue's head
		    if (State.isGoal(ns)) break; // check if it's the goal state

		    for (int m = 1; m < 9; ++m) { // loop through tile numbers (1-8)
				visited = false; // set visitation indicator to 0
				if (State.canMove(ns.tiles,m)) { // if the tile can be moved
				    State ms = new State(ns.tiles,ns.moves,0); // create a new state
				    State.doMove(ms.tiles,m); // move the tile in the new state

				    for (int[] i : visitedStates){ // loop through the list of visited states
				    	// if the new state's tile array exists in array list, set visitaion ind. to 1
				    	if (Arrays.equals(i, ms.tiles)) visited = true;
				    } // end for loop
				    if (visited){
				    	State.decrementCount();
				    	continue; // if new state was visited, ignore it
				    }

				    ms.moves.push(m); // push new state's moves into the moves Ivector
				    b.pushBack(ms); // push new state into the back of the queue 
				    visitedStates.add(ms.tiles); // mark the new state as visited
			    	if (State.isGoal(ms)) foundGoal = true;
			    } // end if statement
			    if (foundGoal) break;
		    } // end or loop
		} // end while loop
		return ns.moves; // return moves' Ivector to caller
    } // end method bfs()
    
    public static Ivector dls (State inState, int depthLimit){ // implementation of Depth-limited Search
    	Stack<State> stateStack = new Stack<State>(); // stack of states in the diagram
    	List<int[]> visitedStates = new ArrayList<int[]>(); // list of visited tile arrays
    	boolean visited = false; // visitation indicator
    	int depthCount = 0; // depth counter. used to compare with depth limit

    	State.setCount(0); // set state counter to zero

    	State currentState = inState; // set current state to initial state
    	stateStack.push(currentState); // push current state onto the stack
    	visitedStates.add(currentState.tiles); // mark current state as visited
    	State.decrementCount(); // exclude the root node from the state count
    	State.decrementCount(); // exclude the newly generated node from the state count

    	while (!stateStack.isEmpty()){ // loop while stack is not empty
    		// get stack top and set it as current state
			if (!stateStack.isEmpty()) currentState = stateStack.pop();
    		if (State.isGoal(currentState)) break; // check if it's the goal state
    		
    		for (int m=8; m>0; m--){ // loop through tile numbers (1-8)
    			if (State.canMove(currentState.tiles,m)) { // if the tile can be moved
    				if ((depthCount == depthLimit) && !stateStack.isEmpty()) { // check whether depth hit the limit
				    	stateStack.pop(); // pop stack top
				    	depthCount=0; // set depth counter to zero
				    }
    				else { // if depth did not hit the limit
					    State newState = new State(currentState.tiles,currentState.moves,0); // create a new state
					    State.doMove(newState.tiles,m); // move the tile in the new state

					    visited = false; // set visitation indicator to 0
					    for (int[] i : visitedStates){ // loop through the list of visited states
					    	// if the new state's tile array exists in array list, set visitation indicator to 1
					    	if (Arrays.equals(i, newState.tiles)) visited = true;
					    } // end for loop
					    if (!visited){ // if state has not been visited
						    visitedStates.add(newState.tiles); // mark new state as visited
						    stateStack.push(newState); // push new state onto the stack
						    newState.moves.push(m); // push new state's moves into the moves Ivector
					    } // end if statement
				    } // end if statement
			    } // end if statement
    		} // end for loop
    		State.incrementCount(); // increment state counter
    		++depthCount; // increment depth counter
    	} // end while loop
    	return currentState.moves; // return moves' Ivector to caller
    } // end method dls()

    public static Ivector iddfs (State inState){ // implementation of Iterative Deepening DFS
    	Stack<State> stateStack = new Stack<State>(); // stack of states in the diagram
    	List<int[]> visitedStates = new ArrayList<int[]>(); // list of visited tile arrays
    	State currentState = new State();
    	boolean visited = false; // visitation indicator
    	boolean foundGoal = false;
    	int depthCount = 0; // depth counter. used to compare with depth limit
    	int depthLimit = 0;

    	State.setCount(0); // set state counter to zero
    	State.decrementCount(); // exclude the root node from the state count

    	while (!State.isGoal(currentState) || depthLimit == 0){
    		currentState = inState; // set current state to initial state
    		stateStack.push(currentState); // push current state onto the stack
    		visitedStates.add(currentState.tiles); // mark current state as visited
    		//State.decrementCount(); // exclude the root node from the state count
	    	while (!stateStack.isEmpty()){ // loop while stack is not empty
	    		// get stack top and set it as current state
				if (!stateStack.isEmpty()) currentState = stateStack.pop();
	    		if (State.isGoal(currentState)) break; // check if it's the goal state
	    		
	    		for (int m=8; m>0; m--){ // loop through tile numbers (1-8)
	    			if (State.canMove(currentState.tiles,m)) { // if the tile can be moved
	    				if ((depthCount == depthLimit) && !stateStack.isEmpty()) { // check whether depth hit the limit
					    	stateStack.pop(); // pop stack top
					    	depthCount=0; // set depth counter to zero
					    }
	    				else { // if depth did not hit the limit
						    State newState = new State(currentState.tiles,currentState.moves,0); // create a new state
						    State.doMove(newState.tiles,m); // move the tile in the new state

						    visited = false; // set visitation indicator to 0
						    for (int[] i : visitedStates){ // loop through the list of visited states
						    	// if the new state's tile array exists in array list, set visitation indicator to 1
						    	if (Arrays.equals(i, newState.tiles)) visited = true;
						    } // end for loop
						    if (!visited){ // if state has not been visited
							    visitedStates.add(newState.tiles); // mark new state as visited
							    stateStack.push(newState); // push new state onto the stack
							    newState.moves.push(m); // push new state's moves into the moves Ivector
							    if (State.isGoal(newState)){
							    	currentState = newState;
							    	foundGoal = true;
							    }
						    } // end if statement
					    } // end if statement
				    } // end if statement
				    if (foundGoal) break;
	    		} // end for loop
	    		if (foundGoal) break;
	    		//State.printState(newState);
	    		State.incrementCount(); // increment state counter
	    		++depthCount; // increment depth counter
	    	} // end while loop
	    	++depthLimit;
	    	visitedStates.clear();
    		stateStack.clear();
    		depthCount = 0;
	    } // end while loop
	    State.decrementCount(); // exclude the goal node from the state count
    	return currentState.moves; // return moves' Ivector to caller
    } // end method iddfs()
    
    public static void main (String[] args) { 
		// BREADTH-FIRST SEARCH CONTROL
		State s0 = new State (initialState); // create initial state
		int istates = State.getCount(); // get state count
		Ivector sv = bfs(s0); // call method bfs() and receive moves
		int nstates = State.getCount()-istates; // states = (state count) -(initial count)
		// output bfs() results
		System.out.printf("\nBreadth-First Search:\n");
		System.out.printf("States: %d; Steps: %d\n", nstates, sv.size());
		System.out.println(sv); // print moves
		State.doMoves(s0.tiles,sv); // do moves
		State.printState(s0); // display final state
		//-------------------------------------------------------------------------
		System.out.println();
		System.out.println("--------------------");
		System.out.println();
		//-------------------------------------------------------------------------
		// DEPTH-LIMITED SEARCH CONTROL
		State s1 = new State(initialState); // create initial state
		Ivector s1_moves = dls(s1, 4); // call method dls() and receive moves
		int dlsStates = State.getCount() - nstates; // states = (state count) - (count from bfs())
		// output results
		System.out.printf("Depth-Limited Search:\n");
		System.out.printf("States: %d; Steps: %d\n", dlsStates, s1_moves.size());
		System.out.println(s1_moves); // print moves
		State.doMoves(s1.tiles,s1_moves); // do moves
		State.printState(s1); // display final state
		//-------------------------------------------------------------------------
		System.out.println();
		System.out.println("--------------------");
		System.out.println();
		//-------------------------------------------------------------------------
		// ITERATIVE DEEPENING DFS CONTROL
		State s2 = new State(initialState); // create initial state
		Ivector s2_moves = iddfs(s2);
		int iddfsStates = State.getCount();// - dlsStates; // states = (state count) - (count from dls())
		// output results
		System.out.printf("Iterative Deepening Depth-First Search:\n");
		System.out.printf("States: %d; Steps: %d\n", iddfsStates, s2_moves.size()); // output state count
		System.out.println(s2_moves); // print moves
		State.doMoves(s2.tiles,s2_moves);
		State.printState(s2); // call mathod iddfs() and display final state
    } // end method main()

} // end class searchers.java