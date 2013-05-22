import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Network-Flow implementation that uses residual edges
 * to find maximum flow and minimum cut
 * 
 * @author Zach Souser
 * @version 3/1/13
 */

public class Network
{
    /**
    * The graph being modeled
    */
   
    private Graph g;
   
    /**
    * The vertex list
    */
   
    private List<Vertex> vertices;
   
    /**
     * The edge list
     */
    
    private List<Edge> edges;
    
    /**
     * The source vertex
     */
    
    private Vertex source;
    
    /**
     * The sink vertex
     */
    
    private Vertex sink;
    
    /**
     * Constructor for objects of class Network
     * @param g the graph in question
     * @param source the name of the source
     * @param sink the name of the sink
     */
    
    public Network(Graph g, String source, String sink)
    {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
        this.g = g;
        List<List<String>> nodes = g.getNodesAsStrings();
        List<List<String>> edges = g.getEdgesAsStrings();
        for (List<String> node : nodes) {
            addVertex(new Vertex(node.get(0),(Integer)(Integer.parseInt(node.get(1)))));
        }
        
        for (List<String> edge : edges) {
            Vertex start = getVertex(edge.get(0));
            Vertex end = getVertex(edge.get(1));
            this.edges.add(start.addEdge(new Edge(start,end,Boolean.parseBoolean(edge.get(2)),edge.get(3),0,(Integer)Integer.parseInt(edge.get(4)))));
        }
        
        this.source = getVertex(source);
        this.sink = getVertex(sink);
    }
    
    /**
     * Add a vertex
     * @param v the vertex
     * @return that vertex
     */
    
    private Vertex addVertex(Vertex v) {
        this.vertices.add(v);
        return v;
    }
    
    /**
     * Get a vertex by name
     * @param name the name of the vertex
     * @return the vertex
     */
    
    private Vertex getVertex(String name) {
        for (Vertex v : vertices) {
            if (v.name().equals(name)) return v;
        }
        return null;
    }

    /**
     * Clear the graph for searching
     */
    
    private void clear() {
        for (Vertex v : vertices) {
            v.clear();
        }
        for (Edge e : edges) {
            e.clear();
        }
    }
    
    /**
     * Get the source
     * @return the source
     */
    
    public Vertex source() {
        return source;
    }
    
    /**
     * Get the sink
     * @return the sink
     */
    
    public Vertex sink() {
        return sink;
    }
    
    /**
     * Breadth-first-search
     * @param start the starting point for the breadth first search
     */
    
    private void bfs(Vertex start) {
        LinkedList<Vertex> q = new LinkedList<Vertex>();
        q.add(start);
        start.discover();
        while (!q.isEmpty()) {
            Vertex v = q.removeLast(); 
            List<Edge> edges = v.getEdgeList();
            for (Edge e : edges) {
                if (!e.isProcessed()) {
                    e.process();
                }
                if (!e.end.isDiscovered()) {
                    q.add(e.end);
                    e.end.discover();
                }
                e.end.setParent(v);
            }
        }
    }
    
    /**
     * Add residual edges to the graph for modeling
     */
    
    private void addResidualEdges() {
        ArrayList<Edge> residuals = new ArrayList<Edge>();
        for (Edge e : edges) {
            residuals.add(new Edge(e.end,e.start,true,e.label() + "'",e.flow(),0));
        }
        edges.addAll(residuals);
    }
    
    /**
     * Generate a maximum flow from the graph and return it
     * @return the flow graph
     */
    
    public Graph maxFlow() {
        addResidualEdges();
        clear();
        bfs(source);
        int volume = pathVolume(source,sink);
        while (volume > 0) {
            augmentPath(source,sink,volume);
            clear();
            bfs(source);
            volume = pathVolume(source,sink);
        }
        
        return this.saveFlow();
    }
    
    /**
     * Augment the path from start to end with volume recursively
     * 
     * @param start the start
     * @param end the end
     * @param volume the volume
     */
    
    private void augmentPath(Vertex start, Vertex end, int volume) {
        if (start == end) return;
        Edge e = findEdge(end.parent,end);
        e.flow += volume;
        e.residual -= volume;
        e = findEdge(end,end.parent);
        e.residual += volume;
        e.flow -= volume;
        augmentPath(start,end.parent,volume);
    }
    
    /**
     * Calculate the flow value of the graph, determined by the flow leaving
     * from the source
     * 
     * @param g the graph
     * @return the value of the flow
     */
    
    public Integer flowValue(Graph g) {
         int total = 0;
         for (List<String> edge : g.getEdgesAsStrings()) {
            if (edge.get(0).equals(this.source.name())) total += Integer.parseInt(edge.get(4));
         }
         return new Integer(total);
    }
    
    /**
     * Probe for the path volume recursively to determine how much
     * you can augment the path by
     * 
     * @param start the start
     * @param end the end
     * @return the volume remaining on the path
     */
    
    private int pathVolume(Vertex start, Vertex end) {
        if (end.parent == null) return 0;
        
        Edge e = findEdge(end.parent, end);
        
        if (start == end.parent) return e.residual;
        else return Math.min(pathVolume(start,end.parent),e.residual);
        
    }
    
    /**
     * Find an edge by its vertices
     * @param start the start
     * @param end the end
     * @return the matching edge
     */
    
    private Edge findEdge(Vertex start, Vertex end) {
        for (Edge e : edges) {
            if (e.start == start && e.end == end) return e;
        }
        return null;
    }
    
    /**
     * Convert the flow generated by the network into a graph
     * @reutrn the graph that represents the flow of this network
     */
    
    private Graph saveFlow() {
        List<String> nodeList = new ArrayList<String>();
        List<Integer> nodeCosts = new ArrayList<Integer>();
        for (Vertex v : vertices) { 
            nodeList.add(v.name());
            nodeCosts.add(new Integer(0));
        }
        List<List<String>> edgeList = new ArrayList<List<String>>();
        for (Edge e : this.edges) {
            List<String> oneEdge = new ArrayList<String>();
            oneEdge.add(e.start().name());
            oneEdge.add(e.end().name());
            oneEdge.add("" + e.directed());
            oneEdge.add("" + e.label());
            oneEdge.add("" + e.residual());
            edgeList.add(oneEdge);
        }
        return new Graph(nodeList, nodeCosts, true, edgeList);
    }    
    
    /**
     * Calculate the cut capacity of a given cut
     * 
     * @param cut the list of nodes in the cut
     * @reutrn the capacity of the cut
     */
         
    public Integer cutCapacity(List<String> cut) {
        int total = 0;
        for (Edge e : edges) {
            if (cut.contains(e.start().name()) && !cut.contains(e.end().name())) {
                total += e.residual();
            }
        }
        return new Integer(total);
    }
    
    /**
     * Calculate the minimum cut by building a cut and keeping only optimal nodes
     * @return the cut list
     */
    
    public List<String> minCut() {
        List<String> cut = new ArrayList<String>();
        cut.add(source.name());
        int min = cutCapacity(cut);
        for (String node : this.g.getNodeNamesAsStrings()) {
            if (!node.equals(source.name()) && !node.equals(sink.name())) {
                cut.add(node);
                int cap = (int)cutCapacity(cut);
                if (cap < min) {
                    min = cap;
                } else {
                    cut.remove(node);
                }
            }
        }
        return cut;
    }
    
    /** 
     * toString method
     */
    
    public String toString() {
        return "Source: " + source + "\nSink: " + sink + "\n" + saveFlow();
    }
    
    
    /**
     * An edge in a network flow graph. Keeps track of the flow and the residual for each edge
     * 
     * @author Zach Souser
     * @version 3/1/13
     */
    private class Edge
    {
        /**
         * Start vertex
         */
        
        public Vertex start;
        
        /**
         * End vertex
         */
        
        public Vertex end;
        
        /**
         * The label
         */
        public String label;
        
        /** 
         * The weight of
         */
        
        /**
         * The flow
         */
        
        public int flow;
        
        /**
         * The processed flag
         */
        
        public boolean processed;
        
        /**
         * The directed flag
         */
        
        public boolean directed;
        
        /** 
         * The residual
         */
        
        public int residual;
        
        /**
         * Constructor for objects of class Edge
         * @param start the starting point for the edge
         * @param end the ending point
         * @param directed boolean flag
         * @param label the string to identify the edge by
         * @param residual the initial residual
         */
        
        public Edge(Vertex start, Vertex end, boolean directed, String label, int flow, int residual)
        {
            this.start = start;
            this.end = end;
            this.flow = flow;
            this.directed = directed;
            this.label = label;
            this.residual = residual;
        }
        
        /**
         * Check if the edge has been processed
         * @return true if it has, false if not
         */
        
        public boolean isProcessed() {
            return processed;
        }
        
        /**
         * Process this edge
         */
        
        public void process() {
            this.processed = true;
        }
        
        /**
         * Clear the processed value for this edge
         */
        
        public void clear() {
            this.processed = false;
        }
        
        /**
         * Get the start
         * @return the start vertex
         */
        
        public Vertex start() {
            return start;
        }
        
        /**
         * Get the end
         * @return the end vertex
         */
        
        public Vertex end() {
            return end;
        }
        
        /** 
         * Is the graph directed?
         * @return true or false
         */
        
        public boolean directed() {
            return directed;
        }
        
        /** 
         * Get the label
         * @return the label
         */
        
        public String label() {
            return label;
        }
        
        /** 
         * Return the flow value for this edge
         * @return the flow
         */
        
        public Integer flow() {
            return flow;
        }
        
        /**
         * Return the residual for this edge
         * @return the residual
         */
        
        public int residual() {
            return residual;
        }
    }
    
    
    /**
     * Representation of a vertex
     * 
     * @author Zach Souser
     * @version 3/1/12
     */
    
    private class Vertex
    {
        /**
         * The name of the vertex
         */
        
        public String name;
        
        /**
         * The cost associated with the vertex
         */
        
        public Integer cost;
        
        /**
         * The discovered flag
         */
        
        public boolean discovered;
        
        /**
         * The list of edges incident on the vertex
         */
        
        public List<Edge> edgeList;
        
        /**
         * The parent vertex according to a BFS
         */
        
        public Vertex parent;
        
        /**
         * Constructor for objects of class Vertex
         * @param name the name of the vertex
         */
        
        public Vertex(String name) {
            this.edgeList = new ArrayList<Edge>();
            this.name = name;
            this.cost = 0;
        }
        
        /**
         * Constructor for objects of class Vertex
         * @param name the name of the vertex
         * @param cost the cost associated with the vertex
         */
        
        public Vertex(String name, Integer cost) {
            this.edgeList = new ArrayList<Edge>();
            this.name = name;
            this.cost = cost;
        }
        
        /**
         * Get the edge list
         * @return the list of edges
         */
        
        public List<Edge> getEdgeList() {
            return this.edgeList;
        }
        
        /**
         * Add an edge
         * @param e the edge
         * @return the same edge
         */
        
        public Edge addEdge(Edge e) {
            this.edgeList.add(e);
            return e;
        }
        
        /**
         * Set the parent
         * @param v the vertex to be assigned as the parent
         */
        
        public void setParent(Vertex v) {
            this.parent = v;
        }
        
        /**
         * Access the parent
         * @reutrn the parent
         */
        
        public Vertex getParent() {
            return this.parent;
        }
        
        /**
         * Is the vertex discovered?
         * @return true or false
         */
    
        public boolean isDiscovered() {
            return discovered;
        }
        
        /**
         * Discover the vertex
         */
    
        public void discover() {
            this.discovered = true;
        }
        
        /**
         * Clear the search status of the vertex
         */
        public void clear() {
            this.parent = null;
            this.discovered = false;
        }
        
        /**
         * Access the name
         * @return the name
         */
        
        public String name() {
            return name;
        }
        
        /**
         * toString implementation
         * @return the string
         */
        
        public String toString() {
            return name;
        }
    }

}
