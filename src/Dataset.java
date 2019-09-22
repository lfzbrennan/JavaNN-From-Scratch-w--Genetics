import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Random;



public class Dataset { // dataset class for piping inputs into the model
	
	
	
	public ArrayList<ArrayList<Double>> inputList = new ArrayList<ArrayList<Double>>();
	
	
	public ArrayList<Double> outputList = new ArrayList<Double>();
	
	public int totalDatapoints = 0;
	
	public ArrayList<Double> m = new ArrayList<Double>();
	
	public ArrayList<Double> b = new ArrayList<Double>();
	
	public double mOUTPUT = 0.0;
	
	public double bOUTPUT = 0.0;
	
	
	
	
	public Dataset(double[][] X, double[] y) {
		
		// instantiate a dataset from an input and output array
		
		for (int i = 0; i < X.length; i++) {
			
			ArrayList<Double> interList = new ArrayList<Double>();
			
			for (int j = 0; j < X[i].length; j++) {
				interList.add(X[i][j]);
			}
			
			inputList.add(interList);
			
			outputList.add(y[i]);
			
			
		}
		
		totalDatapoints = inputList.size();
		
	}
	
	public Dataset(String file) {
		
		// instantiate a dataset from a csv file
		
		String csv = file;
		BufferedReader b = null;
		String line = "";
        String split = " ";
        
        //   ||
        //   ||
        //   ||			parses csv
        //   VV
        
        try {
        	
            b = new BufferedReader(new FileReader(csv));
            while ((line = b.readLine()) != null) {
                String[] RESULTS = line.split(split);
                
                boolean one = false;
                
                ArrayList<Double> sensitive = new ArrayList<Double>();
                
                int count = 0;
                
                
                
                ArrayList<String> RESULTS2 = new ArrayList<String>();
                for (int x = 0; x < RESULTS.length; x++) {
                	if (RESULTS[x].trim().length() > 0) {
                		RESULTS2.add(RESULTS[x]);
                	}
                }
                
                
                
              
                for (int i = 0; i < RESULTS2.size() - 1; i++) {
                	sensitive.add(Double.parseDouble(RESULTS2.get(i)));
                	
                }
                
                
                
                inputList.add(sensitive);
                
                outputList.add(Double.parseDouble(RESULTS2.get(RESULTS2.size() - 1)));
            	
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        
	}
	
	public void normalize2() {
		
		// normalizes inputs to between -1, 1
		
		double meanOUTPUT;
		double stdOUTPUT;
		
		double[] out = this.getOutputList();
		
		
		meanOUTPUT = MathHelper.average(out);
		stdOUTPUT = MathHelper.stddev(out);
		
		for (int i = 0; i < this.outputList.size(); i++) {
			double normO = (this.outputList.get(i) - meanOUTPUT) / stdOUTPUT;
			this.outputList.set(i, normO);
		}
		
		
		
		double[][] in = this.getInputList();
		
		double[] meanI = new double[in[0].length];
		double[] stdI = new double[in[0].length];
		
	
		Matrix inx = new Matrix(in);
		
		inx = inx.getTransposition();
		
		in = inx.getVals();
		
		
		for (int i = 0; i < in.length; i++) {
			meanI[i] = MathHelper.average(in[i]);
			stdI[i] = MathHelper.stddev(in[i]);
			
		}
		
		for (int i = 0; i < this.inputList.size(); i++) {
			for (int j = 0; j < this.inputList.get(i).size(); j++) {
				double normI = (this.inputList.get(i).get(j) - meanI[j]) / stdI[j];
				this.inputList.get(i).set(j, normI);
			}
		}
		
		
	}
	
	public void normalize() {
		
		// normalizes data to between 0, 1
		
		double maxOUTPUT = 0.0;
		double minOUTPUT = this.outputList.get(0);
		
		for (int i = 0; i < this.outputList.size(); i++) {
			
			
			if (this.outputList.get(i) < minOUTPUT) {
				minOUTPUT = this.outputList.get(i);
			}
			if (this.outputList.get(i) > maxOUTPUT) {
				maxOUTPUT = this.outputList.get(i);
			}
		}
		
		double slopeO = maxOUTPUT - minOUTPUT;
		double YINT0 = 0 - minOUTPUT;

		
		this.mOUTPUT = slopeO;
		this.bOUTPUT = YINT0;
		
		for (int j = 0; j < this.inputList.get(0).size(); j++) {
			double max = 0;
			double min = this.inputList.get(0).get(j);
			
		
		
			for (int i = 0 ; i < this.inputList.size(); i ++) {
				if (this.inputList.get(i).get(j) > max) {
					max = this.inputList.get(i).get(j);
				}
			}
			
			for (int i = 0 ; i < this.inputList.size(); i ++) {
				
				if (this.inputList.get(i).get(j) < min) {
					min = this.inputList.get(i).get(j);
				}
			}
			
			
			double SLOPE = max - min;
			double YINT = 0.0 - min;
			
			for (int i = 0; i < this.inputList.size(); i++) {
				
				double normalizedValue = (this.inputList.get(i).get(j) + YINT) / SLOPE;
				this.inputList.get(i).set(j, normalizedValue);
				
			}
			
			// System.out.println(SLOPE + "  " + YINT);
			
			
			
			this.m.add(SLOPE);
			this.b.add(YINT);
			
			
		}
		
	}
	
	public double[][] getInputList() {
		
		// return input list in array format
		
		double[][] inputArray = new double[this.inputList.size()][this.inputList.get(0).size()];
		
		for (int i = 0; i < inputArray.length; i++) {
			
			
			for (int j = 0; j < inputArray[i].length; j++) {
				inputArray[i][j] = this.inputList.get(i).get(j);
			}
			
		}
		
		return inputArray;
	}
	
	public double[] getOutputList() {
		
		// returns output list in array format
		
		double[] outputArray = new double[this.outputList.size()];
		
		
		for (int i = 0; i < outputArray.length; i++) {
			outputArray[i] = this.outputList.get(i);
		}
		
		
		return outputArray;
		
	}
	
	public void shuffle() {

		// shuffles a zipped version of the dataset
		
		Random r = new Random();
		
		int len = this.inputList.size();
		
		
		for (int i = len - 1; i > 0; i--) {
			
			int j = r.nextInt(i + 1);
			
			ArrayList<Double> inputs = this.inputList.get(i);
			this.inputList.set(i, this.inputList.get(j));
			this.inputList.set(j, inputs);
			
			double output = this.outputList.get(i);
			this.outputList.set(i, this.outputList.get(j));
			this.outputList.set(j, output);
			
		}
		
		
		
		
		
		
	}
	
	public Dataset validationSet(int amount) {
		
		// creates a validationSet to be used for validation of the model
		
		this.shuffle();
		
		if (amount > 100 || amount < 1) {
			amount = 25;
		}
		
		
		double percent = (double)amount / 100.0;
		int valNum = (int)(percent * this.inputList.size());
		
		double[][] X = new double[valNum][this.inputList.get(0).size()];
		
		double[] y = new double[valNum];
		
		for (int i = 0; i < valNum; i++) {
			y[i] = this.outputList.get(i);
			for (int j = 0; j < this.inputList.get(i).size(); j++) {
				X[i][j] = this.inputList.get(i).get(j);
			}
		}
		
		Dataset output = new Dataset(X, y);
		
		return output;
		
		
		
	}

	public void print() {
		
		// prints dataset
		
		int max = 5;
		
		int permutations = max;
		
		if (this.totalDatapoints < permutations) {
			permutations = this.totalDatapoints;
		}
		
		
		for (int i = 0; i < permutations; i ++) {
			
			String data = "[";
			
			for (int j = 0; j < this.inputList.get(i).size(); j++) {
				
				data += this.inputList.get(i).get(j);
				if (j != this.inputList.get(i).size() - 1) {
					data += ", ";
				}
				
				
			}
			
			data += "]";
			
			System.out.println("Output: " + this.outputList.get(i) + "    Inputs: " + data);
			
		}
		
		
	}
}
