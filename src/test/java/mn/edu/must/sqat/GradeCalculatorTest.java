package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class GradeCalculatorTest {

    // ---------- letterGrade ----------

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(90.0);          // Act
        assertEquals("A", grade);                       // Assert
    }

    @Test
    @DisplayName("100 оноо A дүн байх ёстой (дээд хязгаар)")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(100.0);         // Act
        assertEquals("A", grade);                       // Assert
    }

    @Test
    @DisplayName("0 оноо F дүн байх ёстой (доод хязгаар)")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(0.0);           // Act
        assertEquals("F", grade);                       // Assert
    }

    @Test
    @DisplayName("89.99 оноо B дүн байх ёстой (90-ээс арай доор)")
    void justBelowNinetyIsB() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(89.99);         // Act
        assertEquals("B", grade);                       // Assert
    }

    @Test
    @DisplayName("Сөрөг оноо IllegalArgumentException шидэх ёстой")
    void negativeScoreThrows() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.letterGrade(-0.1));
    }

    @Test
    @DisplayName("100-аас их оноо IllegalArgumentException шидэх ёстой")
    void overHundredThrows() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.letterGrade(100.1));
    }

    @Test
    @DisplayName("NaN оноо IllegalArgumentException шидэх ёстой")
    void nanScoreThrows() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.letterGrade(Double.NaN));
    }

    // ---------- totalScore ----------

    @Test
    @DisplayName("Бүх хэсгийн оноо зөв нэмэгдэх ёстой (8+35+7+9+25=84)")
    void totalScoreSumsAllParts() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        double total = calc.totalScore(8, 35, 7, 9, 25); // Act
        assertEquals(84.0, total, 0.0001);              // Assert
    }

    @Test
    @DisplayName("Бүх оноо 0 бол нийлбэр 0 байх ёстой")
    void totalScoreAllZeroIsZero() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        double total = calc.totalScore(0, 0, 0, 0, 0);  // Act
        assertEquals(0.0, total, 0.0001);               // Assert
    }

    @Test
    @DisplayName("Ирц 10-аас хэтэрвэл exception шидэх ёстой")
    void totalScoreRejectsAttendanceOver10() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.totalScore(10.5, 30, 5, 5, 20));
    }

    @Test
    @DisplayName("Шалгалт 30-аас хэтэрвэл exception шидэх ёстой")
    void totalScoreRejectsExamOver30() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.totalScore(5, 30, 5, 5, 31));
    }

    // ---------- Parameterized ----------

    @ParameterizedTest
    @DisplayName("letterGrade хязгаарын утгууд: {0} -> {1}")
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
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(score);         // Act
        assertEquals(expected, grade);                  // Assert
    }

    @ParameterizedTest
    @DisplayName("Буруу оноо {0} exception шидэх ёстой")
    @ValueSource(doubles = {-1, -0.01, 100.01, 1000})
    void invalidScoresThrow(double score) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.letterGrade(score));
    }

    @ParameterizedTest
    @DisplayName("totalScore зөв нийлбэр: {0}+{1}+{2}+{3}+{4} = {5}")
    @CsvSource({
        "10, 40, 10, 10, 30, 100",
        "0,  0,  0,  0,  0,  0",
        "5,  20, 5,  5,  15, 50",
        "8,  35, 7,  9,  25, 84"
    })
    void totalScoreValidCombinations(double att, double lab, double q1, double q2,
                                     double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        double total = calc.totalScore(att, lab, q1, q2, exam); // Act
        assertEquals(expected, total, 0.0001);          // Assert
    }

    @ParameterizedTest
    @DisplayName("totalScore буруу оролт exception шидэх ёстой")
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
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
                () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}
