package vn.com.dpm.common.utils;

import org.springframework.lang.NonNull;

import java.text.Normalizer;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * @author an.cantuong
 */
public final class StringUtils {

    public static final String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || EMPTY.equals(obj);
    }

    public static boolean isEmpty(String txt) {
        return txt == null || txt.isEmpty();
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasText(String txt) {
        return !isEmpty(txt) && containsText(txt);
    }

    public static boolean equals(String txt1, String txt2) {
        if (txt1 == null) return txt2 == null;

        return txt1.equals(txt2);
    }

    public static boolean notEquals(String txt1, String txt2) {
        return !equals(txt1, txt2);
    }

    public static boolean equalsIgnoreCase(String txt1, String txt2) {
        if (txt1 == null) return txt2 == null;

        return txt1.equalsIgnoreCase(txt2);
    }

    public static boolean endWithIgnoreCase(String txt, String suffix) {
        return txt != null && suffix != null && txt.toLowerCase().endsWith(suffix.toLowerCase());
    }

    public static boolean startWithIgnoreCase(String txt, String prefix) {
        return txt != null && prefix != null && txt.toLowerCase().startsWith(prefix.toLowerCase());
    }

    public static boolean endWith(String txt, String suffix) {
        return txt != null && txt.endsWith(suffix);
    }

    public static boolean startWith(String txt, String prefix) {
        return txt != null && txt.startsWith(prefix);
    }

    public static boolean containAny(String txt, String contain) {
        return txt != null && txt.contains(contain);
    }

    public static boolean containAnyIgnoreCase(String txt, String contain) {
        return txt != null && contain != null && txt.toLowerCase().contains(contain.toLowerCase());
    }

    public static String toLowercase(String txt) {
        return txt != null ? txt.toLowerCase() : null;
    }

    public static String toUppercase(String txt) {
        return txt != null ? txt.toUpperCase() : null;
    }

    public static String[] split(String txt, String regex) {
        return txt != null ? txt.split(regex) : new String[]{};
    }

    public static String valueOf(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }

    public static String strip(String txt) {
        return txt != null ? txt.strip() : null;
    }

    public static boolean hasLength(String txt) {
        return txt != null && !txt.isEmpty();
    }

    public static boolean hasLength(CharSequence txt) {
        return txt != null && txt.length() > 0;
    }

    public static String lastMatch(String txt, String match) {
        if (hasLength(txt)) {
            var index = txt.lastIndexOf(match);

            return index > 0 ? txt.substring(index + 1) : EMPTY;
        }
        return EMPTY;
    }

    public static String join(List<String> list, @NonNull String delim) {
        if (list == null) return null;
        var builder = new StringBuilder(list.get(0));

        for (var str : list.subList(1, list.size())) {
            builder.append(delim).append(str);
        }

        return builder.toString();
    }

    public static String join(Set<String> set, @NonNull String delim) {
        var joiner = new StringJoiner(delim);
        for (var str : set) {
            joiner.add(str);
        }
        return joiner.toString();
    }

    public static String removeWhiteSpace(String txt) {
        return hasLength(txt) ? txt.replaceAll("\\s+", EMPTY) : txt;
    }

    /**
     * Convert Vietnamese with accents to without accents.
     *
     * @param txt string with accents.
     * @return string without accents.
     */
    public static String removeAccent(String txt) {
        if (StringUtils.isEmpty(txt)) return txt;

        var normTxt = Normalizer.normalize(txt, Normalizer.Form.NFD);
        var pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normTxt).replaceAll(EMPTY);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
