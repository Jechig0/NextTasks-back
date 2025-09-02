package com.nextTasks.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextTasks.DTO.ErrorResponseDTO;
import com.nextTasks.exception.TableNotFoundException;
import com.nextTasks.exception.TaskExistException;
import com.nextTasks.exception.TaskNotFoundException;
import com.nextTasks.model.Task;
import com.nextTasks.service.TaskService;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTasksFromTable(@PathVariable Long id) {
        try {

            return ResponseEntity.ok(taskService.getTasksByTable(id));
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching tasks", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {

        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (TaskExistException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task already exists", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (TableNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Table not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error creating task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (TaskExistException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task already exists", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error updating task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error deleting task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/{id}/completionDate")
    public ResponseEntity<?> setCompletionDate(@PathVariable Long id, @RequestBody LocalDate completionDate) {
        try {
            Task updatedTask = taskService.setCompletionDate(id, completionDate);
            return ResponseEntity.ok(updatedTask);
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error setting completion date", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/{id}/addTag/{idTag}")
    public ResponseEntity<?> addTagToTask(@PathVariable Long id, @PathVariable Long idTag) {
        try {
            taskService.addTagToTask(id, idTag);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error adding tag to task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/{id}/removeTag/{idTag}")
    public ResponseEntity<?> removeTagFromTask(@PathVariable Long id, @PathVariable Long idTag) {
        try {
            taskService.removeTagFromTask(id, idTag);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Task not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error adding tag to task", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
