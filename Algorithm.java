package fordFukerson;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
	private List<List<Integer>> edges = new ArrayList<>();
	private List<List<Integer>> flowsAndCapacities = new ArrayList<>();
	private List<List<Integer>> path = new ArrayList<>();
	private List<Integer> vertexes = new ArrayList<>();
	private List<Boolean> visitsStatus = new ArrayList<>();
	private List<Boolean> blockadesStatus = new ArrayList<>();
	private int origin;
	private int destiny;
		
	public int maxFlow() {
		this.definingOriginAndDestiny();
		this.iniatilizingVertexesAndTheirStatus();
		
		List<List<Integer>> originEdges = this.originEdges();
		
		for(int i = 0; i < originEdges.size(); i++) {
			while(true) {
				this.generatePath(originEdges.get(i), this.origin);
				
				if(this.itsAValidPath()) {
					this.addFlow(this.bottleNeckFlow());					
					this.cleanEverything();
				}else if(this.path.size() > 1) {
					this.cleanVisitsStatus();
					this.cleanPath();
				}else {
					this.cleanEverything();
					break;
				}
			}			
		}
		
		return this.calculateMaxFlow();
	}

	public void addEdge(List<Integer> edge) {
		edges.add(edge);
		
		List<Integer> flowAndCapacity = new ArrayList<>();
		flowAndCapacity.add(0);
		flowAndCapacity.add(0);
		
		this.flowsAndCapacities.add(flowAndCapacity);
	}
	
	private void setFlow(int flow, List<Integer> edge) {
		this.flowsAndCapacities.get(this.edges.indexOf(edge)).set(0, flow);
	}
	
	public void setCapacity(int capacity, List<Integer> edge) {
		this.flowsAndCapacities.get(this.edges.indexOf(edge)).set(1, capacity);
	}

	private void addFlow(int flow) {
		int actualFlowOfOringEdge = this.getFlow(this.path.get(0));
		this.setFlow(flow + actualFlowOfOringEdge, this.path.get(0));
		int referenceVertex = 0;
		boolean previousEdgeIsFoward = true;
		
		for(int i = 1; i < this.path.size(); i++) {
			if(previousEdgeIsFoward) {
				referenceVertex =  this.path.get(i - 1).get(1);
			}else {
				referenceVertex =  this.path.get(i - 1).get(0);
			}
			
			boolean isFoward = this.isFoward(this.path.get(i), referenceVertex);
			
			int actualFlow = this.getFlow(this.path.get(i));
			
			if(isFoward) {
				this.setFlow(actualFlow + flow, this.path.get(i));
				referenceVertex =  this.path.get(i - 1).get(1);
				previousEdgeIsFoward = true;
			}else {
				this.setFlow(actualFlow - flow, this.path.get(i));
				referenceVertex = this.path.get(i - 1).get(0);
				previousEdgeIsFoward = false;
			}
		}
	}
	
	private int bottleNeckFlow() {
		int bottleNeckFlow = this.getCapacity(this.path.get(0))	- this.getFlow(this.path.get(0));
		int referenceVertex = 0;
		boolean previousEdgeIsFoward = true;
		
		for(int i = 1; i < this.path.size(); i++) {
			if(previousEdgeIsFoward) {
				referenceVertex =  this.path.get(i - 1).get(1);
			}else {
				referenceVertex =  this.path.get(i - 1).get(0);
			}
			
			boolean isFoward = this.isFoward(this.path.get(i), referenceVertex);
			
			int flow = this.getFlow(this.path.get(i));
			
			if(isFoward) {
				int capacity = this.getCapacity(this.path.get(i));
								
				if(capacity - flow < bottleNeckFlow) {
					bottleNeckFlow = capacity - flow;
				}
				
				previousEdgeIsFoward = true;
			}else {
				if(flow < bottleNeckFlow) {
					bottleNeckFlow = flow;
				}
				
				previousEdgeIsFoward = false;
			}
			
		}
		
		return bottleNeckFlow;	
	}

	private void generatePath(List<Integer> edge, int vertex){
		int adjacentVertex = this.adjacentVertex(edge, vertex);
			
		boolean edgeIsSaturated = this.isSaturated(edge, this.isFoward(edge, vertex));
		
		if(!edgeIsSaturated) {
			this.path.add(edge);
			
			this.visitsStatus.set(this.vertexes.indexOf(vertex), true);
			this.visitsStatus.set(this.vertexes.indexOf(adjacentVertex), true);
		}	
		
		if(!edgeIsSaturated && this.adjacentVertex(edge, vertex) != this.destiny) {
						
			List<List<Integer>> adjacentEdges = this.adjacentEdges(edge, vertex);
			
			boolean isCornered = true;
			
			for(int i = 0; i < adjacentEdges.size(); i++) {
				if(this.itsAPossibleEdge(adjacentEdges.get(i), adjacentVertex)) {
					this.generatePath(adjacentEdges.get(i), adjacentVertex);
					isCornered = false;
					break;
				}
			}
			
			if(isCornered) {
				this.blockadesStatus.set(this.vertexes.indexOf(adjacentVertex), true);
			}
		}
	} 
	
	private boolean itsAValidPath() {
		if(this.path.size() > 0) {
			return this.path.get(this.path.size() - 1).get(1) == this.destiny;			
		}	
		
		return false;
	}
	
	public boolean itsAPossibleEdge(List<Integer> edge, int vertex) {
		return !this.isSaturated(edge, this.isFoward(edge, vertex)) 
				&& !this.visitsStatus.get(this.vertexes.indexOf(this.adjacentVertex(edge, vertex)))
				&& !this.blockadesStatus.get(this.vertexes.indexOf(this.adjacentVertex(edge, vertex)));
	}
	
	private List<List<Integer>> adjacentEdges(List<Integer> edge, int vertex){
		List<List<Integer>> adjacentEdges = new ArrayList<>();

		int adjacentVertex = this.adjacentVertex(edge, vertex);
		
		for(int i = 0; i < this.edges.size(); i++) {
			if(this.edges.get(i).indexOf(adjacentVertex) != -1 && !this.edges.get(i).equals(edge)) {
				adjacentEdges.add(this.edges.get(i));
			}
		}
		
		return adjacentEdges;
	}

	private boolean isFoward(List<Integer> edge, int vertex) {
		return edge.get(0) == vertex;			
	}

	private int adjacentVertex(List<Integer> edge, int vertex) {
			
		if(edge.get(0) == vertex) {
			return edge.get(1);
		}
		
		return edge.get(0);
	}
	
	private void iniatilizingVertexesAndTheirStatus() {
		for(int i = 0; i < this.edges.size(); i++) {
			if(this.vertexes.indexOf(this.edges.get(i).get(0)) == -1) {
				this.vertexes.add(this.edges.get(i).get(0));
				this.visitsStatus.add(false);
				this.blockadesStatus.add(false);
			}
			
			if(this.vertexes.indexOf(this.edges.get(i).get(1)) == -1) {
				this.vertexes.add(this.edges.get(i).get(1));
				this.visitsStatus.add(false);
				this.blockadesStatus.add(false);
			}
		}
	}
	
	private boolean isSaturated(List<Integer> edge, boolean isFoward) {
		int flow = this.getFlow(edge);
		
		if(isFoward) {
			
			int capacity = this.getCapacity(edge);
			
			if(capacity - flow == 0) {
				return true;
			}
			
			return false;
		}else {
					
			if(flow == 0) {
				return true;
			}
			
			return false;
		}
	}

	private void definingOriginAndDestiny() {
		for(int i = 0; i < this.edges.size(); i++) {
			boolean isOrigin = true;
			boolean isDestiny = true;
			
			int possibleOrigin = this.edges.get(i).get(0);
			int possibleDestiny = this.edges.get(i).get(1);
			
			for(int j = 0; j < this.edges.size(); j++) {
				if(this.edges.get(j).get(1) == possibleOrigin) {
					isOrigin = false;
				}
				
				if(this.edges.get(j).get(0) == possibleDestiny) {
					isDestiny = false;
				}
			}
			
			if(isOrigin) {
				this.origin = possibleOrigin;
			}
			
			if(isDestiny) {
				this.destiny = possibleDestiny;
			}
		}
	}
	
	private void cleanBlockades() {
		for(int i = 0; i < this.blockadesStatus.size(); i++) {
			this.blockadesStatus.set(i, false);
		}
	}
	
	private void cleanVisitsStatus() {
		for(int i = 0; i < this.visitsStatus.size(); i++) {
			this.visitsStatus.set(i, false);
		}
	}

	private List<List<Integer>> originEdges(){
		List<List<Integer>> originEdges = new ArrayList<>();
		
		for(int i = 0; i < this.edges.size(); i++) {
			if(this.edges.get(i).get(0) == this.origin) {
				originEdges.add(this.edges.get(i));
			}
		}
		
		return originEdges;
	}
	
	private int calculateMaxFlow() {
		List<List<Integer>> originEdges = this.originEdges();
		
		int maxFlow = 0;
		
		for(int i = 0; i < originEdges.size(); i++) {
			maxFlow += this.flowsAndCapacities.get(this.edges.indexOf(originEdges.get(i))).get(0);
		}
		
		return maxFlow;
	}

	private int getCapacity(List<Integer> edge) {
		return this.flowsAndCapacities.get(this.edges.indexOf(edge)).get(1);
	}
	
	private int getFlow(List<Integer> edge) {
		return this.flowsAndCapacities.get(this.edges.indexOf(edge)).get(0);
	}
	
	private void cleanPath() {
		this.path = new ArrayList<>();
	}

	private void cleanEverything() {
		this.cleanVisitsStatus();
		this.cleanBlockades();
		this.cleanPath();
	}
}
