package vn.com.dpm.main.api.contrller.files;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.apache.commons.lang3.ClassUtils.getName;

@RestController
public class FilesController {

    private static final int BUFFER_SIZE = 4096;

    @GetMapping("/unzip")
    public String unzip(Authentication authentication) throws IOException {

        String base64ZipFile = "";
        Map<String, String> base64Entries = new LinkedHashMap<>();
        try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(base64ZipFile)))) {
            Base64.Encoder encoder = Base64.getEncoder();
            for (ZipEntry entry; (entry = zipIn.getNextEntry()) != null; ) {
                base64Entries.put(entry.getName(), encoder.encodeToString(zipIn.readAllBytes()));
            }
        }
        return "Welcome to this very private page, ~[" + getName(authentication) + "]~! 🥳🎉🍾";
    }
}
