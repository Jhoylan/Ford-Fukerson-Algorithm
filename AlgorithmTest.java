package fordFukerson;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AlgorithmTest {
	@Test
	void maxFlowInternetTest() throws InterruptedException {
		Algorithm fordFukerson = new Algorithm();
		
		List<Integer> edge1 = new ArrayList<>();
		edge1.add(1);
		edge1.add(2);
		
		List<Integer> edge2 = new ArrayList<>();
		edge2.add(1);
		edge2.add(3);
		
		List<Integer> edge3 = new ArrayList<>();
		edge3.add(2);
		edge3.add(3);
		
		List<Integer> edge4 = new ArrayList<>();
		edge4.add(2);
		edge4.add(4);
		
		List<Integer> edge5 = new ArrayList<>();
		edge5.add(2);
		edge5.add(5);
		
		List<Integer> edge6 = new ArrayList<>();
		edge6.add(3);
		edge6.add(5);
		
		List<Integer> edge7 = new ArrayList<>();
		edge7.add(4);
		edge7.add(6);
		
		List<Integer> edge8 = new ArrayList<>();
		edge8.add(5);
		edge8.add(4);
		
		List<Integer> edge9 = new ArrayList<>();
		edge9.add(5);
		edge9.add(6);
		
		fordFukerson.addEdge(edge1);
		fordFukerson.addEdge(edge2);
		fordFukerson.addEdge(edge3);
		fordFukerson.addEdge(edge4);
		fordFukerson.addEdge(edge5);
		fordFukerson.addEdge(edge6);
		fordFukerson.addEdge(edge7);
		fordFukerson.addEdge(edge8);
		fordFukerson.addEdge(edge9);
		
		fordFukerson.setCapacity(10, edge1);
		fordFukerson.setCapacity(10, edge2);
		fordFukerson.setCapacity(2, edge3);
		fordFukerson.setCapacity(4, edge4);
		fordFukerson.setCapacity(8, edge5);
		fordFukerson.setCapacity(9, edge6);		
		fordFukerson.setCapacity(10, edge7);
		fordFukerson.setCapacity(6, edge8);
		fordFukerson.setCapacity(10, edge9);
		
		Assertions.assertEquals(19, fordFukerson.maxFlow());
	}
	
	@Test
	void maxFlowGoldbargTest () {
		Algorithm fordFukerson = new Algorithm();
		
		List<Integer> edge1 = new ArrayList<>();
		edge1.add(1);
		edge1.add(2);
		
		List<Integer> edge2 = new ArrayList<>();
		edge2.add(1);
		edge2.add(4);
		
		List<Integer> edge3 = new ArrayList<>();
		edge3.add(1);
		edge3.add(7);
		
		List<Integer> edge4 = new ArrayList<>();
		edge4.add(2);
		edge4.add(5);
		
		List<Integer> edge5 = new ArrayList<>();
		edge5.add(3);
		edge5.add(2);
		
		List<Integer> edge6 = new ArrayList<>();
		edge6.add(3);
		edge6.add(9);
		
		List<Integer> edge7 = new ArrayList<>();
		edge7.add(4);
		edge7.add(2);
		
		List<Integer> edge8 = new ArrayList<>();
		edge8.add(4);
		edge8.add(3);
		
		List<Integer> edge9 = new ArrayList<>();
		edge9.add(4);
		edge9.add(5);
		
		List<Integer> edge10 = new ArrayList<>();
		edge10.add(5);
		edge10.add(6);
		
		List<Integer> edge11 = new ArrayList<>();
		edge11.add(5);
		edge11.add(7);
		
		List<Integer> edge12 = new ArrayList<>();
		edge12.add(6);
		edge12.add(3);
		
		List<Integer> edge13 = new ArrayList<>();
		edge13.add(6);
		edge13.add(8);
		
		List<Integer> edge14 = new ArrayList<>();
		edge14.add(6);
		edge14.add(9);
		
		List<Integer> edge15 = new ArrayList<>();
		edge15.add(7);
		edge15.add(4);
		
		List<Integer> edge16 = new ArrayList<>();
		edge16.add(7);
		edge16.add(8);
		
		List<Integer> edge17 = new ArrayList<>();
		edge17.add(8);
		edge17.add(4);
		
		List<Integer> edge18 = new ArrayList<>();
		edge18.add(8);
		edge18.add(9);
		
		fordFukerson.addEdge(edge1);
		fordFukerson.addEdge(edge2);
		fordFukerson.addEdge(edge3);
		fordFukerson.addEdge(edge4);
		fordFukerson.addEdge(edge5);
		fordFukerson.addEdge(edge6);
		fordFukerson.addEdge(edge7);
		fordFukerson.addEdge(edge8);
		fordFukerson.addEdge(edge9);
		fordFukerson.addEdge(edge10);
		fordFukerson.addEdge(edge11);
		fordFukerson.addEdge(edge12);
		fordFukerson.addEdge(edge13);
		fordFukerson.addEdge(edge14);
		fordFukerson.addEdge(edge15);
		fordFukerson.addEdge(edge16);
		fordFukerson.addEdge(edge17);
		fordFukerson.addEdge(edge18);
		
		fordFukerson.setCapacity(10, edge1);
		fordFukerson.setCapacity(10, edge2);
		fordFukerson.setCapacity(10, edge3);
		fordFukerson.setCapacity(12, edge4);
		fordFukerson.setCapacity(15, edge5);		
		fordFukerson.setCapacity(10, edge6);
		fordFukerson.setCapacity(8, edge7);
		fordFukerson.setCapacity(10, edge8);
		fordFukerson.setCapacity(10, edge9);
		fordFukerson.setCapacity(20, edge10);
		fordFukerson.setCapacity(10, edge11);
		fordFukerson.setCapacity(10, edge12);
		fordFukerson.setCapacity(10, edge13);
		fordFukerson.setCapacity(7, edge14);		
		fordFukerson.setCapacity(10, edge15);
		fordFukerson.setCapacity(10, edge16);
		fordFukerson.setCapacity(10, edge17);
		fordFukerson.setCapacity(13, edge18);
		
		Assertions.assertEquals(30, fordFukerson.maxFlow());
	}
	
	@Test
	void hopelessPathTest() throws InterruptedException {
		Algorithm fordFukerson = new Algorithm();
		
		List<Integer> edge1 = new ArrayList<>();
		edge1.add(1);
		edge1.add(2);
		
		List<Integer> edge2 = new ArrayList<>();
		edge2.add(1);
		edge2.add(3);
		
		List<Integer> edge3 = new ArrayList<>();
		edge3.add(1);
		edge3.add(6);
		
		List<Integer> edge4 = new ArrayList<>();
		edge4.add(2);
		edge4.add(7);
		
		List<Integer> edge5 = new ArrayList<>();
		edge5.add(3);
		edge5.add(4);
		
		List<Integer> edge6 = new ArrayList<>();
		edge6.add(4);
		edge6.add(5);
		
		List<Integer> edge7 = new ArrayList<>();
		edge7.add(5);
		edge7.add(2);
		
		List<Integer> edge8 = new ArrayList<>();
		edge8.add(6);
		edge8.add(7);
		
		fordFukerson.addEdge(edge1);
		fordFukerson.addEdge(edge2);
		fordFukerson.addEdge(edge3);
		fordFukerson.addEdge(edge4);
		fordFukerson.addEdge(edge5);
		fordFukerson.addEdge(edge6);
		fordFukerson.addEdge(edge7);
		fordFukerson.addEdge(edge8);
		
		fordFukerson.setCapacity(10, edge1);
		fordFukerson.setCapacity(10, edge2);
		fordFukerson.setCapacity(10, edge3);
		fordFukerson.setCapacity(10, edge4);
		fordFukerson.setCapacity(10, edge5);		
		fordFukerson.setCapacity(10, edge6);
		fordFukerson.setCapacity(10, edge7);
		fordFukerson.setCapacity(10, edge8);
		
		Assertions.assertEquals(20, fordFukerson.maxFlow());
		
	}
	
}
