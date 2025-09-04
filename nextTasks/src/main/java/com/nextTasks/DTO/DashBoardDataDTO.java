package com.nextTasks.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashBoardDataDTO {

    // 1. Total de tableros del usuario
    private Long totalBoards;
    
    // 2. Total de tareas
    private Long totalTasks;
    
    // 3. Tareas completadas
    private Long completedTasks;
    
    // 4. Tareas pendientes
    private Long pendingTasks;
    
    // 5. Tareas vencidas (overdue)
    private Long overdueTasks;
    
    // 6. Porcentaje de productividad (tareas completadas del total)
    private double productivityRate; // 0-100
    
}
