//import java.util.*;
//import java.io.*;
//import java.awt.geom.Point2D;
//
//public class VRSolution {
//	public static VRProblem prob;
//	public List<List<Customer>>soln;
//	public VRSolution(VRProblem problem){
//		this.prob = problem;
//	}
//
//
//	//The dumb solver adds one route per customer
//	public void oneRoutePerCustomerSolution(){
//		this.soln = new ArrayList<List<Customer>>();
//		for(Customer c:prob.customers){
//			ArrayList<Customer> route = new ArrayList<Customer>();
//			route.add(c);
//			soln.add(route);
//		}
//	}
//
//
//	//Students should implement another solution
//
//	ArrayList<Saving> savings = new ArrayList<Saving>();
//	int capacity;
//	int current;
//
//	public void MyNewSolver(){
//		
//		ArrayList<Route> routes = new ArrayList<Route>();
//		ArrayList<Customer> cInRoutes = new ArrayList<Customer>();
//		SavingNode();
//		capacity = prob.depot.c;
//		this.soln = new ArrayList<List<Customer>>();
//		ArrayList<Customer> routes1 = new ArrayList<Customer>();
//
//		for(Saving sn: savings){
//			if(!routes1.contains(sn.from)&!routes1.contains(sn.to)){
//				if(current<=capacity){
//					routes1.add(sn.from);
//					routes1.add(sn.to);
//					routes1.add(newR);
//				}else{
//					current = 0;
//					break;
//				}
//			}
//		}
//	}
//
//
//
//
//
//		public void SavingNode(){
//			for(int i=0;i<prob.size();i++){
//				for(int j=0;j<prob.size();j++){
//					if (i != j){
//						Customer ci = prob.customers.get(i);
//						Customer cj = prob.customers.get(j);
//
//						double saving = (prob.depot.distance(ci)) + prob.depot.distance(cj)-ci.distance(cj);
//						Saving s = new Saving(saving,ci,cj);
//						savings.add(s);
//					}
//				}
//			}
//			//Collections.sort(savings);
//		}
//
//
//		//////////////////////////////////////////////////////////////////////////////////////////
//		//Calculate the total journey
//		public double solnCost(){
//			double cost = 0;
//			for(List<Customer>route:soln){
//				Customer prev = this.prob.depot;
//				for (Customer c:route){
//					cost += prev.distance(c);
//					prev = c;
//				}
//				//Add the cost of returning to the depot
//				cost += prev.distance(this.prob.depot);
//			}
//			return cost;
//		}
//		public Boolean verify(){
//			//Check that no route exceeds capacity
//			Boolean okSoFar = true;
//			for(List<Customer> route : soln){
//				//Start the spare capacity at
//				int total = 0;
//				for(Customer c:route)
//					total += c.c;
//				if (total>prob.depot.c){
//					System.out.printf("********FAIL Route starting %s is over capacity %d\n",
//							route.get(0),
//							total
//							);
//					okSoFar = false;
//				}
//			}
//			//Check that we keep the customer satisfied
//			//Check that every customer is visited and the correct amount is picked up
//			Map<String,Integer> reqd = new HashMap<String,Integer>();
//			for(Customer c:this.prob.customers){
//				String address = String.format("%fx%f", c.x,c.y);
//				reqd.put(address, c.c);
//			}
//			for(List<Customer> route:this.soln){
//				for(Customer c:route){
//					String address = String.format("%fx%f", c.x,c.y);
//					if (reqd.containsKey(address))
//						reqd.put(address, reqd.get(address)-c.c);
//					else
//						System.out.printf("********FAIL no customer at %s\n",address);
//				}
//			}
//			for(String address:reqd.keySet())
//				if (reqd.get(address)!=0){
//					System.out.printf("********FAIL Customer at %s has %d left over\n",address,reqd.get(address));
//					okSoFar = false;
//				}
//			return okSoFar;
//		}
//
//		public void readIn(String filename) throws Exception{
//			BufferedReader br = new BufferedReader(new FileReader(filename));
//			String s;
//			this.soln = new ArrayList<List<Customer>>();
//			while((s=br.readLine())!=null){
//				ArrayList<Customer> route = new ArrayList<Customer>();
//				String [] xycTriple = s.split(",");
//				for(int i=0;i<xycTriple.length;i+=3)
//					route.add(new Customer(
//							(int)Double.parseDouble(xycTriple[i]),
//							(int)Double.parseDouble(xycTriple[i+1]),
//							(int)Double.parseDouble(xycTriple[i+2])));
//				soln.add(route);
//			}
//			br.close();
//		}
//
//		public void writeSVG(String probFilename,String solnFilename) throws Exception{
//			String[] colors = "chocolate cornflowerblue crimson cyan darkblue darkcyan darkgoldenrod".split(" ");
//			int colIndex = 0;
//			String hdr = 
//					"<?xml version='1.0'?>\n"+
//							"<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' '../../svg11-flat.dtd'>\n"+
//							"<svg width='8cm' height='8cm' viewBox='0 0 500 500' xmlns='http://www.w3.org/2000/svg' version='1.1'>\n";
//			String ftr = "</svg>";
//			StringBuffer psb = new StringBuffer();
//			StringBuffer ssb = new StringBuffer();
//			psb.append(hdr);
//			ssb.append(hdr);
//			for(List<Customer> route:this.soln){
//				ssb.append(String.format("<path d='M%s %s ",this.prob.depot.x,this.prob.depot.y));
//				for(Customer c:route)
//					ssb.append(String.format("L%s %s",c.x,c.y));
//				ssb.append(String.format("z' stroke='%s' fill='none' stroke-width='2'/>\n",
//						colors[colIndex++ % colors.length]));
//			}
//			for(Customer c:this.prob.customers){
//				String disk = String.format(
//						"<g transform='translate(%.0f,%.0f)'>"+
//								"<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>" +
//								"<text text-anchor='middle' y='5'>%d</text>"+
//								"</g>\n", 
//								c.x,c.y,10,c.c);
//				psb.append(disk);
//				ssb.append(disk);
//			}
//			String disk = String.format("<g transform='translate(%.0f,%.0f)'>"+
//					"<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>" +
//					"<text text-anchor='middle' y='5'>%s</text>"+
//					"</g>\n", this.prob.depot.x,this.prob.depot.y,20,"D");
//			psb.append(disk);
//			ssb.append(disk);
//			psb.append(ftr);
//			ssb.append(ftr);
//			PrintStream ppw = new PrintStream(new FileOutputStream(probFilename));
//			PrintStream spw = new PrintStream(new FileOutputStream(solnFilename));
//			ppw.append(psb);
//			spw.append(ssb);
//			ppw.close();
//			spw.close();
//		}
//		public void writeOut(String filename) throws Exception{
//			PrintStream ps = new PrintStream(filename);
//			for(List<Customer> route:this.soln){
//				boolean firstOne = true;
//				for(Customer c:route){
//					if (!firstOne)
//						ps.print(",");
//					firstOne = false;
//					ps.printf("%f,%f,%d",c.x,c.y,c.c);
//				}
//				ps.println();
//			}
//			ps.close();
//		}
//	}
//
//
//
//



import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VRSolution {
	public VRProblem prob;
	public List<List<Customer>>soln;
	public VRSolution(VRProblem problem){
		this.prob = problem;
	}

	//The dumb solver adds one route per customer
	public void oneRoutePerCustomerSolution(){
		this.soln = new ArrayList<List<Customer>>();
		for(Customer c:prob.customers){
			ArrayList<Customer> route = new ArrayList<Customer>();
			route.add(c);
			soln.add(route);
		}
	}

	//Students should implement another solution

	public void BetterSolution(){
		this.soln= new ArrayList<List<Customer>>();
		int i = 0;
		ArrayList<Customer> route = null;
		for(Customer c:prob.customers){
			if (i%2==0){

				route = new ArrayList<Customer>();
				soln.add(route);			
			}	
			route.add(c);	
			i++;	
		}	
	}

	public void BetterSavings(){
		ArrayList<Savings> mysaving = new ArrayList<Savings>();
		ArrayList<Route> routes = new ArrayList<Route>();
		ArrayList<Customer> cus = new ArrayList<Customer>();
		this.soln= new ArrayList<List<Customer>>();
		for (int i=0; i < prob.customers.size(); i++){
			for (int j=0; j < prob.customers.size(); j++){
				if (i != j){
					Customer cj = prob.customers.get(i);
					Customer ci = prob.customers.get(j);
					double saving = (prob.depot.distance(ci) + prob.depot.distance(cj) - ci.distance(cj));
					mysaving.add(new Savings(saving,cj,ci));
				}
			}
		}

		Collections.sort(mysaving);
		
//		     int p=0;
//				for(Savings y: mysaving)
//				{
//					System.out.println(p++ + " " + y.val);
//				}

		for(Savings sn : mysaving) {

			if(!cus.contains(sn.from)&!cus.contains(sn.to)){
				if (sn.from.c + sn.to.c <= 50){
					Route newR = new Route();
					newR.add(sn.from);
					newR.add(sn.to);
					routes.add(newR);
					
					cus.add(sn.to);
					cus.add(sn.from);
				}
			}	else
				if (!cus.contains(sn.to)){
					for(Route route: routes){
						if (route.lastDelivery()== sn.from){
							if (route.hasCapacity(sn.to.c)){
								route.add(sn.to);

								cus.add(sn.to);
								break;
							}
						}


					}   

				}

			if (!cus.contains(sn.from)){
				for(Route route: routes){
					if (route.lastDelivery() == sn.to){
						if (route.hasCapacity(sn.from.c)){
							route.addToStart(sn.from);
							
							cus.add(sn.from);
							break;
						}
					}
				}
			}

			Route merged=null;
			for(Route routeX: routes){
				if (merged != null)break;
				if (routeX.lastDelivery() == sn.from){
					for(Route routeY: routes){
						if (routeY.first() == sn.to){
							if (routeX != routeY){
								if ((routeX.goods() + routeY.goods())<= prob.depot.c)
								{
									routeX.merge(routeY);
									merged= routeY;
									break;
								}
							}
						}
					}
				}
			}
			if (merged != null)
				routes.remove(merged);

		}
		
		for (Customer c : prob.customers) {
			if (!cus.contains(c)){
				Route r = new Route();
				r.add(c);
				routes.add(r);
			}
		}
		
		
		for ( Route r:routes){
			 soln.add(r.listCustomer);
		}

	}



	//Calculate the total journey
	public double solnCost(){
		double cost = 0;
		for(List<Customer>route:soln){
			Customer prev = this.prob.depot;
			for (Customer c:route){
				cost += prev.distance(c);
				prev = c;
			}
			//Add the cost of returning to the depot
			cost += prev.distance(this.prob.depot);
		}
		return cost;
	}
	public Boolean verify(){
		//Check that no route exceeds capacity
		Boolean okSoFar = true;
		for(List<Customer> route : soln){
			//Start the spare capacity at
			int total = 0;
			for(Customer c:route)
				total += c.c;
			if (total>prob.depot.c){
				System.out.printf("********FAIL Route starting %s is over capacity %d\n",
						route.get(0),
						total
						);
				okSoFar = false;
			}
		}
		//Check that we keep the customer satisfied
		//Check that every customer is visited and the correct amount is picked up
		Map<String,Integer> reqd = new HashMap<String,Integer>();
		for(Customer c:this.prob.customers){
			String address = String.format("%fx%f", c.x,c.y);
			reqd.put(address, c.c);
		}
		for(List<Customer> route:this.soln){
			for(Customer c:route){
				String address = String.format("%fx%f", c.x,c.y);
				if (reqd.containsKey(address))
					reqd.put(address, reqd.get(address)-c.c);
				else
					System.out.printf("********FAIL no customer at %s\n",address);
			}
		}
		for(String address:reqd.keySet())
			if (reqd.get(address)!=0){
				System.out.printf("********FAIL Customer at %s has %d left over\n",address,reqd.get(address));
				okSoFar = false;
			}
		return okSoFar;
	}

	public void readIn(String filename) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s;
		this.soln = new ArrayList<List<Customer>>();
		while((s=br.readLine())!=null){
			ArrayList<Customer> route = new ArrayList<Customer>();
			String [] xycTriple = s.split(",");
			for(int i=0;i<xycTriple.length;i+=3)
				route.add(new Customer(
						(int)Double.parseDouble(xycTriple[i]),
						(int)Double.parseDouble(xycTriple[i+1]),
						(int)Double.parseDouble(xycTriple[i+2])));
			soln.add(route);
		}
		br.close();
	}

	public void writeSVG(String probFilename,String solnFilename) throws Exception{
		String[] colors = "chocolate cornflowerblue crimson cyan darkblue darkcyan darkgoldenrod".split(" ");
		int colIndex = 0;
		String hdr = 
				"<?xml version='1.0'?>\n"+
						"<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' '../../svg11-flat.dtd'>\n"+
						"<svg width='8cm' height='8cm' viewBox='0 0 500 500' xmlns='http://www.w3.org/2000/svg' version='1.1'>\n";
		String ftr = "</svg>";
		StringBuffer psb = new StringBuffer();
		StringBuffer ssb = new StringBuffer();
		psb.append(hdr);
		ssb.append(hdr);
		for(List<Customer> route:this.soln){
			ssb.append(String.format("<path d='M%s %s ",this.prob.depot.x,this.prob.depot.y));
			for(Customer c:route)
				ssb.append(String.format("L%s %s",c.x,c.y));
			ssb.append(String.format("z' stroke='%s' fill='none' stroke-width='2'/>\n",
					colors[colIndex++ % colors.length]));
		}
		for(Customer c:this.prob.customers){
			String disk = String.format(
					"<g transform='translate(%.0f,%.0f)'>"+
							"<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>" +
							"<text text-anchor='middle' y='5'>%d</text>"+
							"</g>\n", 
							c.x,c.y,10,c.c);
			psb.append(disk);
			ssb.append(disk);
		}
		String disk = String.format("<g transform='translate(%.0f,%.0f)'>"+
				"<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>" +
				"<text text-anchor='middle' y='5'>%s</text>"+
				"</g>\n", this.prob.depot.x,this.prob.depot.y,20,"D");
		psb.append(disk);
		ssb.append(disk);
		psb.append(ftr);
		ssb.append(ftr);
		PrintStream ppw = new PrintStream(new FileOutputStream(probFilename));
		PrintStream spw = new PrintStream(new FileOutputStream(solnFilename));
		ppw.append(psb);
		spw.append(ssb);
		ppw.close();
		spw.close();
	}
	public void writeOut(String filename) throws Exception{
		PrintStream ps = new PrintStream(filename);
		for(List<Customer> route:this.soln){
			boolean firstOne = true;
			for(Customer c:route){
				if (!firstOne)
					ps.print(",");
				firstOne = false;
				ps.printf("%f,%f,%d",c.x,c.y,c.c);
			}
			ps.println();
		}
		ps.close();
	}
}
