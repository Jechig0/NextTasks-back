package com.nextTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.DTO.DashBoardDataDTO;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.TaskRepository;

@Service
public class DashBoardService {
    // TODO filtrar por el usuario autenticado

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

   
    

    public DashBoardDataDTO getData() {
        DashBoardDataDTO data = new DashBoardDataDTO();
        data.setTotalBoards(getBoardsCount());
        data.setTotalTasks(getTasksCount());
        data.setCompletedTasks(getCompletedTasksCount());
        data.setPendingTasks(getTasksCount() - getCompletedTasksCount());
        data.setOverdueTasks(getOverdueTasksCount()); 
        data.setProductivityRate(calculateProductivityRate(data));
        return data;
    }

    private double calculateProductivityRate(DashBoardDataDTO data) {
        if (data.getTotalTasks() == 0) {
            return 0;
        }
        return (double) data.getCompletedTasks() / data.getTotalTasks() * 100;
    }

    private long getOverdueTasksCount() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getCompletionDate() == null && task.getDueDate().isBefore(java.time.LocalDate.now()))
                .count();
    }

    private long getTasksCount() {
        return taskRepository.count();
    }

    private long getCompletedTasksCount() {
        return taskRepository.countByCompletionDateNotNull();
    }

    private long getBoardsCount() {
        return boardRepository.count();
    }

}
