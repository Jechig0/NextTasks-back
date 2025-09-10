package com.nextTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nextTasks.DTO.DashBoardDataDTO;
import com.nextTasks.model.Board;
import com.nextTasks.model.Task;
import com.nextTasks.model.User;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.TaskRepository;
import com.nextTasks.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DashBoardService {
    // TODO filtrar por el usuario autenticado

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired 
    private UserRepository userRepository;


    public DashBoardDataDTO getDashBoardData() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Obtener solo boards activos

        Optional<User> userOpt = userRepository.findByUsername(auth.getName());

        Long ownerId = userOpt.map(User::getId).orElse(null);

        List<Board> activeBoards = boardRepository.findByOwnerIdAndActive(ownerId, true);
        

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
                (long) activeBoards.size(),
                totalTasks,
                completedTasks,
                pendingTasks,
                overdueTasks,
                productivityRate
        );
    }

}
