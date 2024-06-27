package vn.com.dpm.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;


public final class CurrencyUtils {

    public static final String PATTERN_MONEY_FORMAT = "#,###";

    private CurrencyUtils() {
    }

    /**
     * Format money using Long parser for integer fraction
     * 1000.0 => ["1000","0"]
     *
     * @param inputMoney input.
     * @return formatted money.
     */
    public static String formatCurrency(String inputMoney) {
        if (StringUtils.isEmpty(inputMoney)) return "";
        var fractions = inputMoney.split("\\.");
        if (fractions.length < 2) {
            return formatMoney(inputMoney, ",");
        }
        var decimalFraction = StringUtils.stripEnd(fractions[1], "0");
        if (decimalFraction.isEmpty()) { // 1.0 -> 1
            return formatMoney(fractions[0], ",");
        }
        return String.format("%s.%s", formatMoney(fractions[0], ","), decimalFraction);
    }

    public static String formatVieCurrency(String inputMoney) {
        return formatCurrency(inputMoney, ".", ",");
    }

    /**
     * Format money using Long parser for integer fraction
     * 1000.0 => ["1000","0"]
     *
     * @param inputMoney input.
     * @return formatted money.
     */
    public static String formatCurrency(String inputMoney, String delim, String fractionDelim) {
        if (StringUtils.isEmpty(inputMoney)) return "";
        var fractions = inputMoney.split("\\.");
        if (fractions.length < 2) {
            return formatMoney(inputMoney, delim);
        }
        var decimalFraction = StringUtils.stripEnd(fractions[1], "0");
        if (decimalFraction.isEmpty()) { // 1.0 -> 1
            return formatMoney(fractions[0], delim);
        }
        return String.format("%s%s%s", formatMoney(fractions[0], delim), fractionDelim, decimalFraction);
    }

    /**
     * Format money using Regex.
     *
     * @param inputMoney input.
     * @param delim      delimiter.
     * @return formatted money.
     */
    public static String formatMoney(final String inputMoney, String delim) {
        if (StringUtils.isEmpty(inputMoney)) return "";
        Pattern pattern = Pattern.compile("\\B(?<!\\.\\d.)(?=(\\d{3})+(?!\\d))");
        return pattern.matcher(inputMoney).replaceAll(delim);
    }

    /**
     * Format money.
     *
     * @param inputMoney input.
     * @return formatted money.
     */
    public static String formatMoney(Long inputMoney) {
        var formatter = new DecimalFormat(PATTERN_MONEY_FORMAT);
        return formatter.format(inputMoney);
    }

    public static String getLongValueAsString(String value) {
        if (StringUtils.isEmpty(value) || isNotNumeric(value)) {
            return StringUtils.EMPTY;
        }

        BigDecimal bigDecimal = new BigDecimal(value);
        var intValue = bigDecimal.longValue();
        return String.valueOf(intValue);
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isNotNumeric(String strNum) {
        return !isNumeric(strNum);
    }
}
