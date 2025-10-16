import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Test class for Calculator
class CalculatorTest {

    // Test for subtract() method with a single case
    @Test
    void testSubtract() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(3, 2)); // Expected result: 3 - 2 = 1
    }

    // Test for add() method with a single case
    @Test
    void testAdd() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
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
