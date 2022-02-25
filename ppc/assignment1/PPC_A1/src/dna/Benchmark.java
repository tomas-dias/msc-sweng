package dna;

import java.util.function.BiFunction;

import dna.problems.P1Small;
import dna.problems.P2Large;
import dna.problems.Problem;

public class Benchmark
{
	public static Result sequential(Problem p, Integer ncores)
	{
		/* Replace the following try-catch with the right operations
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		int[] results = new int[p.getPatterns().size()];

		for(int i = 0; i < p.getPatterns().size(); i++)
		{
			for(int j = 0; j < p.getSearchSequence().length; j++)
			{
				if(isIn(p.getSearchSequence(), j, p.getPatterns().get(i)))
					results[i]++;
			}
		}

		return new Result(results);
	}
	
	public static Result parallel(Problem p, Integer ncores)
	{
		/* Replace the following try-catch with the right operations
		try {
			Thread.sleep(100 / ncores);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		*/

		int[] results = new int[p.getPatterns().size()];

		Thread[] ts = new Thread[p.getPatterns().size()];
		for (int i = 0; i < p.getPatterns().size(); i++)
		{
			results[i] = 0;
			final int index = i;
			ts[i] = new Thread( () -> {
				for(int j = 0; j < p.getSearchSequence().length; j++) {
					if(isIn(p.getSearchSequence(), j, p.getPatterns().get(index)))
						results[index]++;
				}
			});
			ts[i].start();
		}

		for (Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Result(results);
	}
	
	public static boolean isIn(char[] arr, int start, String pattern) {
		if ( (arr.length - start) < pattern.length()) return false;
		for (int i=0; i < pattern.length(); i++) {
			if (arr[start + i] != pattern.charAt(i)) return false;
		}
		return true;
	}
	
	public static void bench(Problem p, BiFunction<Problem, Integer, Result> f, String name) {
		
		int maxCores = Runtime.getRuntime().availableProcessors();

		for (int ncores=1; ncores<=maxCores; ncores *= 2) {

			for (int i=0; i<30; i++) {
				long tseq = System.nanoTime();
				Result r = f.apply(p, ncores);
				tseq = System.nanoTime() - tseq;

				if (!r.compare(p.getSolution())) {
					System.out.println("Wrong result for " + name + ".");
					System.exit(1);
				}
				System.out.println(ncores + ";" + name + ";" + tseq);
			}
		}
	}
	
	public static void main(String[] args) {
		Problem p = (Runtime.getRuntime().availableProcessors() == 64) ? new P2Large() : new P1Small();
		Benchmark.bench(p, Benchmark::sequential, "seq");
		Benchmark.bench(p, Benchmark::parallel, "par");
	}
}