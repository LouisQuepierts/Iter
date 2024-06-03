package quepierts.iter.util.math;

public class GaussianKernel {

    public static double[] getGaussianKernel(int size, double sigma) {
        double[] kernel = new double[size * size];
        double step = 1.0 / size;
        double amount = 0;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double u = -0.5 + (0.5 + x) * step;
                double v = -0.5 + (0.5 + y) * step;
                double value = getDistribution(u, v, sigma);

                kernel[y * size + x] = value;
                amount += value;
            }
        }

        for (int i = 0; i < kernel.length; i++) {
            kernel[i] /= amount;
        }

        return kernel;
    }

    public static double getDistribution(double u, double v, double sigma) {
        double sqrR = u * u + v * v;
        double exponent = -sqrR / (2 * sigma * sigma);
        double denominator = 2 * Math.PI * sigma * sigma;

        return Math.pow(Math.E, exponent) / denominator;
    }
}
