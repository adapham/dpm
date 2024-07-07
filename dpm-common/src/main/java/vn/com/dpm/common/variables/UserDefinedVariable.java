package vn.com.dpm.common.variables;

public interface UserDefinedVariable {
    /**
     * Define variable name.
     */
    String varName();

    /**
     * Make variable visible for search.
     */
    default boolean visibleSearch() {
        return false;
    }

    /**
     * Mark variable stored or not.
     */
    default boolean stored() {
        return true;
    }
}
