import java.util.Random;

public class NeuralNet
{
    private Matrix[] weights;
    private Matrix[] biases;
    private int[] defn;
    private double learningRate;
    private int size;
    
    public double maxCleaning = 0;

    public NeuralNet(int[] def)
    {
    	
    	// instantiate net
    	
        size = def.length;
        defn = def;
        weights = new Matrix[size];
        biases = new Matrix[size];
        learningRate = .1;
        weights[0] = new Matrix(1,1);
        biases[0] = new Matrix(1,1);
        for (int i = 1; i < size; i++)
        {
            weights[i] = new Matrix(def[i], def[i - 1]);
            biases[i] = new Matrix(def[i], 1);
        }
    }

    public int[] getDefn()
    {
    	
    	// layer array getter
    	
    	
        return defn;
    }

    public Matrix[] getWeights()
    
    {
    	// weights getter
    	
        return weights;
    }

    public Matrix[] getBiases()
    {
    	// biases getter
    	
        return biases;
    }

    public void setLearningRate(double in)
    {
    	// learning rate setter
    	
        learningRate = in;
    }

    public double[] respond(double[] in)
    {
    	// responds with output
    	
        Matrix it = new Matrix(in.length, 1);
        for (int i = 0; i < in.length; i++)
        {
            it.set(i, 0, in[i]);
        }
        for (int i = 1; i < size; i++)
        {
            it = weights[i].multiply(it).add(biases[i]);
            it = it.sigmoid();
        }
        double[] tR = new double[it.getR()];
        for (int i = 0; i < tR.length; i++)
        {
            tR[i] = it.get(i, 0);
        }
        return tR;
    }

    private Matrix[] respondHelper(double[] in)
    {
    	// respond helper
    	
        Matrix it = new Matrix(in.length, 1);
        Matrix[] temp = new Matrix[size];
        temp[0] = new Matrix(in.length, 1);
        for (int i = 0; i < in.length; i++)
        {
            it.set(i, 0, in[i]);
            temp[0].set(i, 0, in[i]);
        }
        for (int i = 1; i < size; i++)
        {
            it = weights[i].multiply(it).add(biases[i]);
            it = it.sigmoid();
            temp[i] = Matrix.copyMatrix(it);
        }
        return temp;
    }

    public void randomize()
    {
    	// randomize weights to between -1 and 1
    	
        for (int i = 1; i < size; i++)
        {
            weights[i].randomize(-1, 1);
            biases[i].randomize(-1, 1);
        }
    }
    
    public void randomize2()
    {
    	// randomize weights to between 0 and 1
    	
        for (int i = 1; i < size; i++)
        {
            weights[i].randomize(0, 1);
            biases[i].randomize(0, 1);
        }
    }

    public String toString()
    {
    	
    	// print representation of neural net
    	
        String tR = "Weights: \n";
        for (int i = 1; i < size; i++)
        {
            tR += i + "->" + (i + 1) + "\n" + weights[i] + "\n";
        }

        tR += "Biases: \n";
        for (int i = 1; i < size; i++)
        {
            tR += i + "->" + (i + 1) + "\n" + biases[i] + "\n";
        }
        return tR;
    }

    public NeuralNet train(double[] in, double[] out)
    {
    	
    	// train the net -> one iteration
    	
        Matrix[] mid = respondHelper(in);
        Matrix temp = new Matrix(out.length, 1);
        for (int i = 0; i < out.length; i++)
        {
            temp.set(i, 0, out[i]);
        }
        Matrix error = temp.subtract(mid[size - 1]);
        for (int i = size - 1; i >= 1 ; i--)
        {
            //Change weights
            Matrix gradient = error.elementMultiply(mid[i].dSigmoid()).multiply(learningRate);
            Matrix delta = gradient.multiply(mid[i - 1].getTransposition());
            weights[i] = weights[i].add(delta);
            biases[i] = biases[i].add(gradient);

            if(i > 1)
            {
                Matrix trans = weights[i].getTransposition();
                error = trans.multiply(error);
            }
        }
        
        return this;
    }

    public static NeuralNet copy(NeuralNet other)
    {
    	
    	// copy a nn
    	
    	
        NeuralNet temp = new NeuralNet(other.getDefn());
        for (int i = 0; i < other.getWeights().length; i++)
        {
            temp.getWeights()[i] = Matrix.copyMatrix(other.getWeights()[i]);
            temp.getBiases()[i] = Matrix.copyMatrix(other.getBiases()[i]);
        }
        temp.setLearningRate(other.getLearningRate());
        return temp;
    }

    public double getLearningRate()
    {
    	// learning rate getter
    	
        return learningRate;
    }

    public NeuralNet child(NeuralNet p1)
    {
    	
    	// create child net 
    	
        NeuralNet temp = new NeuralNet(defn);
        Random rand = new Random();
        for (int i = 0; i < size; i++)
        {
//            temp.getWeights()[i] = Matrix.copyMatrix(p1.getWeights()[i].add(weights[i]).multiply(.5));
//            temp.getBiases()[i] = Matrix.copyMatrix(p1.getBiases()[i].add(biases[i]).multiply(.5));
            if(rand.nextDouble() < .5)
            {
                temp.getWeights()[i] = Matrix.copyMatrix(p1.getWeights()[i]);
                temp.getBiases()[i] = Matrix.copyMatrix(p1.getBiases()[i]);
            }
            else
            {
                temp.getWeights()[i] = Matrix.copyMatrix((weights[i]));
                temp.getBiases()[i] = Matrix.copyMatrix((biases[i]));
            }
        }
        temp.setLearningRate(learningRate);
        return temp;
    }

    public NeuralNet mutate(double d)
    {
    	
    	// update weights and biases
    	
        NeuralNet temp = NeuralNet.copy(this);
        for (int i = 0; i < size; i++)
        {
            temp.getWeights()[i] = temp.getWeights()[i].mutate(d);
            temp.getBiases()[i] = temp.getBiases()[i].mutate(d);
        }
        return temp;
    }
    
    /*   HELPER FUNCTION TO APPLY NET TO A GAME

    public void newTrain(Game j, int numTrials)
    {
        double[][] results = j.run(this, numTrials);
        NeuralNet[] nn = new NeuralNet[(j.getNumDecisions() - 1) * numTrials];
        for (int i = 0; i < nn.length; i++)
        {
            nn[i] = copy(this);
            nn[i].train(new double[]{results[0][i]}, new double[]{results[1][i]});
        }

        NeuralNet temp = j.run(nn, numTrials);

        this.setWeights(temp.getWeights());
        this.setBiases(temp.getBiases());
    }
    
    */

    public void setWeights(Matrix[] weights)
    {
    	// weight setter
    	
        this.weights = weights;
    }

    public void setBiases(Matrix[] biases)
    {
    	// bias setter
    	
        this.biases = biases;
    }
    
    /* HELPER FUNCTION TO APPLY NET TO A GAME

    public void superTrain(Game g, int numTrials, int numTrains)
    {
        int init = g.runHelper(this, numTrials);
        for (int i = 0; i < numTrains; i++)
        {
            newTrain(g, numTrials);
            if(i == numTrains / 2 && g.runHelper(this, numTrials) <= init)
            {
                randomize();
                i = 0;
            }
        }
       
    }
    */
}
