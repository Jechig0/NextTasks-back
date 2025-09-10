package com.nextTasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextTasks.DTO.DashBoardDataDTO;
import com.nextTasks.service.DashBoardService;

@RestController
@RequestMapping("dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping()
    public ResponseEntity<?> getTasksCount() {
        try {
            DashBoardDataDTO data = dashBoardService.getDashBoardData();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching tasks count");
        }
    }

}
