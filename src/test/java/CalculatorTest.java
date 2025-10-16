import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {

    //Method to test subtract
    @Test
    void testSubtract() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(3, 2)); // This will fail because subtract() doesn't exist yet
    }

    //Method to test add
    @Test
    void testAdd() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
    }

    //Parameterized data using CsvSource
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
        assertEquals(expected, c.add(a, b));
    }

}
