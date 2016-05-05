


public class Saving implements Comparable<Savings>{

	Customer from;
	Customer to;
	double val;


	public Saving(double v,Customer f,Customer t){
		this.val = v;
		this.from = f;
		this.to = t;
	}
	
	public int compareTo(Savings o) {
		if(o.val == this.val)
			return 0;
		else if (o.val > this.val)
			return 1;
		else
			return -1;
	}
}

