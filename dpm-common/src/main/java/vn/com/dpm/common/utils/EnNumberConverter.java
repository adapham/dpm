package vn.com.dpm.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnNumberConverter {
    private static final String THOUSAND = "thousand";
    private static final String HUNDRED = "hundred";
    private static final String MILLION = "million";
    private static final String BILLION = "billion";
    private static final String SPACE = " ";
    public static final String ZERO = "0";
    public static final String ONE = "1";

    private static final Map<String, String> numbers = new HashMap<>();

    static {
        numbers.put("1", "one");
        numbers.put("2", "two");
        numbers.put("3", "three");
        numbers.put("4", "four");
        numbers.put("5", "five");
        numbers.put("6", "six");
        numbers.put("7", "seven");
        numbers.put("8", "eight");
        numbers.put("9", "nine");
        numbers.put("10", "ten");
        numbers.put("11", "eleven");
        numbers.put("12", "twelve");
        numbers.put("13", "thirteen");
        numbers.put("14", "fourteen");
        numbers.put("15", "fifteen");
        numbers.put("16", "sixteen");
        numbers.put("17", "seventeen");
        numbers.put("18", "eighteen");
        numbers.put("19", "nineteen");
        numbers.put("20", "twenty");
        numbers.put("30", "thirty");
        numbers.put("40", "forty");
        numbers.put("50", "fifty");
        numbers.put("60", "sixty");
        numbers.put("70", "seventy");
        numbers.put("80", "eighty");
        numbers.put("90", "ninety");
    }

    private EnNumberConverter() {
    }

    /**
     * Examples: "splitEvery3Characters("1234567")" return List<String> {"567", "234", "1"}
     */
    private static List<String> splitEveryThreeCharacters(String input) {
        List<String> output = new ArrayList<>();
        if (input.length() < 3) {
            output.add(input);
            return output;
        }

        while (input.length() > 3) {
            int lastThreeCharPosition = input.length() - 3;

            String lastThreeChar = input.substring(lastThreeCharPosition);

            output.add(lastThreeChar);

            input = input.substring(0, lastThreeCharPosition);
        }

        if (input.length() > 0) {
            output.add(input);
        }

        return output;
    }

    private static String convertThreeNumberIntoEnglish(String input) {
        switch (input.length()) {
            case 0:
                return input;
            case 1:
                return numbers.get(input);
            case 2:
                return processConvertTwoNumberIntoEnglish(input);
            case 3:
                return processConvertThreeNumberIntoEnglish(input);
            default:
                return "";
        }
    }

    /**
     * Examples:
     * "processConvertTwoNumberIntoEnglish("00")" return ""}
     * "processConvertTwoNumberIntoEnglish("02")" return "two"}
     * "processConvertTwoNumberIntoEnglish("12")" return "twelve"}
     * "processConvertTwoNumberIntoEnglish("22")" return "twenty-two"}
     */
    private static String processConvertTwoNumberIntoEnglish(String input) {
        String tens = input.substring(0, 1);
        String ones = input.substring(1, 2);

        if (ZERO.equals(tens) && ZERO.equals(ones)) {
            return "";
        }

        if (ZERO.equals(tens)) {
            return numbers.get(ones);
        }

        if (ONE.equals(tens)) {
            return numbers.get(tens + ones);
        }

        tens = tens + ZERO;
        StringBuilder output = new StringBuilder();
        output.append(numbers.get(tens));

        if (ZERO.equals(ones)) return output.toString();
        output.append("-");
        output.append(numbers.get(ones));
        return output.toString();
    }

    /**
     * Examples:
     * "processConvertThreeNumberIntoEnglish("022")" return "twenty two"
     * "processConvertThreeNumberIntoEnglish("100")" return "one hundred"
     * "processConvertThreeNumberIntoEnglish("102")" return "one hundred two"
     * "processConvertThreeNumberIntoEnglish("112")" return "one hundred twelve"
     * "processConvertThreeNumberIntoEnglish("122")" return "one hundred twenty-two"
     */
    private static String processConvertThreeNumberIntoEnglish(String input) {
        String tens = input.substring(1, 2);
        String ones = input.substring(2, 3);
        String tensAndOnes = processConvertTwoNumberIntoEnglish(tens + ones);

        String hundreds = input.substring(0, 1);
        if (ZERO.equals(hundreds)) return tensAndOnes;

        StringBuilder output = new StringBuilder();
        output.append(numbers.get(hundreds));
        output.append(SPACE);
        output.append(HUNDRED);

        if (StringUtils.isNotBlank(tensAndOnes)) {
            output.append(SPACE);
            output.append(tensAndOnes);
        }

        return output.toString();
    }

    /**
     * Examples:
     * convertNumberToEnglish("1234567890") return "One billion two hundred thirty-four million five hundred sixty-seven thousand eight hundred ninety"
     */
    public static String convertNumberToEnglish(String input) {
        String inputPreProcessed = preProcessData(input);

        List<String> listThreeChar = splitEveryThreeCharacters(inputPreProcessed);

        // it looks like hard code because the values we get are just numbers up to billions,
        // can be refactored to be more flexible if time permits
        StringBuilder output = new StringBuilder();
        while (CollectionUtil.isNotEmpty(listThreeChar)) {
            switch (listThreeChar.size()) {
                case 1:
                    processConvert(output, listThreeChar, "");
                    break;
                case 2:
                    processConvert(output, listThreeChar, THOUSAND);
                    break;
                case 3:
                    processConvert(output, listThreeChar, MILLION);
                    break;
                default:
                    processConvert(output, listThreeChar, BILLION);
                    break;
            }
            listThreeChar.remove(listThreeChar.size() - 1);
        }

        return upperFirstLetter(output.toString());
    }

    /**
     * upperFirstLetter("one two three") return "One two three"
     */
    private static String upperFirstLetter(String input) {
        input = input.trim();

        String firstLetter = input.substring(0, 1);
        String remainLetter = input.substring(1);

        firstLetter = firstLetter.toUpperCase();
        return firstLetter + remainLetter;
    }

    /**
     * preProcessData(155000.00) return 155000
     */
    private static String preProcessData(String input) {
        if (input.contains(".")) {
            return input.substring(0, input.indexOf("."));
        }

        return input;
    }

    private static void processConvert(StringBuilder output, List<String> listThreeChar, String suffix) {
        output.append(SPACE);
        String firstThreeNumberConverted = convertThreeNumberIntoEnglish(listThreeChar.get(listThreeChar.size() - 1));
        output.append(firstThreeNumberConverted);
        output.append(SPACE);
        output.append(suffix);
    }
}
