package diablo.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Parses strings into string arrays.
 */
public class Parser {

    /**\
     * Takes a string and parses it based on the input logic of Diablo, returning a string array with the required
     * information.
     *
     * @param input String to be parsed.
     * @return String array containing the parsed contents of the string.
     */
    public static String[] parse(String input) {
        String firstWord = input.split(" ")[0];
        switch (firstWord) {
        case "bye": {
            return new String[] {"bye"};
        }
        case "list": {
            return new String[] {"list"};
        }
        case "find": {
            int stringLen = input.length();
            return new String[] {"find", input.substring(5, stringLen)};
        }
        case "mark": {
            int stringLen = input.length();
            return new String[] {"mark", input.substring(stringLen - 1, stringLen)};
        }
        case "delete": {
            int stringLen = input.length();
            return new String[] {"delete", input.substring(stringLen - 1, stringLen)};
        }
        case "deadline": {
            int startBy = input.indexOf("/by");
            if (startBy == -1) {
                return new String[] {"deadline", "-1", "Please indicate a deadline!"};
            } else {
                String description = input.substring(9, startBy - 1);
                String day = input.substring(startBy + 4);
                try {
                    LocalDate date = LocalDate.parse(day);
                } catch (DateTimeParseException e) {
                    return new String[] {"deadline", "-1", "Please write the date in the format yyyy-mm-dd"};
                }
                return new String[] {"deadline", description, day};
            }
        }
        case "todo": {
            String noSpaces = input.replace(" ", "");
            int todoLength = noSpaces.length();
            if (todoLength < 5) {
                return new String[] {"todo", "-1", "Please include something you want to do!"};
            } else {
                String description = input.substring(5);
                return new String[] {"todo", description};
            }
        }
            case "event": {
                int startFrom = input.indexOf("/from");
                int startTo = input.indexOf("/to");
                if (startFrom == -1 || startTo == -1) {
                    return new String[] {"event", "-1", "Please include both /from and /to dates!"};
                } else {
                    String description = input.substring(6, startFrom - 1);
                    String from = input.substring(startFrom + 6, startTo - 1);
                    String to = input.substring(startTo + 4);
                    try {
                        LocalDate dateFrom = LocalDate.parse(from);
                        LocalDate dateTo = LocalDate.parse(to);
                    } catch (DateTimeParseException e) {
                        return new String[] {"event", "-1", "Please write the dates in the format yyyy-mm-dd"};
                    }
                    return new String[] {"event", description, from, to};
                }
            }
            default: {
                return new String[] {""};
            }

        }
    }
}
