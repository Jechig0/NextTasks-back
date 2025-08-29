package com.nextTasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.nextTasks.service.DashBoardService;

public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/tasks/count")
    public ResponseEntity<?> getTasksCount() {
        try {
            long count = dashBoardService.getTasksCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching tasks count");
        }
    }

}
