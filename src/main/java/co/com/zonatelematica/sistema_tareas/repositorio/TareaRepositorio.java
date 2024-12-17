package co.com.zonatelematica.sistema_tareas.repositorio;

import co.com.zonatelematica.sistema_tareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository <Tarea, Integer> {
}