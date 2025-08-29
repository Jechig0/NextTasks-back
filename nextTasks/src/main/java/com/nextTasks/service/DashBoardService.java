package com.nextTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.TaskRepository;

@Service
public class DashBoardService {
    // TODO filtrar por el usuario autenticado

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

   
    public long getTasksCount() {
        return taskRepository.count();
    }

    public long getCompletedTasksCount() {
        return taskRepository.countByCompletionDateNotNull();
    }

    public long getBoardsCount() {
        return boardRepository.count();
    }

}
