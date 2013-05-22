import java.util.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class NetworkTest.
 *
 * @author Zach Souser
 * @version 3/1/13
 */
public class NetworkTest
{
    /**
     * Default constructor for test class NetworkTest
     */
    
    Network n1,n2,n3,n4;
    Graph g1, g2, g3,g4;
    public NetworkTest()
    {
        // We will use the same nodes for each graph
        List<String> nodeList = new ArrayList<String>();
        nodeList.add("s");
        nodeList.add("t");
        nodeList.add("1");
        nodeList.add("2");
        nodeList.add("3");
        nodeList.add("4");
        nodeList.add("5");
        nodeList.add("6");
        
        // Initialize all node costs to zero
        List<Integer> nodeCosts = new ArrayList<Integer>();
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        nodeCosts.add(new Integer(0));
        
        
        // Initialize the working edge list
        List<List<String>> edgeList = new ArrayList<List<String>>();
        
        // Initialize edge, add to the list, repeat.
        ArrayList<String> edge = new ArrayList<String>();
        edge.add("s");
        edge.add("1");
        edge.add("true");
        edge.add("1");
        edge.add("3");      
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("2");
        edge.add("true");
        edge.add("2");
        edge.add("5");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("3");
        edge.add("true");
        edge.add("3");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("4");
        edge.add("t");
        edge.add("true");
        edge.add("4");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("5");
        edge.add("t");
        edge.add("true");
        edge.add("5");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("6");
        edge.add("t");
        edge.add("true");
        edge.add("6");
        edge.add("4");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("3");
        edge.add("5");
        edge.add("true");
        edge.add("10");
        edge.add("5");
        edgeList.add(edge);
        
        g1 = new Graph(nodeList, nodeCosts, true, edgeList);
        
        // Flow
        
        edgeList = new ArrayList<List<String>>();
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("1");
        edge.add("true");
        edge.add("1");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("2");
        edge.add("true");
        edge.add("2");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("3");
        edge.add("true");
        edge.add("3");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("4");
        edge.add("t");
        edge.add("true");
        edge.add("4");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("5");
        edge.add("t");
        edge.add("true");
        edge.add("5");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("6");
        edge.add("t");
        edge.add("true");
        edge.add("6");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("1");
        edge.add("5");
        edge.add("true");
        edge.add("7");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("2");
        edge.add("4");
        edge.add("true");
        edge.add("8");
        edge.add("4");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("2");
        edge.add("6");
        edge.add("true");
        edge.add("9");
        edge.add("1");
        edgeList.add(edge);
        
        g2 = new Graph(nodeList, nodeCosts, true, edgeList);
        
        // g3 is for testing min cut
        
        edgeList = new ArrayList<List<String>>();
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("1");
        edge.add("true");
        edge.add("1");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("2");
        edge.add("true");
        edge.add("2");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("3");
        edge.add("true");
        edge.add("3");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("4");
        edge.add("t");
        edge.add("true");
        edge.add("4");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("5");
        edge.add("t");
        edge.add("true");
        edge.add("5");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("6");
        edge.add("t");
        edge.add("true");
        edge.add("6");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("1");
        edge.add("5");
        edge.add("true");
        edge.add("7");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("2");
        edge.add("4");
        edge.add("true");
        edge.add("8");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("2");
        edge.add("6");
        edge.add("true");
        edge.add("9");
        edge.add("1");
        edgeList.add(edge);
        
        g3 = new Graph(nodeList, nodeCosts, true, edgeList);
        // Initialize the networks
        
        edgeList = new ArrayList<List<String>>();
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("1");
        edge.add("true");
        edge.add("1");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("2");
        edge.add("true");
        edge.add("2");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("s");
        edge.add("3");
        edge.add("true");
        edge.add("3");
        edge.add("1");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("4");
        edge.add("t");
        edge.add("true");
        edge.add("4");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("5");
        edge.add("t");
        edge.add("true");
        edge.add("5");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("6");
        edge.add("t");
        edge.add("true");
        edge.add("6");
        edge.add("2");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("1");
        edge.add("5");
        edge.add("true");
        edge.add("7");
        edge.add("5");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("2");
        edge.add("4");
        edge.add("true");
        edge.add("8");
        edge.add("5");
        edgeList.add(edge);
        
        edge = new ArrayList<String>();
        edge.add("3");
        edge.add("6");
        edge.add("true");
        edge.add("9");
        edge.add("5");
        edgeList.add(edge);
        
        g4 = new Graph(nodeList,nodeCosts,true,edgeList);
        n1 = new Network(g1,"s","t");
        n2 = new Network(g2,"s","t");
        n3 = new Network(g3,"s","t");
        n4 = new Network(g4,"s","t");
    }

    @Test
    public void testFlowValue() {
        assertEquals(new Integer(4),n1.flowValue(g2));
       // Test g2, which represents a flow that lines up with n1's source and sink
    }
    
    @Test
    public void testMaxFlow() {
        assertEquals(new Integer(8),n1.flowValue(n1.maxFlow()));
        // test the max flow's flow value
        // g2 is a flow and will not have a max flow for g1
        assertEquals(new Integer(3),n3.flowValue(n3.maxFlow()));
        assertEquals(new Integer(2),n4.flowValue(n4.maxFlow()));
    }
    
    @Test
    public void testCutCapacity() {
        
        // Every vertex
        
        List<String> list = new ArrayList<String>();
        list.add("s");
        list.add("t");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        assertEquals(new Integer(0),n1.cutCapacity(list));
        
        // Some vertices
        
        list = new ArrayList<String>();
        list.add("s");
        list.add("t");
        list.add("1");
        list.add("2");
        assertEquals(new Integer(1),n1.cutCapacity(list));
        
        // Just the source
        
        list = new ArrayList<String>();
        list.add("s");
        assertEquals(new Integer(9),n1.cutCapacity(list));
        
        // The empty list
        
        list = new ArrayList<String>();
        assertEquals(new Integer(0),n1.cutCapacity(list));
    }  
    
    @Test
    public void testMinCut() {
        // Initialize expected, and add a few vertices
        List<String> expected = new ArrayList<String>();
        expected.add("s");
        expected.add("1");
        expected.add("2");
        assertEquals(expected,n1.minCut());
        // Try expected again with different vertices
        expected = new ArrayList<String>();
        expected.add("s");
        expected.add("3");
        assertEquals(expected,n3.minCut());
        
        expected = new ArrayList<String>();
        expected.add("s");
        assertEquals(expected,n4.minCut());
    }
        
}
