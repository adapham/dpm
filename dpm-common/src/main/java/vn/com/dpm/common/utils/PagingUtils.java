package vn.com.dpm.common.utils;


public final class PagingUtils {

    private PagingUtils() {
    }


    /**
     * Call off set base on pages ize, page index.
     */
    public static int calOffset(int pageIndex, int pageSize) {
        return pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
    }
}
