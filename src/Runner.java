public class Runner
{
	
	public NeuralNet updateNetwork(NeuralNet net, double[] inputs, double output) {
		
		// update network - used from outside scope
		
		net = net.train(inputs, new double[] {output});
		
		return net;
		
	}
	
	public static NeuralNet run(double lr, int inputDim, int outputDim, int[] hiddenNeurons, final int epochs, Dataset d) {
		
		// trains the model
		
		int[] layers = new int[hiddenNeurons.length + 1];
		
		layers[0] = inputDim;
		for (int i = 0; i < hiddenNeurons.length; i++) {
			layers[i + 1] = hiddenNeurons[i];
		}
		
		layers[layers.length - 1] = outputDim;
		
		NeuralNet net = new NeuralNet(layers);
		net.randomize();
		net.setLearningRate(lr);
		
		
		int interval = 100;
		
		int NUM_TRIALS = d.getInputList().length;
		
		
		d.normalize();
		
		d.shuffle();
		
		d.print();
		
		
		double[][] input = d.getInputList();
		double[] output = d.getOutputList();
		
		
		
		for (int j = 0; j < epochs; j++) {		
			
			d.shuffle();
			
			input = d.getInputList();
			output = d.getOutputList();
			
			System.out.println("EPOCH: " + (j + 1));
			
			System.out.println();
			for (int i = 0; i < NUM_TRIALS; i++) {
			
			
			
				if (i % interval == 0) {
					System.out.println("Trial: " + i);
				}
			
			
				net.train(input[i], new double[] {output[i]});
			}
			
			//System.out.println();
        }
		
		return net;
	}
	
	public static NeuralNet instantiateNetwork() {
		NeuralNet net = new NeuralNet(new int[]{5, 16, 64, 64, 1});
		return net;
	}
	
	
	public static double accuracy(NeuralNet n, Dataset d, int valPercent) {
		
		// returns accuracy of model 
		
		double stats = 0.0;
		
		d.normalize();
		
		d = d.validationSet(valPercent); // valPercent is 0-100, percentage of set used for validation
		
		d.print();
		
		int VAL_RUNS = d.getInputList().length;
		
		double[][] I = d.getInputList();
		double[] O = d.getOutputList();
		
		
		
		
		double VAL_ACCURACY = 0.0;
		
		for (int i = 0; i < VAL_RUNS; i ++) {
            double[] testDouble = I[i];
            
            double output = n.respond(testDouble)[0];
            double actual = O[i];
            
            
            /*
            for (int j = 0; j < 2; j++) {
            	System.out.println(output);
            }
            */
            
            double difference = Math.abs(actual - output);
            
            difference /= (output);
            
            VAL_ACCURACY += 1.0 - difference;
            
            
            
		}
	
		stats = VAL_ACCURACY / VAL_RUNS;
		
		return stats;
		
	}
	
	
	public static double IO(int[] hiddenN, int trials, double lr, int ep) {
		
		// to return result of network from function outside scope
		
		String dataCSV = "housing.csv";
    	
    	Dataset d = new Dataset(dataCSV);
    	
    	double[][] in = d.getInputList();
    	
    	int inputDim = in[0].length;
    	int outputDim = 1;
    	
    	double learningRate = lr;
    	

    	int[] hiddenNeurons= hiddenN;
    	
    	final int epochs = ep;
    	
    	NeuralNet net;
    	
    	net = run(learningRate, inputDim, outputDim, hiddenNeurons, epochs, d);
    	
    	double acc;
    	
    	acc = accuracy(net, d, 100);
    	
    	return acc;
		
		
	}
	
	
	
    public static void main(String[] args)
    {	
    	
    	// main function for running network
    	
    	
    	String dataCSV = "housing.csv";
    	
    	Dataset d = new Dataset(dataCSV);
    	
    	double[][] in = d.getInputList();
    	
    	int inputDim = in[0].length;
    	int outputDim = 1;
    	
    	
    	System.out.println("hey");
    	double lr = .01;
    	

    	int[] hiddenNeurons= {128, 256, 512, 64};
    	
    	final int epochs = 25;
    	
    	NeuralNet net;
    	
    	net = run(lr, inputDim, outputDim, hiddenNeurons, epochs, d);
    	
    	double acc;
    	
    	acc = accuracy(net, d, 100);
    	
    	System.out.println("Final Accuracy: " + acc);
    	

    }

}
