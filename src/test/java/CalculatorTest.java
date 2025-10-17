import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Test class for Calculator
class CalculatorTest {

    // Test for divide() method with a single case
    @Test
    void testDivide() {
        Calculator c = new Calculator();
        assertEquals(2, c.divide(6, 3)); // This will fail — divide() not yet implemented
    }

    // Test for divide() method with divide-by-zero case
    @Test
    void testDivideByZero() {
        Calculator c = new Calculator();
        assertThrows(ArithmeticException.class, () -> c.divide(5, 0)); // Expect exception
    }


    // Test for multiply() method with a single case
    @Test
    void testMultiply() {
        Calculator c = new Calculator();
        assertEquals(6, c.multiply(2, 3)); // Expected result: 2 * 3 = 6 implemented
    }

    // Parameterized test for multiply() method with multiple input cases
    @ParameterizedTest
    @CsvSource({
            "2, 3, 6",
            "0, 5, 0",
            "-2, 4, -8",
            "-3, -3, 9",
            "100, 0, 0"
    })
    void testMultiplyMultipleCases(int a, int b, int expected) {
        Calculator c = new Calculator();
        assertEquals(expected, c.multiply(a, b)); // Verifies multiply() with various inputs
    }


    // Test for subtract() method with a single case
    @Test
    void testSubtract() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(3, 2)); // Expected result: 3 - 2 = 1
    }

    // Parameterized test for subtract() method with multiple input cases
    @ParameterizedTest
    @CsvSource({
            "5, 3, 2",
            "10, 5, 5",
            "0, 0, 0",
            "-5, -5, 0",
            "100, 50, 50"
    })
    void testSubtractMultipleCases(int a, int b, int expected) {
        Calculator c = new Calculator();
        assertEquals(expected, c.subtract(a, b)); // Verifies subtract() with various inputs
    }

    // Test for add() method with a single case
    @Test
    void testAdd() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3)); //Expected result: 2 + 3 = 5
    }

    // Parameterized test for add() method with multiple input cases
    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "-1, -2, -3",
            "0, 0, 0",
            "100, 200, 300",
            "-5, 10, 5"
    })

    //Method to test add - multiple cases
    void testAddMultipleCases(int a, int b, int expected) {
        Calculator c = new Calculator();
        assertEquals(expected, c.add(a, b)); // Verifies add() with various inputs
    }

}
