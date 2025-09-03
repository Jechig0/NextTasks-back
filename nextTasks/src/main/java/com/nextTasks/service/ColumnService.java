package com.nextTasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.BoardNotFoundException;
import com.nextTasks.exception.ColumnNotFoundException;
import com.nextTasks.model.Board;
import com.nextTasks.model.Column;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.ColumnRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private BoardRepository boardRepository;
    
    public List<Column> getColumnsByBoardId(Long boardId){
        return columnRepository.findByBoardId(boardId);
    }

    public Column getColumnByIdAndBoardId(Long id, Long boardId) {
        Column column = columnRepository.findByIdAndBoardId(id, boardId);
        if(column == null){
            throw new ColumnNotFoundException("Column not found with id: " + id + " in board with id: " + boardId);
        }
        return column;
    }

    public Column createColumn(Long boardId, Column column){
        if(column.getBoard() != null && column.getBoard().getId() != null){
            // Lazy loading - Cargar el board completo desde la base de datos
           Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board not found with id: " + column.getBoard().getId()));
            column.setBoard(board);
        }
        return columnRepository.save(column);
    }

    public Column updateColumn(Long id, Long boardId, Column newColumn) {
        Column existingColumn = columnRepository.findByIdAndBoardId(id, boardId);
        if(existingColumn == null){
            throw new ColumnNotFoundException("Column not found with id: " + id + " in board with id: " + boardId);
        }

        existingColumn.setName(newColumn.getName());
        // Si se desea actualizar el board, se puede agregar lógica aquí

        return columnRepository.save(existingColumn);

    }

    public void deleteColumn(Long id, Long boardId) {
        Column existingColumn = columnRepository.findByIdAndBoardId(id, boardId);
        if(existingColumn == null){
            throw new ColumnNotFoundException("Column not found with id: " + id + " in board with id: " + boardId);
        }
        columnRepository.delete(existingColumn);
    }


}
