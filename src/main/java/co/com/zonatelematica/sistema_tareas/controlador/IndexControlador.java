package co.com.zonatelematica.sistema_tareas.controlador;

import co.com.zonatelematica.sistema_tareas.modelo.Tarea;
import co.com.zonatelematica.sistema_tareas.servicio.TareaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;

@Component
public class IndexControlador implements Initializable {
    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private TareaServicio tareaServicio;


    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea , Integer> idTareaColumna;

    @FXML
    private TableColumn<Tarea , String> nombreTareaColumna;

    @FXML
    private TableColumn<Tarea , String> responsableColumna;

    @FXML
    private TableColumn<Tarea , String> statusColumna;

    private  final ObservableList<Tarea> tareaList =
            FXCollections.observableArrayList();

    @FXML
    private TextField nombreTareaTexto;

    @FXML
    private TextField responsableTexto;

    @FXML
    private TextField statusTexto;

    private Integer idTareaInterno;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }
    private void configurarColumnas(){
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        statusColumna.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void listarTareas(){
        logger.info("Ejecutando lista de tareas");
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareaList);
    }

    public void agregarTarea(){
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error de validadcion" , "Debe registrar una tarera");
            nombreTareaTexto.requestFocus();
            return;
        }
        else {
            var tarea = new Tarea();
            recolectarDatosFormularios(tarea);
            tarea.setIdTarea(null);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion",
                    "La tarea se agrego satisfactoriamemte ");
            limpiarFormulario();
            listarTareas();
        }
    }

    public void cargarTareaFormulario(){
       var tarea =
               tareaTabla.getSelectionModel().getSelectedItem();
       if(tarea != null){
           idTareaInterno = tarea.getIdTarea();
           nombreTareaTexto.setText(tarea.getNombreTarea());
           responsableTexto.setText(tarea.getResponsable());
           statusTexto.setText(tarea.getStatus());
       }
    }

    private void recolectarDatosFormularios(Tarea tarea){
        if(idTareaInterno != null)
            tarea.setIdTarea(idTareaInterno);
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setStatus(statusTexto.getText());
    }

    public void modificarTarea(){
        if(idTareaInterno == null){
            mostrarMensaje("Informacion" , "Debe seleccionar una tarea");
            return;
        }
        if(nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion" , "Debe ingresar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        var tarea = new Tarea();
        recolectarDatosFormularios(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Informacion" ,
                "La tarea se modifica exitosamente");
        limpiarFormulario();
        listarTareas();
    }

    public void eliminarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null){
            logger.info("Registro a eliminar: " + tarea.toString());
            tareaServicio.eliminarTarea(tarea);
            mostrarMensaje("Informacion" ,
                    "La tarea se elimina exitosamente:" + tarea.getIdTarea() );
            limpiarFormulario();
            listarTareas();
        }
        else {
            mostrarMensaje("Error" , "No se ha seleccionado tarea");
        }

    }

    public void limpiarFormulario(){
        idTareaInterno = null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        statusTexto.clear();

    }

    private void mostrarMensaje(String titulo , String mensaje){
        Alert alerta = new  Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
