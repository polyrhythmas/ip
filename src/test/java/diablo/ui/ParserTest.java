package diablo.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void testParseDeadlineWithNoDate() {
        String[] output = Parser.parse("deadline i wanna go home");
        assertEquals("Please indicate a deadline!", output[2]);
    }

    @Test
    public void testParseDeadlineWithWrongDateFormat() {
        String[] output = Parser.parse("deadline i wanna go home /by 19 January 2024");
        assertEquals("Please write the date in the format yyyy-mm-dd", output[2]);
    }

    @Test
    public void testParseDeadlineWithMissingDate() {
        String[] output = Parser.parse("deadline i wanna go home /by ");
        assertEquals("Please write the date in the format yyyy-mm-dd", output[2]);
    }
}
