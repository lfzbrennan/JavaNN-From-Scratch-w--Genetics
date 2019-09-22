import java.util.Arrays;


public class Genetics { // an experimental genetics class to optimize lr/layers/trials/epochs for nn
	
	public static final int GENERATION_OFFSET = 2; // higher offset = less deviate in winners
	
	
	public static final int NUM_MUTATIONS = 50; // number of children per generation
	public static final int TOTAL_GENERATIONS = 30; // number of generations
	
	
	public static final int WINNERS = 3; // number of "winners" per generation - number of nets that have children
	public static final int layers = 4; // layers of the neuralnet
	
	
	
	
	public static int[] instantializeLayers(int layers) {
		
		// instantialize layers
	
		
		int[] hiddenLayers = new int[layers];
		final int MAX_LAYERS = 500;
		for (int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = (int)Math.round(Math.random() * MAX_LAYERS);
			if (hiddenLayers[i] < 2) {
				hiddenLayers[i] = 2;
			}
		}
		
		
		return hiddenLayers;
		
	}
	
	public static int instantializeTrials() {
		
		// instantialize trials

		final int MAX_TRIALS = 10000;
		
		int trials;
		
		trials = (int)Math.round(Math.random() * MAX_TRIALS);
		
		if (trials <= 10) {
			trials = 10;
		}
		
		return trials;
	}
	
	public static double instantializeLR() {
		
		// intantialize learning rate
		
		double lr;
		double startingLR = .01;
		if ((int)(Math.random() * 2) == 1) {
			lr = startingLR + (Math.random() * .05);
		} else {
			lr = startingLR - (Math.random() * .005);
		}
		
		return lr;
		
	}
	
	public static int instantializeEP() {
		
		// instantialize epochs
		
		int epoch;
		int startingEpoch = 50;
		epoch = (int)(Math.random() * startingEpoch) + 1;
		
		return epoch;
	}
	
	public static int[] mutatedLayers(int layers, double[] acc, int[][] bestLayers, int g, int j) {
		
		// creates children of best performing layers
		
		
		int[] hiddenLayers = new int[layers];
		
		final int MAX_LAYERS = 500;
		
		int range = (int)(Math.round((double)MAX_LAYERS / (double)(g+ GENERATION_OFFSET)));
		
		
		int whichWinner = j % WINNERS;
		// System.out.println(whichWinner);
		
		for (int i = 0; i < hiddenLayers.length; i++) {
			int offset = (int)Math.round(Math.random() * (2 * range) - range);
			
			
			hiddenLayers[i] = bestLayers[whichWinner][i] + offset;
			if (hiddenLayers[i] > 1000) {
				hiddenLayers[i] = 1000;
			} else if (hiddenLayers[i] < 2) {
				hiddenLayers[i] = 2;
			}
		}
		
		
		
		return hiddenLayers;
		
		
	}
	
	public static int mutatedTrials(double bestAcc[], int bestTrials[], int g, int j) {
		
		// creates children of best performing trials
		
		int trials = 0;
		
		
		final int MAX_TRIALS = 10000;
		
		int range = (int)(Math.round((double)MAX_TRIALS / (double)(g + GENERATION_OFFSET)));
		
		int whichWinner = j % WINNERS;
		
		trials = bestTrials[whichWinner] + (int)Math.round(Math.random() * (2 * range) - range);
		
		
		
		if (trials > 20000) {
			trials = 20000;
		} else if (trials < 5) {
			trials = 5;
		}
		
		return trials;
		
	}
	
	public static double mutatedLR(double[] bestLR, int g, int j) {
		
		// creates children of best performing learning rate
		
		double lr;
		
		final double MAXLR = .1;
		final double MINLR = .001;
		
		final double STARTINGLR = .01;
		
		int whichWinner = j % WINNERS;
		
		if ((int)(Math.random() * 2) == 1) {
			double range = STARTINGLR / (double)(g + GENERATION_OFFSET);
			lr = bestLR[whichWinner] - (Math.random() * range);
		} 
		else {
			double range = MAXLR / (double)(g + GENERATION_OFFSET);
			lr = bestLR[whichWinner] + (Math.random() * range);
		}
		
		if (lr > MAXLR) {
			lr = MAXLR;
		}
		else if (lr < MINLR) {
			lr = MINLR;
		}
		
		
		
		return lr;
	}
	
	public static int mutatedEP(int[] bestEP, int g, int j) {
		
		//  creates children of best performing epochs
		
		int epochs = 0;
		
		
		final int MAX_EPOCHS = 30;
		
		int range = (int)(Math.round((double)MAX_EPOCHS / (double)(g + GENERATION_OFFSET)));
		
		int whichWinner = j % WINNERS;
		
		epochs = bestEP[whichWinner] + (int)Math.round(Math.random() * (2 * range) - range);
		
		
		
		if (epochs > 100) {
			epochs = 100;
		} else if (epochs < 1) {
			epochs = 1;
		}
		
		return epochs;
	}
	
	
	public static int[] createHiddenLayers(int g, int m, int l, double[] bestAcc, int[] bestTrials, int[][] bestLayers, int j) {
		
		// creates layers
		
		int[] layers = new int[m];
		
		if (g == 0) {
			
			layers = instantializeLayers(l);
			
		}
		
		else {
			
			layers = mutatedLayers(l, bestAcc, bestLayers, g, j);
			
		}
		
		return layers;
		
	}
	
	public static int createTrials(int g, int m, int l, double[] bestAcc, int[] bestTrials, int[][] bestLayers, int j) {
		
		// creates trials
		
		int trials = 0;
		
		if (g == 0) {
			trials = instantializeTrials();
		} else {
			trials = mutatedTrials(bestAcc, bestTrials, g, j);
		}
		
		
		return trials;
	}
	
	public static double createLR(int g, double[] bestLR, int j) {
		
		// creates learning rate
		
		double lr = 0.01;
		if (g == 0) {
			lr = instantializeLR();
		}
		else {
			lr = mutatedLR(bestLR, g, j);
		}
		
		return lr;
	}
	
	public static int createEP(int g, int[] bestEP, int j) {
		
		// creates epochs
		
		int epoch = 10;
		if (g == 0) {
			epoch = instantializeEP();
		} else {
			
		}
		
		return epoch;
	}
	
	public static int[] bestMutIndex(double[] a) {
		
		// finds the best performing child
		
		int[] indexs = new int[WINNERS];
		
		for (int v = 0; v < indexs.length; v++) {
			double max = 0;
			
			for (int i = 0; i < a.length; i++) {
				if (a[i] > max) {
					max = a[i];
				}
			}
			
			Double[] array = new Double[a.length];
			
			for (int x = 0; x < array.length; x++) {
				array[x] = a[x];
				
			}
			
			System.out.println(max);
			
			indexs[v] = Arrays.asList(array).indexOf(max);
			
			a[indexs[v]] = 0.0;
		}
		
		
		return indexs;
	}
	
	
	public static void DOGENETICS() {
		
		// runs genetics
		
		int GENERATION = 0;
	
		

		
		
		double[] ACCURACY = new double[NUM_MUTATIONS];
		int[][] HIDDENLAYERS = new int[NUM_MUTATIONS][];
		int[] TRIALS = new int[NUM_MUTATIONS];
		double[] LR = new double[NUM_MUTATIONS];
		int[] EPOCHS = new int[NUM_MUTATIONS];
		
		
		double AVG_ACCURACY = 0.0;
		double MAX_ACCURACY = 0.0;
		
		
		
		
		int[] bestIndexs = new int[WINNERS];
		double[] bestAccuracys = new double[WINNERS];
		int[] bestTrialss = new int[WINNERS];
		int[][] bestLayerss = new int[WINNERS][layers];
		double[] bestLearningRate = new double[WINNERS];
		int[] bestEpoch = new int[WINNERS];
		 
		
		
		for (int i = 0; i < TOTAL_GENERATIONS; i++ ) {
			
			System.out.println(" ");
			
			AVG_ACCURACY = 0;
			MAX_ACCURACY = 0;
			
			if (GENERATION != 0) {
				
				bestIndexs = bestMutIndex(ACCURACY);
				
				for (int w = 0; w < bestIndexs.length; w++) {
					bestAccuracys[w] = ACCURACY[bestIndexs[w]];
					bestTrialss[w] = TRIALS[bestIndexs[w]];
					bestLayerss[w] = HIDDENLAYERS[bestIndexs[w]];
					bestLearningRate[w] = LR[bestIndexs[w]];
					bestEpoch[w] = EPOCHS[bestIndexs[w]];
				}
			}
			for (int j = 0; j < NUM_MUTATIONS; j++) {
				
				
				HIDDENLAYERS[j] = createHiddenLayers(GENERATION, NUM_MUTATIONS, layers, bestAccuracys, bestTrialss, bestLayerss, j);
				TRIALS[j] = createTrials(GENERATION, NUM_MUTATIONS, layers, bestAccuracys, bestTrialss, bestLayerss, j);
				LR[j] = createLR(GENERATION, bestLearningRate, j);
				EPOCHS[j] = createEP(GENERATION, bestEpoch, j);
				
				
				ACCURACY[j] = Runner.IO(HIDDENLAYERS[j], TRIALS[j], LR[j], EPOCHS[j]);
				if (j % 5 == 0) {
					System.out.println("MUTATION: " + j);
				}
				
			}
			
			
			
			
			
			
			// COMPUTE ACCURACIES
			
			AVG_ACCURACY = 0.0;
			MAX_ACCURACY = 0.0;
			
			
			for (int S = 0; S < ACCURACY.length; S++) {
				AVG_ACCURACY += ACCURACY[S];
			}
			
			AVG_ACCURACY /= NUM_MUTATIONS;
			
			for (int l = 0; l < ACCURACY.length; l++) {
				if (ACCURACY[l] > MAX_ACCURACY) {
					MAX_ACCURACY = ACCURACY[l];
				}
			}
			
			
			
			System.out.println("GENERATION: " + (GENERATION + 1));
			System.out.println("AVERAGE ACCURACY: " + AVG_ACCURACY);
			System.out.println("MAX ACCURACY: " + MAX_ACCURACY);
			
			for (int win = 0; win < WINNERS; win++) {
				System.out.println("BEST TRIALS: " + bestTrialss[win]);
			}
			System.out.println();
			for (int win = 0; win < WINNERS; win++) {
				System.out.println("BEST LEARNING RATE: " + bestLearningRate[win]);
			}
			
			
			for (int f = 0; f < WINNERS; f++) {
				System.out.println("");
				for (int q = 0; q < bestLayerss[f].length; q++) {
					System.out.print(bestLayerss[f][q] + " ");
				}
			}
			System.out.println(" ");
			System.out.println(" ");
			
			/*
			if (GENERATION == 1) {
				for (int v = 0; v < ACCURACY.length; v++) {
					System.out.print(Math.round(ACCURACY[v]) + " ");
				}
			}
			*/
			
			
			
			GENERATION++;
		}
		
		
		for (int r = 0; r < WINNERS; r++) {
			System.out.println(bestTrialss[r]);
		}
		
		for (int r = 0; r < WINNERS; r++) {
			System.out.println(bestLearningRate[r]);
		}
		System.out.println();
		
		
		
		for (int l = 0; l < WINNERS; l++) {
			System.out.println("");
			for (int z = 0; z < bestLayerss.length; z++) {
				System.out.print(bestLayerss[l][z] + " ");
			
			}
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		// run the genetics
		
			
		DOGENETICS();
				
		}

}
