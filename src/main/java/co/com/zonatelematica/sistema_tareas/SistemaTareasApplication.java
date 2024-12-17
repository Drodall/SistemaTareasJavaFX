package co.com.zonatelematica.sistema_tareas;

import co.com.zonatelematica.sistema_tareas.presentacion.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaTareasApplication {

	public static void main(String[] args) {

		//SpringApplication.run(SistemaTareasApplication.class, args);
		Application.launch(SistemaTareasFx.class , args);
	}

}
