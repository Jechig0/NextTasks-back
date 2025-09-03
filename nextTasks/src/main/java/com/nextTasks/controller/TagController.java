package com.nextTasks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextTasks.DTO.ErrorResponseDTO;
import com.nextTasks.exception.TagExistException;
import com.nextTasks.exception.TagNotFoundException;
import com.nextTasks.model.Tag;
import com.nextTasks.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Obtener tags por owner ID
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getTagsByOwnerId(@PathVariable Long ownerId) {
        try {
            List<Tag> tags = tagService.getTagsByOwnerId(ownerId);
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching tags by owner", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Obtener tag por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable Long id) {
        try {
            Tag tag = tagService.getTagById(id);
            return ResponseEntity.ok(tag);
        } catch (TagNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Tag not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching tag", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Crear nuevo tag
    @PostMapping("")
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        try {
            Tag createdTag = tagService.createTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (TagExistException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Tag already exists", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error creating tag", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar tag
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            Tag updatedTag = tagService.updateTag(id, tag);
            return ResponseEntity.ok(updatedTag);
        } catch (TagNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Tag not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (TagExistException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Tag already exists", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error updating tag", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar tag
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.ok().build();
        } catch (TagNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Tag not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error deleting tag", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
