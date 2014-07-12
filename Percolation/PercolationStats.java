
public class PercolationStats {
	private double[] samples;
	private int experTimes;
	
	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T){
		if(N <= 0 || T <= 0) 
            throw new IllegalArgumentException("Illeagal Argument");
		samples = new double[T];
		experTimes = T;
		
		
		for(int i = 1 ; i <= T ; i++){
			int opensites = 0;
			Percolation per = new Percolation(N);
			while(!per.percolates()){
				int x = StdRandom.uniform(1, N+1);
				int y = StdRandom.uniform(1, N+1);
				if(!per.isOpen(x, y)){
					per.open(x,y);
					opensites++;
				}
			}

			samples[i-1] = (double)opensites/(N*N);
		}
	}
	
	// sample mean of percolation threshold
	public double mean(){
		double sum = 0.0;
		for(double i:samples){
			sum += i;
		}
		return sum/experTimes;
	}  
	
	// sample standard deviation of percolation threshold
	public double stddev(){
		if (experTimes == 1)
            return Double.NaN;
		double sum = 0.0;
		double mean = mean();
		for(double i:samples){
			sum += (i-mean)*(i-mean);
		}
		return Math.sqrt(sum/(experTimes-1));
	}           
	
	// returns lower bound of the 95% confidence interval
	public double confidenceLo(){
		double mean = mean();
		double stddev = stddev();
		return mean-1.96*stddev/Math.sqrt(experTimes);
	} 
	
	// returns upper bound of the 95% confidence interval
	public double confidenceHi(){
		double mean = mean();
		double stddev = stddev();
		return mean+1.96*stddev/Math.sqrt(experTimes);
	}           
	
	// test client, described below
	public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(200, 100);
        StdOut.println("mean = "+percStats.mean());
        StdOut.println("stddev = "+percStats.stddev());
        StdOut.println("95%% confidence interval = "+ percStats.confidenceLo()+"  "+percStats.confidenceHi());
        
    }
}
