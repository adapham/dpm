package vn.com.dpm.common.utils;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public final class HttpUtils {


    private HttpUtils() {
    }

    /**
     * Get response string.
     *
     * @param in in.
     * @return String.
     * @throws IOException ex.
     */
    public static String getResponseStr(InputStream in) throws IOException {
        var writer = new StringWriter();
        IOUtils.copy(in, writer, StandardCharsets.UTF_8.toString());
        return writer.toString();
    }

    /**
     * Convert from multipart file to base 64.
     *
     * @param imageFile image file.
     * @return String.
     */
    public static String multipartImageToBase64(MultipartFile imageFile) throws IOException {
        return Base64.getEncoder().encodeToString(imageFile.getBytes());
    }
}
