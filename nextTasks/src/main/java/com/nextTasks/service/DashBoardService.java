package com.nextTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.DTO.DashBoardDataDTO;
import com.nextTasks.model.Board;
import com.nextTasks.model.Task;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

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

    public DashBoardDataDTO getDashBoardData(Long ownerId) {
        // Obtener solo boards activos
        List<Board> activeBoards = boardRepository.findByOwnerIdAndActive(ownerId, true);
        long totalBoards = activeBoards.size();

        // Obtener todas las tareas del owner
        List<Task> allTasks = taskRepository.findByColumnBoardOwnerId(ownerId);
        
        // Filtrar solo tareas de boards activos y contar en Java
        long totalTasks = allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .count();

        long completedTasks = allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .filter(task -> task.getCompletionDate() != null)
                .count();

        long overdueTasks = allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now()))
                .filter(task -> task.getCompletionDate() == null)
                .count();

        long pendingTasks = totalTasks - completedTasks;
        double productivityRate = totalTasks == 0 ? 0 : (double) completedTasks / totalTasks * 100;

        return new DashBoardDataDTO(
                totalBoards,
                totalTasks,
                completedTasks,
                pendingTasks,
                overdueTasks,
                productivityRate
        );
    }

    private double calculateProductivityRate(DashBoardDataDTO data) {
        if (data.getTotalTasks() == 0) {
            return 0;
        }
        return (double) data.getCompletedTasks() / data.getTotalTasks() * 100;
    }

    private long getOverdueTasksCount() {
        // Obtener todas las tareas y filtrar en Java
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now()))
                .filter(task -> task.getCompletionDate() == null)
                .count();
    }

    private long getTasksCount() {
        // Obtener todas las tareas y filtrar en Java
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .count();
    }

    private long getCompletedTasksCount() {
        // Obtener todas las tareas y filtrar en Java
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(task -> task.getColumn().getBoard().getActive())
                .filter(task -> task.getCompletionDate() != null)
                .count();
    }

    private long getBoardsCount() {
        // Obtener todos los boards y filtrar en Java
        List<Board> allBoards = boardRepository.findAll();
        return allBoards.stream()
                .filter(Board::getActive)
                .count();
    }

}
