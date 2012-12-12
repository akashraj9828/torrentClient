package org.hamisto.quickpool.howTo;

import java.util.Random;

import QuickPool.ManualResetEvent;
import QuickPool.ThreadPool;

public class QyuckPoolHowTo {
	// This is a simple job class.
	// This job is finished after waiting for a few milliseconds.
	// To pause the thread we use a QuickPool.ManualResetEvent.
	
	//Runnable=quello che il thread deve eseguire
	
	
	public static class Job implements Runnable {
		private int fJobId;
		
		//evento che controlla il thread
		private ManualResetEvent fManualResetEvent = new ManualResetEvent(false);
		
		private Random fRandom = new Random();
        
		//costruttore
		public Job(int jobId) {
			fJobId = jobId;
		}

		public void run() {
			try {
				int waitTime = fRandom.nextInt(5) + 1 * 50;
				fManualResetEvent.WaitOne(waitTime); // Pause job.
				System.out.println("--- Job " + fJobId + " finished!");
				System.out.println("------- Wait time " + waitTime + "ms");
			} catch (Exception ex) {
			}
		}
	}
    
	//oggetto che tiene la lista dei thread disponibili
	private static QuickPool.ThreadPool fThreadPool;

	public static void main(String[] args) {
		// Initialize new Thread Pool with 10 worker threads and Normal Thread
		// Priority
		fThreadPool = new ThreadPool(10, Thread.NORM_PRIORITY);

		// Create jobs and enqueue them in the thread pool.
		// The jobs will remain in the queue until a worker peeks them.
		for (int i = 0;; i++) {
			Job job = new Job(i);
			
			//mette in coda il thread
			fThreadPool.EnqueueJob(job);
		}
	}
}
