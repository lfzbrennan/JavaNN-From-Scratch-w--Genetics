import java.util.Random;

public class Matrix
{
    private double[][] vals;
    private int rows;
    private int cols;

    public Matrix(int r, int c)
    {
    	// instantiates matrix with rows and colunns
    	
        vals = new double[r][c];
        rows = r;
        cols = c;
    }

    public Matrix(double[][] v)
    {
    	
    	// instantiates matrix with input array
    	
        rows = v.length;
        cols = v[0].length;
        vals = new double[rows][cols];
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                vals[r][c] = v[r][c];
            }
        }
    }

    public double set(int r, int c, double val)
    {
    	// sets a value in a matrix
    	
        double temp = vals[r][c];
        vals[r][c] = val;
        return temp;
    }

    public double get(int r, int c)
    {
    	// return values in matrix
    	
        return vals[r][c];
    }

    public double[][] getVals()
    {
    	// returns all values in a matrix
    	
        return vals;
    }

    public int getR()
    {
    	// row getter
    	
        return rows;
    }

    public int getC()
    {
    	// column getter
    	
        return cols;
    }

    public String toString()
    {
    	
    	// to string method for matrix
    	
    	
        String tR = "";
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                tR += vals[r][c] + " ";
            }
            tR += "\n";
        }
        return tR;
    }

    public Matrix add(Matrix m)
    {
    	
    	// add two matrices
    	
    	
        if(m.getR() != rows || m.getC() != cols)
        {
            throw new IllegalArgumentException("Invalid dimensions");
        }
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] + m.get(r, c));
            }
        }
        return t;
    }

    public Matrix add(double other)
    {
    	// adds constant to matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] + other);
            }
        }
        return t;
    }

    public Matrix add(int other)
    {
    	// adds constant to matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] + other);
            }
        }
        return t;
    }

    public Matrix subtract(Matrix m)
    {
    	//subtracts two matrices
    	
        if(m.getR() != rows || m.getC() != cols)
        {
            throw new IllegalArgumentException("Invalid dimensions");
        }
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] - m.get(r, c));
            }
        }
        return t;
    }

    public Matrix subtract(double m)
    {
    	// subtracts constant from matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] - m);
            }
        }
        return t;
    }

    public Matrix subtract(int m)
    {
    	// subtracts constant from matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] - m);
            }
        }
        return t;
    }

    public Matrix multiply(Matrix other)
    {
    	
    	// multiplies two matrices
    	
    	
        int height = rows;
        int width = cols;
        if(width != other.getR())
        {
            throw new IllegalArgumentException("Matrix bounds not valid");
        }

        Matrix toReturn = new Matrix(height, other.getC());

        for (int r = 0; r < toReturn.getR(); r++)
        {
            for (int c = 0; c < toReturn.getC(); c++)
            {
                double sum = 0;
                for (int i = 0; i < width; i++)
                {
                    sum += vals[r][i] * other.get(i, c);
                }
                toReturn.set(r, c, sum);
            }
        }
        return toReturn;
    }

    public Matrix multiply(double m)
    {
    	
    	// multiplies matrix by a constant
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] * m);
            }
        }
        return t;
    }

    public Matrix multiply(int m)
    {
    	// mutliplies matrix by a constant
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, vals[r][c] * m);
            }
        }
        return t;
    }

    public double determinant()
    {
    	// returns determinant of a matrix
    	
        if(rows != cols)
        {
            throw new IllegalArgumentException("Matrix bounds invalid");
        }
        if(rows == 2)
        {
            return vals[0][0] * vals[1][1] - vals[0][1] * vals[1][0];
        }

        double sum = 0;

        for (int i = 0; i < rows; i++)
        {
            Matrix temp = shadow(0, i);
            sum += temp.determinant() * vals[0][i] * Math.pow(-1, i);
        }
        return sum;
    }

    private Matrix shadow(int indexR, int indexC)
    {
    	
    	// returns shadow matrix
    	
        Matrix toReturn = new Matrix(rows - 1, cols - 1);

        int swR = 0;
        for (int r = 0; r < rows; r++)
        {
            int swC = 0;

            if(r == indexR)
            {
                r++;
                swR = 1;
            }
            if(r != rows)
            {
                for (int c = 0; c < cols; c++)
                {

                    if (c == indexC)
                    {
                        c++;
                        swC = 1;
                    }
                    if (c != cols)
                    {
                        toReturn.set(r - swR, c - swC, vals[r][c]);
                    }
                }
            }
        }
        return toReturn;
    }

    public Matrix inverse()
    {
    	// returns inverse matrix
    	
        Matrix toReturn = getCofactor().getTransposition();
        toReturn = toReturn.multiply(1/determinant());

        return toReturn;
    }

    public Matrix getCofactor()
    {
    	
    	// returns cofactor matrix
    	
    	
        Matrix toReturn = new Matrix(rows, cols);

        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                toReturn.set(r, c, shadow(r, c).determinant() * (Math.pow(-1, r + c)));
            }
        }

        return toReturn;
    }

    public Matrix getTransposition()
    {
    	
    	// get transposition of matrix
    	
        Matrix toReturn = new Matrix(cols, rows);

        for (int r = 0; r < cols; r++)
        {
            for (int c = 0; c < rows; c++)
            {
                toReturn.set(r, c, vals[c][r]);
            }
        }

        return toReturn;
    }

    public void randomize(int low, int high)
    {
    	// randomize matrix
    	
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                vals[r][c] = (Math.random() * (high - low) + low);
            }
        }
    }

    public static Matrix copyMatrix(Matrix other)
    {
    	// copy matrix
    	
        Matrix t = new Matrix(other.getR(), other.getC());
        for (int r = 0; r < other.getR(); r++)
        {
            for (int c = 0; c < other.getC(); c++)
            {
                t.set(r, c, other.get(r, c));
            }
        }
        return t;
    }

    public Matrix sigmoid()
    {
    	// return result of sigmoid function to matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r,c, 1/(1+Math.exp(-vals[r][c])));
            }
        }
        return t;
    }

    public Matrix dSigmoid()
    {
    	// return result of derivative of sigmoid function to matrix
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r,c, vals[r][c] * (1 - vals[r][c]));
            }
        }
        return t;
    }

    public Matrix elementMultiply(Matrix other)
    {
    	// element wise multiplication
    	
        Matrix t = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                t.set(r, c, other.get(r, c) * vals[r][c]);
            }
        }
        return t;
    }

    public Matrix mutate(double d)
    {
    	// creates a child matrix
    	
        Random rand = new Random();
        Matrix temp = Matrix.copyMatrix(this);
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if(rand.nextDouble() < .15)
                temp.set(r, c, temp.get(r, c) + d * rand.nextGaussian());
            }
        }
//        int r = rand.nextInt(rows);
//        int c = rand.nextInt(cols);
//        temp.set(r, c, temp.get(r, c) + d * rand.nextGaussian());
        return temp;
    }
}
