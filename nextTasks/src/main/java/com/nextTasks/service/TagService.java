package com.nextTasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.TagExistException;
import com.nextTasks.exception.TagNotFoundException;
import com.nextTasks.exception.TaskNotFoundException;
import com.nextTasks.model.Tag;
import com.nextTasks.model.User;
import com.nextTasks.repository.TagRepository;
import com.nextTasks.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    
    // Obtener tags por owner ID
    public List<Tag> getTagsByOwnerId(Long ownerId) {
        return tagRepository.findByOwnerId(ownerId);
    }

        // Obtener tag por ID
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));
    }

    // Crear nuevo tag
    public Tag createTag(Tag tag) {
        // Verificar si ya existe un tag con el mismo nombre
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new TagExistException("Tag already exists with name: " + tag.getName());
        }

        // Verificar que el owner existe
        if (tag.getOwner() != null && tag.getOwner().getId() != null) {
            User owner = userRepository.findById(tag.getOwner().getId())
                .orElseThrow(() -> new TaskNotFoundException("User not found with id: " + tag.getOwner().getId()));
            tag.setOwner(owner);
        }
        return tagRepository.save(tag);
    }

    // Actualizar tag
    public Tag updateTag(Long id, Tag newTag) {
        Tag existingTag = tagRepository.findById(id)
            .orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));

        // Verificar si ya existe otro tag con el mismo nombre (excluyendo el actual)
        tagRepository.findByName(newTag.getName())
            .ifPresent(tag -> {
                if (!tag.getId().equals(id)) {
                    throw new TagExistException("Tag already exists with name: " + newTag.getName());
                }
            });

        existingTag.setName(newTag.getName());
        existingTag.setColorCode(newTag.getColorCode());
        
        // Si se proporciona un nuevo owner, verificar que existe
        if (newTag.getOwner() != null && newTag.getOwner().getId() != null) {
            User owner = userRepository.findById(newTag.getOwner().getId())
                .orElseThrow(() -> new TaskNotFoundException("User not found with id: " + newTag.getOwner().getId()));
            existingTag.setOwner(owner);
        }

        return tagRepository.save(existingTag);
    }

    // Eliminar tag
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));
        tagRepository.delete(tag);
    }

    
}
