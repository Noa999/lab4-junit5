package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    // ---------- Parameterized ----------

    @ParameterizedTest
    @CsvSource({
        "100,   A",
        "90,    A",
        "89.99, B",
        "80,    B",
        "79.99, C",
        "70,    C",
        "69.99, D",
        "60,    D",
        "59.99, F",
        "0,     F"
    })
    void letterGradeBoundaries(double score, String expected) {
        assertEquals(expected, calc.letterGrade(score));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -0.01, 100.01, 1000})
    void invalidScoresThrow(double score) {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(score));
    }

    @ParameterizedTest
    @CsvSource({
        "10, 40, 10, 10, 30, 100",
        "0,  0,  0,  0,  0,  0",
        "5,  20, 5,  5,  15, 50",
        "8,  35, 7,  9,  25, 84"
    })
    void totalScoreValidCombinations(double att, double lab, double q1, double q2, double exam, double expected) {
        assertEquals(expected, calc.totalScore(att, lab, q1, q2, exam), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
        "-1,   0,    0,    0,    0",
        "0,    -1,   0,    0,    0",
        "0,    0,    -1,   0,    0",
        "0,    0,    0,    -1,   0",
        "0,    0,    0,    0,    -1",
        "10.1, 0,    0,    0,    0",
        "0,    40.1, 0,    0,    0",
        "0,    0,    10.1, 0,    0",
        "0,    0,    0,    10.1, 0",
        "0,    0,    0,    0,    30.1"
    })
    void totalScoreRejectsOutOfRange(double att, double lab, double q1, double q2, double exam) {
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}
