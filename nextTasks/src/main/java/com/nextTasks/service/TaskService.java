package com.nextTasks.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.TableNotFoundException;
import com.nextTasks.exception.TaskExistException;
import com.nextTasks.exception.TaskNotFoundException;
import com.nextTasks.model.Board;
import com.nextTasks.model.Task;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    public List<Task> getTasksByTable(Long tableroId) {
        return taskRepository.findByBoardId(tableroId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }


    public Task createTask(Task task) {
        // Comprobamos si existe el task a crear
        taskRepository.findByBoardIdAndTitle(task.getBoard().getId(), task.getTitle())
            .ifPresent( t -> {
                throw new TaskExistException(t.getTitle());
            });

        // Hay que comprobar si existe el tablero
        Board t = boardRepository.findById(task.getBoard().getId())
            .orElseThrow(TableNotFoundException::new);

        task.setBoard(t);
        task.setCreationDate(LocalDate.now());

        return taskRepository.save(task);
    }


    public Task updateTask(Long id, Task newTask) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);


        taskRepository.findByBoardAndTitle(task.getBoard(), task.getTitle())
            .ifPresent( t -> {
                if(!t.getId().equals(id)) {
                    throw new TaskExistException(t.getTitle());
                }
            });

        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setStatus(newTask.getStatus());
        task.setDueDate(newTask.getDueDate());
        task.setPriority(newTask.getPriority());
        task.setCompletionDate(newTask.getCompletionDate());
        task.setTags(newTask.getTags());

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


}
