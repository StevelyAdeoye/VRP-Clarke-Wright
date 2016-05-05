import java.util.ArrayList;


public class Route {

	public ArrayList<Customer> listCustomer;

	public int capacityAvailable;
	public int capacity;

	public Route()
	{
		this.listCustomer= new ArrayList<Customer>();
		//this.capacityAvailable=50;


	}

	public boolean hasCapacity(int requirement){
		if(this.capacityAvailable-requirement>=0)
			return true;
		else
			return false;
	}

	public void add(Customer c){
		this.listCustomer.add(c);
		this.capacityAvailable =(this.capacityAvailable - c.c);
		this.capacity =this.capacity + c.c;

	}



	public Customer lastDelivery(){
		int las = this.listCustomer.size()-1;
		return listCustomer.get(las);

	}


	public Customer first(){
		return this.listCustomer.get(0);

	}


	public int goods(){
		return this.capacity;

	}
	public void addToStart(Customer c){
		this.listCustomer.add(0, c);

	}

	public void merge(Route y)
	{
		// add each customer from y.listCustomer to this.listCustomer 



		//		for(Customer C : y.listCustomer){
		//			
		//			this.listCustomer.add(C);
		//		}

		for(Customer customer : y.listCustomer){

			this.listCustomer.add(customer);

		}

		//	this.listCustomer.addAll(y.listCustomer);



		this.capacity+=y.capacity ;


	}


}


