package vn.com.dpm.common.utils;

/**
 * @author an.cantuong
 */
public final class UserIDUtils {

    public static final String IPA_DOMAIN = "ipa\\";
    public static final String IPA_DOMAIN_UNDERSCORE = "ipa_";

    private UserIDUtils() {
    }

    /**
     * format username has domain from ipa\\username -> ipa_username.
     *
     * @param username username.
     * @return String.
     */
    public static String formatDomainUsername(String username) {
        return StringUtils.hasLength(username)
                ? username.replace(IPA_DOMAIN, IPA_DOMAIN_UNDERSCORE)
                : null;
    }

    /**
     * Get formatted username as splash format.
     *
     * @return username with ipa_ -> ipa\\
     */
    public static String reformatToDomainUsername(String formattedUsername) {
        return StringUtils.hasLength(formattedUsername)
                ? formattedUsername.replace(IPA_DOMAIN_UNDERSCORE, IPA_DOMAIN)
                : null;
    }

    /**
     * Get formatted username as splash format.
     *
     * @return username with ipa_xxx -> xxx
     */
    public static String removeDomain(String username) {
        return StringUtils.hasLength(username)
                ? username.replace(IPA_DOMAIN, "")
                : null;
    }

    /**
     * Add domain ipa_ to username if not present.
     *
     * @param username username.
     */
    public static String addDomainUnderscore(String username) {
        return StringUtils.hasLength(username) && !username.startsWith(IPA_DOMAIN_UNDERSCORE)
                ? IPA_DOMAIN_UNDERSCORE + username
                : username;
    }
}
