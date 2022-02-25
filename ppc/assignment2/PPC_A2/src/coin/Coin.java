package coin;

import java.util.concurrent.RecursiveTask;

public class Coin extends RecursiveTask<Integer>
{
	public static final int LIMIT = 999;
	private int[] coins;
	private int index;
	private int accumulator;
	//private int depth;

	public Coin(int[] coins, int index, int accumulator/*, int depth*/)
	{
		this.coins = coins;
		this.index = index;
		this.accumulator = accumulator;
		//this.depth = depth;
	}

	public static int[] createRandomCoinSet(int N)
	{
		int[] r = new int[N];
		for (int i = 0; i < N ; i++) {
			if (i % 10 == 0) {
				r[i] = 400;
			} else {
				r[i] = 4;
			}
		}
		return r;
	}

	private static int seq(int[] coins, int index, int accumulator)
	{
		if (index >= coins.length) {
			if (accumulator < LIMIT) {
				return accumulator;
			}
			return -1;
		}
		if (accumulator + coins[index] > LIMIT) {
			return -1;
		}
		int a = seq(coins, index+1, accumulator);
		int b = seq(coins, index+1, accumulator + coins[index]);
		return Math.max(a,  b);
	}
	
	private static int par(int[] coins, int index, int accumulator)
	{
/*		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		Coin fc = new Coin(coins, index, accumulator);
		return fc.compute();
	}

	@Override
	protected Integer compute()
	{
		if (index >= coins.length) {
			if (accumulator < LIMIT) {
				return accumulator;
			}
			return -1;
		}
		if (accumulator + coins[index] > LIMIT) {
			return -1;
		}

		// Max level:
		//if ( depth >= 20 ) return seq(coins, index, accumulator);

		// Max tasks: if the total number of tasks >= T * #cores.
		//if ( this.getQueuedTaskCount() > 4 * Runtime.getRuntime().availableProcessors() ) return seq(coins, index, accumulator);

		// Surplus: if the current queue has more than 2 tasks than the average
		if ( RecursiveTask.getSurplusQueuedTaskCount() > 2 ) return seq(coins, index, accumulator);

		Coin c1 = new Coin(coins, index+1, accumulator);
		c1.fork();

		Coin c2 = new Coin(coins, index+1, accumulator + coins[index]);

		int b = c2.compute(); // Executes directly here (and not in another task)
		int a = c1.join();

		return Math.max(a, b);
	}

	public static void main(String[] args)
	{
		int nCores = Runtime.getRuntime().availableProcessors();

		int[] coins = createRandomCoinSet(30);

		int repeats = 40;
		for (int i=0; i<repeats; i++) {
			long seqInitialTime = System.nanoTime();
			int rs = seq(coins, 0, 0);
			long seqEndTime = System.nanoTime() - seqInitialTime;
			System.out.println(nCores + ";Sequential;" + seqEndTime);

			long parInitialTime = System.nanoTime();
			int rp = par(coins, 0, 0);
			long parEndTime = System.nanoTime() - parInitialTime;
			System.out.println(nCores + ";Parallel;" + parEndTime);

			if (rp != rs) {
				System.out.println("Wrong Result!");
				System.exit(-1);
			}
		}
	}
}