package vn.com.dpm.common.utils;

import java.math.BigDecimal;


public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    /**
     * check the input decimal number is greater than 0.
     *
     * @param decimal input decimal number.
     * @return true if gt.
     */
    public static boolean gtZero(BigDecimal decimal) {
        return gt(decimal, BigDecimal.ZERO);
    }

    /**
     * check the input decimal number is greater than or equal 0.
     *
     * @param decimal input decimal number.
     * @return true if gte.
     */
    public static boolean gteZero(BigDecimal decimal) {
        return gte(decimal, BigDecimal.ZERO);
    }

    /**
     * check the input decimal number is less than 0.
     *
     * @param decimal input decimal number.
     * @return true if lt.
     */
    public static boolean ltZero(BigDecimal decimal) {
        return lt(decimal, BigDecimal.ZERO);
    }

    /**
     * check the input decimal number is less than or equal 0.
     *
     * @param decimal input decimal number.
     * @return true if lte.
     */
    public static boolean lteZero(BigDecimal decimal) {
        return lte(decimal, BigDecimal.ZERO);
    }

    /**
     * check the <code>first</code> decimal number is less than <code>second</code>.
     *
     * @param first  input decimal number.
     * @param second input compare number.
     * @return true if lt.
     */
    public static boolean lt(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    /**
     * check the <code>first</code> decimal number is less than or equal <code>second</code>.
     *
     * @param first  input decimal number.
     * @param second input compare number.
     * @return true if lte.
     */
    public static boolean lte(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) <= 0;
    }

    /**
     * check the <code>first</code> decimal number is greater than <code>second</code>.
     *
     * @param first  input decimal number.
     * @param second input compare number.
     * @return true if gt.
     */
    public static boolean gt(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    /**
     * check the <code>first</code> decimal number is greater than or equal <code>second</code>.
     *
     * @param first  input decimal number.
     * @param second input compare number.
     * @return true if gte.
     */
    public static boolean gte(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) >= 0;
    }

    /**
     * check the <code>first</code> decimal number is equal <code>second</code>.
     *
     * @param first  input decimal number.
     * @param second input compare number.
     * @return true if eq.
     */
    public static boolean eq(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }
}
