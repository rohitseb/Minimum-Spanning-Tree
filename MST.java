import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MST_Rohit_Sebastian_50204806 {

	int verticeCount;
	int edgeCount;
	class HeapNode{
		int id;
		int parent;
		double distance;
		int pos;
		double edgeLength;
		public HeapNode()
		{
		
		}
		public void setId(int id)
		{
			this.id=id;
		}
		
	}
		
		class Heap{
			
			List<HeapNode> heap;
			Map<Integer,HeapNode> nodeStore;
			public Heap()
			{
				heap =  new ArrayList<HeapNode>();
				heap.add(0, null);
				nodeStore = new HashMap<Integer,HeapNode>(); 
			}
			
			 public void insert(HeapNode node)
			 {
				 heap.add(node);
				 node.pos=heap.size()-1;
				 nodeStore.put(node.id, node);
				 heapifyUp(heap.size()-1);
			 }
			 public void heapifyUp(int pos)
			 {
			//	 System.out.println("heapify up");
				 while(pos>1)
				 {
					 int j = Math.floorDiv(pos, 2);
					 if(heap.get(pos).distance<heap.get(j).distance)
					 {
						 HeapNode temp=heap.get(j);
						 
						 heap.add(j,heap.get(pos));
						 heap.remove(j+1);
						 heap.get(j).pos=j;
						 
						 heap.add(pos,temp);
						 heap.remove(pos+1);
						 heap.get(pos).pos=pos;
						 
						 pos=j;
					 }
					 else
					 {
						 break;
					 }
				 }
			 }
			 
			 public HeapNode extractMin()
			 {
				 HeapNode root = heap.get(1);
				 int size = heap.size(); 
				 if(size>2)
				 {
					 heap.add(1,heap.get(size-1));
					 heap.remove(2);
					 heap.remove(size-1);
					// printHeap();
					// System.out.println("heap Size - "+heap.size());
					 heap.get(1).pos=1;
					 heapifyDown(1);
				 }
				 
				 return root;
			 }
			 public void heapifyDown(int i)
			 {
				 int j;
				 while((2*i)<heap.size())
				 {
					 if(((2*i)==(heap.size()-1))||heap.get(2*i).distance<=heap.get(2*i+1).distance)
					 {
						j=2*i; 
					 }
					 else
					 {
						 j=2*i+1;
					 }
					 if(heap.get(j).distance<heap.get(i).distance)
					 {
						 HeapNode temp=heap.get(j);
						 
						 heap.add(j,heap.get(i));
						 heap.remove(j+1);
						 heap.get(j).pos=j;
						 
						 heap.add(i,temp);
						 heap.remove(i+1);
						 heap.get(i).pos=i;
						 
						 i=j;
					 }
					 else
					 {
						 break;
					 }
				 }
			 }
			 
			 public void decreaseKey(HeapNode node, Double distance)
			 {
				node.distance=distance;
				heapifyUp(node.pos);
			 }
			
			public void printNodeStore()
			{
				for(int i: nodeStore.keySet())
				{
					System.out.println("KeyValue - "+i+" Node Id - "+nodeStore.get(i).id+" Position - "+nodeStore.get(i).pos);
				}
			}
			public void printHeap()
			{
				for(HeapNode i: heap)
				{
					if(i!=null)
					{
						System.out.println(i.id+" - "+i.distance);
					}
					
				}
			//	System.out.println("Size - "+heap.size());
			}
			
		
		}
		
		class GraphNode
		{
			int nodeID;
			double weight;
			public GraphNode(int nodeID, double weight)
			{
				this.nodeID=nodeID;
				this.weight=weight;
			}
		}
		
		class Graph
		{
			Map<Integer,ArrayList<GraphNode>> graph;
			int vertexCount;
			int edgeCount;
			
			public Graph(int nodeCount, int edgeCount)
			{
				this.vertexCount=nodeCount;
				this.edgeCount=edgeCount;
				graph = new HashMap<Integer, ArrayList<GraphNode>>();
			}
			
			public void buildGraph(String input)
			{
				HeapNode node = null;
				StringTokenizer st = new StringTokenizer(input);
				while (st.hasMoreTokens()) {
					String token1 = st.nextToken();
					int val1= Integer.parseInt(token1);
					String token2 = st.nextToken();
					int val2 = Integer.parseInt(token2);
					String token3 = st.nextToken();
					double val3 = Double.parseDouble(token3);
					if (!graph.containsKey(val1)) {
						graph.put(val1, new ArrayList<GraphNode>());
						graph.get(val1).add(new GraphNode(val2, val3));
					}
					else
					{
						graph.get(val1).add(new GraphNode(val2, val3));
					}
					if (!graph.containsKey(val2)) {
						graph.put(val2, new ArrayList<GraphNode>());
						graph.get(val2).add(new GraphNode(val1, val3));
					}
					else
					{
						graph.get(val2).add(new GraphNode(val1, val3));
					}
				} 
			}
		
			public void print()
			{
				System.out.println(vertexCount+"  "+edgeCount);
				for (int i : graph.keySet()) {
					System.out.print(i);
					for(GraphNode j : graph.get(i))
					{
						System.out.print("--("+j.weight+")--"+j.nodeID);
					}
					System.out.println();
				}
			}
		}
		
		public void runPrims(Graph graphObj)
		{
			int startNode=(int)(Math.random()*100)%verticeCount;
			++startNode;
			double totalWeight=0;
			Heap heap = new Heap();
			Map<Integer,ArrayList<GraphNode>> graph=graphObj.graph;
			List<Integer> discoveredNodes = new ArrayList<Integer>();
			
			for(int i:graph.keySet())
			{
				if(i!=(startNode))
				{
					HeapNode node= new HeapNode();
					node.setId(i);
					node.distance=Double.POSITIVE_INFINITY;
					heap.insert(node);
				}
				else
				{
					HeapNode start= new HeapNode();
					start.setId(startNode);
					start.distance=0;
					heap.insert(start);
				}
			}
			while(discoveredNodes.size()<verticeCount)
			{
				HeapNode currentNode=heap.extractMin();
				discoveredNodes.add(currentNode.id);
				List<GraphNode> currentNeighbours =  graph.get(currentNode.id);
				if(currentNode.parent!=0)
				{
					totalWeight=totalWeight+currentNode.edgeLength;
					
				}
				for(GraphNode neighbour : currentNeighbours)
				{
					if(!discoveredNodes.contains(neighbour.nodeID))
					{
						int vertex = neighbour.nodeID;
						double distance = neighbour.weight;
						distance = neighbour.weight;
						HeapNode node=heap.nodeStore.get(vertex);
						if(distance<node.distance)
						{
							node.edgeLength= neighbour.weight;
							node.parent=currentNode.id;
							heap.decreaseKey(node,distance);
						}
					}
				}
			}
			
			
			try {
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF8"));
				int totWeight = (int)totalWeight;
				out.write(totWeight+"\n");
				for(int i : heap.nodeStore.keySet())
				{
					if(i!=startNode)
					{
						int edgeLen=(int)heap.nodeStore.get(i).edgeLength;
						out.write(heap.nodeStore.get(i).id+" "+heap.nodeStore.get(i).parent+" "+edgeLen+"\n");
					}	
				}
				out.close();
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MST_Rohit_Sebastian_50204806 prims2 = new MST_Rohit_Sebastian_50204806();
		Graph graph=null ;
		try {
			String str;
			int count=0;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
			while ((str = in.readLine()) != null) {
				++count;
				if (count==1) {
					StringTokenizer st = new StringTokenizer(str);
					int vertexCount = Integer.parseInt(st.nextToken());
					int edgeCount = Integer.parseInt(st.nextToken());
					graph=prims2.new Graph(vertexCount,edgeCount);
				}
				else if (graph!=null) {
					graph.buildGraph(str);
				}	
			}
			prims2.verticeCount= graph.vertexCount;
			prims2.edgeCount=graph.edgeCount;
			prims2.runPrims(graph);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
