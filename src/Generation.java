public class Generation
{
    private double[] fitness;
    private NeuralNet[] brains;
    private int size;

    public Generation(int size, int[] def)
    {
    	// generation to apply neuralnet to a game
    	
        this.size = size;
        brains = new NeuralNet[size];
        fitness = new double[size];
        for (int i = 0; i < size; i++)
        {
            brains[i] = new NeuralNet(def);
            brains[i].randomize();
        }
    }

    public void randomize()
    {
    	
    	// randomizes generations
    	
    	
        for (int i = 0; i < size; i++)
        {
            brains[i].randomize();
        }
    }

    public int size()
    {
    	// size getter
    	
        return size;
    }

    public double[] respond(int i, double[] in)
    {
    	// finds output of input
    	
        return brains[i].respond(in);
    }

    public void setFitness(int i, double a)
    {
    	
    	// sets fitness of function
    	
        fitness[i] = a;
    }

    public void evolve(double d)
    {
    	// creates a new generation
    	
        double[] probs = new double[size];
        probs[0] = fitness[0];
        for (int i = 1; i < size; i++)
        {
            probs[i] = probs[i - 1] + fitness[i];
        }
        for (int i = 0; i < size; i++)
        {
            probs[i] = probs[i] / probs[size - 1];
        }
        double a = Math.random();
        double b = Math.random();

        boolean acalled = false;
        int ai = 0;
        boolean bcalled = false;
        int bi = 0;
        for (int i = 0; i < size && !(acalled && bcalled); i++)
        {
            if(!acalled && a < probs[i])
            {
                acalled = true;
                ai = i;
            }
            if(!bcalled && b < probs[i])
            {
                bcalled = true;
                bi = i;
            }
        }

        NeuralNet p1 = NeuralNet.copy(brains[ai]);
        NeuralNet p2 = NeuralNet.copy(brains[bi]);
        NeuralNet dad = p1.child(p2);

        brains[0] = NeuralNet.copy(dad);
        for (int i = 1; i < size; i++)
        {
            brains[i] = NeuralNet.copy(dad.mutate(d));
        }
        fitness = new double[size];
    }
}
