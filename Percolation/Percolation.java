
public class Percolation {
	private boolean[][] lattice;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uftop;
	private int count;
	// create N-by-N grid, with all sites blocked
	public Percolation(int N){
		if(N <= 0 )
			throw new IllegalArgumentException();
		count = N;
		lattice = new boolean[N+1][N+1];
		uftop = new WeightedQuickUnionUF(N*N+1);
		uf = new WeightedQuickUnionUF(N*N+2);
		for(int i = 0 ; i <= N ; i++){
            for(int j = 0;j <= N ; j++){  
            	lattice[i][j] = false;  
            }  
        }  
	}           
	
	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		validate(i,j);
		int index = xyTo1D(i, j);
		lattice[i][j] = true;
		if(i == 1){
			uf.union(index, 0);
			uftop.union(index, 0);
		}
		if(i == count)
			uf.union(index, count*count+1);
		
		if((i+1) <= count && lattice[i+1][j]){
			uf.union(index, xyTo1D(i+1, j));
			uftop.union(index, xyTo1D(i+1, j));
		}		
		if((i-1) > 0 && lattice[i-1][j]){
			uf.union(index, xyTo1D(i-1, j));
			uftop.union(index, xyTo1D(i-1, j));
		}
		if((j+1) <= count && lattice[i][j+1]){
			uf.union(index, xyTo1D(i, j+1));
			uftop.union(index, xyTo1D(i, j+1));
		}
		if((j-1) > 0 && lattice[i][j-1]){
			uf.union(index, xyTo1D(i, j-1));
			uftop.union(index, xyTo1D(i, j-1));
		}
	}
	
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j)  {
		validate(i,j);
		return lattice[i][j];
	}  
	
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) 
	{
		validate(i,j);
		return uftop.connected(xyTo1D(i, j), 0);
	}    
	
	// does the system percolate?
	public boolean percolates()  
	{
		if(uf.connected(0, count*count+1))
			return true;
		else
			return false;
	}          
	
	private int xyTo1D(int x, int y) {
		return (x-1)*count+y;
	}
	
	private boolean validate(int x, int y){
		if(x > count || x < 1)
			throw new java.lang.IndexOutOfBoundsException();
		else if(y > count || y < 1)
			throw new java.lang.IndexOutOfBoundsException();
		else
			return true;
	}
	
	
	public static void main(String[] args){
		Percolation per = new Percolation(200);
		while(!per.percolates()){
			per.open(StdRandom.uniform(1, 201),StdRandom.uniform(1, 201));
		}
	}
}
