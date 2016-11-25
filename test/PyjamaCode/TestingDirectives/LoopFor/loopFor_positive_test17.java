package PyjamaCode.TestingDirectives.LoopFor;

public class loopFor_positive_test17 {
	/**
	 * loopFor More
	 * */
	 
	public  int max() {
  		return Integer.MAX_VALUE;
  	}
  	
	public int[] parallel_loopFor(int threadCount) {
		Pyjama.omp_set_num_threads(threadCount);
		int[] array = new int[1000];

		//#omp parallel for shared(array)
		for (int i = 0; i < max(); i++) {
			array[i]=i;
		}

		return array;
	}
	
}
