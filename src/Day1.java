/*
--- Day 1: Trebuchet?! ---

Something is wrong with global snow production, and you've been selected to take a look.
The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.

You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar;
the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

You try to ask why they can't just use a weather machine ("not powerful enough")
and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions")
and hang on did you just say the sky ("of course, where do you think snow comes from")
when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").

As they're making the final adjustments, they discover that their calibration document (your puzzle input)
has been amended by a very young Elf who was apparently just excited to show off her art skills.
Consequently, the Elves are having trouble reading the values on the document.

The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value
that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit
and the last digit (in that order) to form a single two-digit number.

For example:

1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet


In this example, the calibration values of these four lines are 12, 38, 15, and 77.
Adding these together produces 142.

--- Part Two ---
Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters:
one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".

Equipped with this new information, you now need to find the real first and last digit on each line. For example:

two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen

//[219, 8wo3, abc123xyz, x2ne34, 49872, z1ight234, 7pqrst6teen]

In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Day1 {

    public static String getFileAsString(String filePath) {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents.toString();
    }

    public static String[] getInputArray(String input) {
        // input = input.replaceAll("one", "1");
        // input = input.replaceAll("two", "2");
        // input = input.replaceAll("eightwo", "82");
        // input = input.replaceAll("eighthree", "83");
        // input = input.replaceAll("three", "3");
        // input = input.replaceAll("four", "4");
        // input = input.replaceAll("five", "5");
        // input = input.replaceAll("six", "6");
        // input = input.replaceAll("seven", "7");
        // input = input.replaceAll("eight", "8");
        // input = input.replaceAll("nine", "9");

        String[] words = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        // Create a regex pattern to match single words representing single-digit numbers
        Pattern pattern = Pattern.compile("(" + String.join("|", words) + ")");

        // Create a Matcher object for the input string
        Matcher matcher = pattern.matcher(input);

        // Iterate through matches
        while (matcher.find()) {
            // Get the matched value
            String matchedValue = matcher.group();

            // Determine the replacement value based on the matched word
            int digitValue = indexOfWord(words, matchedValue);

            // Perform the replacement
            input = input.substring(0, matcher.start()) + digitValue + input.substring(matcher.end() - 2);

            // Reset the matcher with the updated input
            matcher.reset(input);
        }

        return input.split("\n");
    }

    public static Integer getTotalSum(String[] lines) {
        int totalSum = 0;

        for (String line : lines) {
            char firstDigit = '\0';
            char lastDigit  = '\0';

            // get first and last digits
            // two1nine => 2t19
            for (char c : line.toCharArray()) {
                if (isDigit(c)) {
                    if (firstDigit == '\0') {
                        firstDigit = c;
                    }

                    lastDigit = c;
                }
            }

            // parse digits
            String numberOfTheLine = String.valueOf(firstDigit) + lastDigit;
            int valueToAdd = Integer.parseInt(numberOfTheLine);
            System.out.println(valueToAdd);
            totalSum += valueToAdd;
        }

        return totalSum;
    }

    private static int indexOfWord(String[] array, String word) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(word)) {
                return i;
            }
        }
        return -1; // Not found
    }

    public static void main(String[] args) throws Exception {
        String foo = getFileAsString("input/Day1");
        String[] fooArray = getInputArray(foo);
        System.out.println(getTotalSum(fooArray));
    }
}
