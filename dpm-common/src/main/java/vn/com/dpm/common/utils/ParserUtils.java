package vn.com.dpm.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public final class ParserUtils {

    private static final String PARSER_ERROR = "Parser error, msg: {}";

    private ParserUtils() {
    }

    public static Optional<Long> parseLong(Object num) {
        return parseLong(String.valueOf(num));
    }

    public static Long parseLong(String num, Long defaultVal) {
        return parseLong(num).orElse(defaultVal);
    }

    public static Optional<Long> parseLong(String num) {
        try {
            if (StringUtils.hasLength(num)) {
                return Optional.of(Long.parseLong(num));
            }
        } catch (NumberFormatException e) {
            log.error(PARSER_ERROR, e.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<Integer> parseInt(String num) {
        try {
            if (StringUtils.hasLength(num)) {
                return Optional.of(Integer.parseInt(num));
            }
        } catch (NumberFormatException e) {
            log.error(PARSER_ERROR, e.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<Double> parseDouble(String num) {
        try {
            if (StringUtils.hasLength(num)) {
                return Optional.of(Double.parseDouble(num));
            }
        } catch (NumberFormatException e) {
            log.error(PARSER_ERROR, e.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<Float> parseFloat(String num) {
        try {
            if (StringUtils.hasLength(num)) {
                return Optional.of(Float.parseFloat(num));
            }
        } catch (NumberFormatException e) {
            log.error(PARSER_ERROR, e.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<BigDecimal> parseBigDecimal(String num) {
        try {
            if (StringUtils.hasLength(num)) {
                return Optional.of(new BigDecimal(num));
            }
        } catch (ArithmeticException | NumberFormatException e) {
            log.error(PARSER_ERROR, e.getMessage());
        }
        return Optional.empty();
    }
}
