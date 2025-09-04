package com.nextTasks.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.ColumnNotFoundException;
import com.nextTasks.exception.TaskExistException;
import com.nextTasks.exception.TaskNotFoundException;
import com.nextTasks.model.Column;
import com.nextTasks.model.Task;
import com.nextTasks.model.Tag;
import com.nextTasks.repository.ColumnRepository;
import com.nextTasks.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired 
    private TagService tagService;

    public List<Task> getTasksByColumn(Long columnId) {
        return taskRepository.findByColumnId(columnId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }


    public Task createTask(Task task) {
        // Comprobamos si existe el task a crear
        taskRepository.findByColumnIdAndTitle(task.getColumn().getId(), task.getTitle())
            .ifPresent( t -> {
                throw new TaskExistException(t.getTitle());
            });

        // Hay que comprobar si existe el tablero
        Column column = columnRepository.findById(task.getColumn().getId())
            .orElseThrow(ColumnNotFoundException::new);

        // Validar tags - solo mantener los que existen
        if (task.getTags() != null) {
            List<Tag> validatedTags = task.getTags().stream()
                .map(tag -> {
                    try {
                        return tagService.getTagById(tag.getId());
                    } catch (Exception e) {
                        return null; // Tag no existe, retornar null
                    }
                })
                .filter(tag -> tag != null) // Filtrar solo los tags válidos
                .toList(); 
            task.setTags(validatedTags);
        }
        task.setColumn(column);
        task.setCreationDate(LocalDate.now());

        return taskRepository.save(task);
    }


    public void addTagToTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Tag tag = tagService.getTagById(tagId);
        task.getTags().add(tag);
        taskRepository.save(task);
    }

    public void removeTagFromTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Tag tag = tagService.getTagById(tagId);
        task.getTags().remove(tag);
        taskRepository.save(task);
    }

    public Task updateTask(Long id, Task newTask) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);


        taskRepository.findByColumnAndTitle(task.getColumn(), task.getTitle())
            .ifPresent( t -> {
                if(!t.getId().equals(id)) {
                    throw new TaskExistException(t.getTitle());
                }
            });

        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setDueDate(newTask.getDueDate());
        task.setPriority(newTask.getPriority());
        task.setCompletionDate(newTask.getCompletionDate());
        
        // Validar tags - solo mantener los que existen
        if (newTask.getTags() != null) {
            List<Tag> validatedTags = newTask.getTags().stream()
                .map(tag -> {
                    try {
                        return tagService.getTagById(tag.getId());
                    } catch (Exception e) {
                        return null; // Tag no existe, retornar null
                    }
                })
                .filter(tag -> tag != null) // Filtrar solo los tags válidos
                .toList();
            task.setTags(validatedTags);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

    public Task setCompletionDate(Long id, LocalDate completionDate) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setCompletionDate(completionDate);
        return taskRepository.save(task);
    }

    public Task updateColumnTask(Long id, Long idColumn) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        Column column = columnRepository.findById(idColumn).orElseThrow(ColumnNotFoundException::new);

        taskRepository.findByColumnAndTitle(column, task.getTitle())
            .ifPresent( t -> {
                if(!t.getId().equals(id)) {
                    throw new TaskExistException(t.getTitle());
                }
            });


        task.setColumn(column);


        return taskRepository.save(task);
    }


}
