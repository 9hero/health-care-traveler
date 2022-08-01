package com.healthtrip.travelcare.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class CommonUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String TIME_SEPARATOR = "_";
    public static String dateWithTypeIdGenerate(String type) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+type
                +((int)(Math.random()*10))
                +((int)(Math.random()*10))
                +((int)(Math.random()*10))
                +((int)(Math.random()*10));
    }
    public static String TESTdateWithTypeIdGenerate(String type) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+type
                +((int)(Math.random()*10));
    }

    public static String buildFileName(String originalFileName,String directory) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return directory + "/" + fileName + TIME_SEPARATOR + now + fileExtension;
    }

    public static Long getAuthenticatedUserId(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
