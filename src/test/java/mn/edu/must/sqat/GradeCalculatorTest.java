package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GradeCalculatorTest {

    private final GradeCalculator calc = new GradeCalculator();

    // ---------- letterGrade ----------

    @Test
    void ninetyIsExactlyA() {
        assertEquals("A", calc.letterGrade(90));
    }

    @Test
    void hundredIsA() {
        assertEquals("A", calc.letterGrade(100));
    }

    @Test
    void zeroIsF() {
        assertEquals("F", calc.letterGrade(0));
    }

    @Test
    void justBelowNinetyIsB() {
        assertEquals("B", calc.letterGrade(89.99));
    }

    @Test
    void negativeScoreThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-0.1));
    }

    @Test
    void overHundredThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(100.1));
    }

    @Test
    void nanScoreThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(Double.NaN));
    }

    // ---------- totalScore ----------

    @Test
    void totalScoreSumsAllParts() {
        assertEquals(84.0, calc.totalScore(8, 35, 7, 9, 25), 0.0001);
    }

    @Test
    void totalScoreAllZeroIsZero() {
        assertEquals(0.0, calc.totalScore(0, 0, 0, 0, 0), 0.0001);
    }

    @Test
    void totalScoreRejectsAttendanceOver10() {
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10.5, 30, 5, 5, 20));
    }

    @Test
    void totalScoreRejectsExamOver30() {
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(5, 30, 5, 5, 31));
    }
}
