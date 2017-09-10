// Disclaimer: This sample Java class is downloaded from http://cogsci.ucsd.edu/~batali/cogsci109/searches.html
//             and used as a case study for Introduction to AI module taught in the University of Nottingham,  
//             Malaysia Campus for the 2007/2008 session   

// Ivector implements a variable-sized array of integer values.

import java.util.Vector;

public class Ivector { 

    // The data slot holds an instance of the Vector class
    private Vector<Integer> data = new Vector<Integer>();

    // Push a new value onto the end of the current Ivector.
    // (Must be converted to the Integer wrapper class.)
    public void push (int i) { 
        data.addElement(new Integer(i));
    }

    // Push all of the values in the array onto the end of the current
    // Ivector.
    public void push (int[] ia) { 
        int i, s = ia.length; 
        for(i = 0; i < s; ++i) push(ia[i]); 
    }

    // Push all of the values in an existing Ivector onto the end of
    // the current one.
    public void push (Ivector v) { 
        int i, s = v.size(); 
        for(i = 0; i < s; ++i) push(v.at(i)); 
    }

    // Default constructor makes an empty vector.
    public Ivector() {}

    // This constructor makes an Ivector out of an array of ints.
    public Ivector (int[] ia) { 
        push(ia); 
    }

    // This constructor makes an Ivector out of an existing Ivector
    public Ivector (Ivector iv) {
        push(iv);
    }

    // Returns the number of elements in the vector.
    public int size() {
        return data.size(); 
    }

    // Returns the element at index p
    public int at (int p) { 
        Integer i = data.elementAt(p);
        return i.intValue(); 
    }

    // Returns the last element
    public int last () { 
        Integer i = data.lastElement();
        return i.intValue(); 
    }

    // Set the element at index p to the value v
    public void set (int p, int v) { 
        data.set(p, new Integer(v));
    }

    // Returns a new Ivector with all of the elements of the current
    // one except the one at position p.
    public Ivector rem (int p) { 
        Ivector nv = new Ivector(); 
        int i, s = size(); 
        for(i = 0; i < s; ++i) { 
            if (i != p) nv.push(at(i)); 
        }
        return nv; 
    }

    // Returns a new Ivector with a subsequence of the current Ivector's elements
    // beginning with the element at s, up to, but not including the element at e.
    public Ivector subv (int s, int e) { 
        Ivector iv = new Ivector();
        for (; s < e; ++s) iv.push(at(s)); 
        return iv; 
    }

    // Return a new Ivector containing the first e elements of the current one.
    public Ivector head (int e) { 
        return subv(0, e); 
    }

    // Return a new Ivector containing the elements of the current
    // one starting at position s.
    public Ivector tail (int s) { 
        return subv(s, size()); 
    }

    // toString method for simple printing
    public String toString () { 
        return data.toString();  
    }
    
    // Print the elements of an Ivector, one per line.
    public static void printv (Ivector iv) { 
        int i, n = iv.size(); 
        for (i = 0; i < n; ++i ) { 
            System.out.println (iv.at(i));
        }
    }

    // ================================================================

    // Test of Ivector code
    public static void main (String[] args) { 

        int[] vals = { 33, 44, 55, 66, 77, 88, 99 }; 
        Ivector iv = new Ivector(vals); 

        System.out.println("Original Ivector"); 
        System.out.println(iv); 
        System.out.println(); 

        System.out.println("First two elements"); 
        System.out.println(iv.head(2)); 
        System.out.println(); 

        System.out.println("Elements starting at index 2"); 
        System.out.println(iv.tail(2)); 
    }
}

    