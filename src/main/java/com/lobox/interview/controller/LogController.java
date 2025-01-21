package com.lobox.interview.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    private static final String LOG_FILE_PATH = "logs/application.log";

    @GetMapping
    public ResponseEntity<String> getLogs() {
        try {
            Path logFilePath = Paths.get(LOG_FILE_PATH);
            if (!Files.exists(logFilePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log file not found.");
            }

            String logContent = Files.readString(logFilePath);
            return ResponseEntity.ok(logContent);
        } catch (IOException e) {
            log.error("Error reading log file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading log file.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadLogs() {
        Path logFilePath = Paths.get(LOG_FILE_PATH);
        if (!Files.exists(logFilePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Resource resource = new FileSystemResource(logFilePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application.log")
                .body(resource);
    }
}
//http://localhost:8080/api/logs
//http://localhost:8080/api/logs/download
