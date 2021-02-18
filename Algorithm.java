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
	private List<List<Integer>> possibleSSetCombinations = new ArrayList<>();
	private List<Integer> sSubSet = new ArrayList<>();
	private List<List<Integer>> sSubSets = new ArrayList<>();
	private List<List<Integer>> sSets = new ArrayList<>();
	private int numberOfPartitions;
	private int origin;
	private int destiny;
	private int element;
		
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
	
	private void cleanSSets() {
		this.sSets = new ArrayList<>();
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
		this.cleanSSubSet();
	}

	private int calculateMaxFlow() {
		int maxFlow = 0;
		for(int i = 1; i < this.vertexes.size() - 1; i++) {
			this.generatePartitionsOfS(i);
			this.partitionsUnion(i);
						
			for(int j = 0; j < this.sSets.size(); j++) {
				List<Integer> sVertexes = this.sSets.get(j);
				List<List<Integer>> cuttingEdges = this.cuttingEdges(sVertexes);
				List<Boolean> cuttingEdgesAreFoward = this.cuttingEdgesAreFoward(sVertexes, cuttingEdges);
				
				if(this.itsAMinimumCut(cuttingEdges, cuttingEdgesAreFoward)) {
					
					maxFlow = this.sCapacity(cuttingEdges, cuttingEdgesAreFoward);
					
					System.out.println("this is S " + sVertexes);
					
					System.out.println("this is the maxFlow " + maxFlow);
				}
			}
			
			this.cleanSSets();
		}
		
		return maxFlow;
	}
	
	private int sCapacity(List<List<Integer>> cuttingEdges , List<Boolean> cuttingEdgesAreFoward) {
		int totalCapacity = 0;
		
		for(int j = 0; j < cuttingEdges.size(); j++) {
			if(cuttingEdgesAreFoward.get(j)) {
				List<Integer> edge = cuttingEdges.get(j);
				int capacity = this.flowsAndCapacities.get(this.edges.indexOf(edge)).get(1);
				totalCapacity += capacity;
			}
		}
		
		return totalCapacity;
	}
	
	private void generatePartitionsOfS(int size) {
		if(size == 1) {
			List<Integer> origin = new ArrayList<>();
			origin.add(this.origin);
			this.sSets.add(origin);
		}else {
			List<List<Integer>> originEdges = this.originEdges();
			
			List<List<Integer>> partitions = this.generatingPartitions(size, originEdges.size());
					
			for(int i = 0; i < partitions.size(); i++) {
				this.blockadesStatus.set(this.vertexes.indexOf(this.origin), true);
				
				boolean isFirstPositiveEdge = true;
				
				for(int j = 0; j < originEdges.size(); j++) {
					int partitionValue = partitions.get(i).get(j);
					
					int subSetSize = 0;
					
					if(partitionValue > 0 && isFirstPositiveEdge) {
						subSetSize = partitions.get(i).get(j) - 1;
						isFirstPositiveEdge = false;
					}else {
						subSetSize = partitions.get(i).get(j);
					}
					
					
					if(subSetSize > 0) {
						while(true) {
							this.cleanSSubSet();
							this.cleanVisitsStatus();
							
							this.generateSubSetS(subSetSize, originEdges.get(j).get(1));
							
							boolean itsAValidSet = this.itsAValidSet(subSetSize);
							
							if(itsAValidSet) {
								int partitionNumber = i;
								this.sSubSet.add(0, partitionNumber);
								this.sSubSets.add(sSubSet);								
								this.blockadesStatus.set(this.vertexes.indexOf(this.sSubSet.get(this.sSubSet.size() - 1)), true);
							}
							
							if(this.sSubSet.size() == 1 || (itsAValidSet && this.sSubSet.size() == 2)) {
								this.blockadeAllVisitedPoints();
								break;
							}
						}
					}
				}
				
				this.cleanEverything();
			}
			
			
		}
	}
	
	private boolean itsAValidSet(int quantity) {
		if(this.sSubSet.size() == quantity) {
			return true;
		}
		
		return false;
	}
	
	private void generateSubSetS(int quantity, int vertex) {
		this.sSubSet.add(vertex);	
		this.visitsStatus.set(this.vertexes.indexOf(vertex), true);
		
		quantity--;
		
		if(quantity > 0) {
			List<Integer> adjacentsVertexes = new ArrayList<>();
			
			for(int i = 0; i < this.edges.size(); i++) {
				if(this.edges.get(i).indexOf(vertex) != -1) {
					int adjacentVertex = this.adjacentVertex(this.edges.get(i), vertex);
					
					boolean wasVisited = this.visitsStatus.get(this.vertexes.indexOf(adjacentVertex));
					boolean isBlocked = this.blockadesStatus.get(this.vertexes.indexOf(adjacentVertex));
					
					if(adjacentVertex != this.destiny && !isBlocked && !wasVisited) {
						adjacentsVertexes.add(adjacentVertex);
					}
				}				
			}
			
			if(adjacentsVertexes.size() > 0) {
				this.generateSubSetS(quantity, adjacentsVertexes.get(0));
			}else {
				this.blockadesStatus.set(this.vertexes.indexOf(vertex), true);
			}
			
		}
	}
	
	private boolean itsAMinimumCut(List<List<Integer>> cuttingEdges, List<Boolean> cuttingEdgesAreFoward) {
		for(int i = 0; i < cuttingEdgesAreFoward.size(); i++) {
			if(!this.isSaturated(cuttingEdges.get(i), cuttingEdgesAreFoward.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	private List<Boolean> cuttingEdgesAreFoward(List<Integer> sVertexes, List<List<Integer>> cuttingEdges) {
		List<Boolean> cuttingEdgesAreFoward = new ArrayList<>();
		
		for(int i = 0; i < cuttingEdges.size(); i++) {
			boolean isFoward = false;
			
			for(int j = 0; j < sVertexes.size(); j++) {
				if(cuttingEdges.get(i).get(0) == sVertexes.get(j)) {
					isFoward = true;
				}
			}
			
			if(isFoward) {
				cuttingEdgesAreFoward.add(true);
			}else {
				cuttingEdgesAreFoward.add(false);
			}
		}
		
		return cuttingEdgesAreFoward;
	}
	
	private List<List<Integer>> cuttingEdges(List<Integer> sVertexes){
		List<List<Integer>> cuttingEdges = new ArrayList<>();
		List<Integer> sBarVertexes = new ArrayList<>(); 
		
		for(int i = 0; i < this.vertexes.size(); i++) {
			if(sVertexes.indexOf(this.vertexes.get(i)) == -1) {
				sBarVertexes.add(this.vertexes.get(i));
			}
		}
		
		for(int i = 0; i < sVertexes.size(); i++) {
			for(int j = 0; j < this.edges.size(); j++) {
				for(int k = 0; k < sBarVertexes.size(); k++) {
					if((this.edges.get(j).get(0) == sVertexes.get(i) && this.edges.get(j).get(1) == sBarVertexes.get(k))
						|| (this.edges.get(j).get(1) == sVertexes.get(i) && this.edges.get(j).get(0) == sBarVertexes.get(k))) {
						cuttingEdges.add(this.edges.get(j));
					}
				}
			}
		}
		
		return cuttingEdges;
	}

	private void cleanSSubSet() {
		this.sSubSet = new ArrayList<>();
	}
	
	private void blockadeAllVisitedPoints() {
		for(int i = 0; i < this.visitsStatus.size(); i++) {
			if(this.visitsStatus.get(i)) {
				this.blockadesStatus.set(i, true);
			}
		}
	}
	
	private List<List<Integer>> generatingPartitions(int number, int maxNumberOfPartitions){
		List<List<Integer>> partitions = new ArrayList<>();
		List<Integer> partition = new ArrayList<>();
		
		for(int i = 0; i < maxNumberOfPartitions; i++) {
			partition.add(0);
		}
		
		int sumOfElements = 0;
		
		while(true) {
			partition.set(partition.size() - 1, partition.get(partition.size() - 1) + 1);
					
			for(int i = partition.size() - 1; i > -1; i--) {
				if(partition.get(i) > number && i > 0) {
					partition.set(i, 0);
					partition.set(i - 1, partition.get(i - 1) + 1);
				}		
				
				sumOfElements += partition.get(i);
			}
			
			if(sumOfElements == number) {
				List<Integer> clonedPartition = new ArrayList<>(partition);
				partitions.add(clonedPartition);
			}
			
			if(sumOfElements == number * maxNumberOfPartitions) {
				partition = new ArrayList<>();
				break;
			}else {
				sumOfElements = 0;
			}
		}
		
		this.numberOfPartitions = partitions.size();
		
		return partitions;
	}
	
	private void partitionsUnion(int size) {	
		for(int i = 0; i < this.numberOfPartitions; i++) {
			List<List<Integer>> samePartitionSubSets = new ArrayList<>();
			
			for(int j = 0; j < this.sSubSets.size(); j++) {
				if(this.sSubSets.get(j).get(0) == i) {
					samePartitionSubSets.add(new ArrayList<>(this.sSubSets.get(j)));
				}
			}
			
			int sum = 0;
			
			for(int k = 0; k < samePartitionSubSets.size(); k++) {
				samePartitionSubSets.get(k).remove(0);
				sum += samePartitionSubSets.get(k).size();
			}
			
			if(sum == size - 1) {
				List<Integer> sSet = new ArrayList<>();
				sSet.add(this.origin);
				
				for(int w = 0; w < samePartitionSubSets.size(); w++) {
					sSet.addAll(samePartitionSubSets.get(w));
				}
				
				if(this.itsANewSet(sSet)) {
					this.sSets.add(sSet);
				}				
			}
		}
	}
	
	public boolean itsANewSet(List<Integer> sSet) {
		if(this.sSets.size() == 0) {
			return true;
		}else {
			boolean isNew = true;
			
			for(int i = 0; i < this.sSets.size(); i++) {
				boolean isTheSame = true;
				
				for(int j = 0; j < sSet.size(); j++) {					
					if(this.sSets.get(i).indexOf(sSet.get(j)) == -1) {
						isTheSame = false;
					}
				}
				
				isNew = isNew && !isTheSame;
			}
			
			return isNew;
		}
		
	}
	
	
}
