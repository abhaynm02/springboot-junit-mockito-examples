import com.rest.app.Calculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    Calculator calculator;
    @BeforeEach
    void setUp(){
        calculator=new Calculator();
    }
    @AfterEach
    void message(){
        System.out.println("testing is finished ");
    }
    @Test
    void testMultiply(){
        System.out.println("testing is started ");
        assertEquals(20,calculator.multiply(5,4));
    }
    @Test
    void testMultiply2(){
        System.out.println("testing is started for multiply 2 method ");
        assertEquals(4,calculator.multiply(1,4));
    }
}
