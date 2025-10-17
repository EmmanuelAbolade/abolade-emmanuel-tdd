// Calculator class provides basic arithmetic operations
public class Calculator {
    // Adds two integers and returns the result
    public int add(int a, int b) {
        return a + b;
    }

    // Subtracts the second integer from the first and returns the result
    public int subtract(int a, int b) {
        return a - b;
    }

    // Multiplies two integers and returns the result
    public int multiply(int a, int b) {
        return a * b;
    }

    // Divides the first integer by the second and returns the result
    // Throws ArithmeticException if division by zero is attempted
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }


}
