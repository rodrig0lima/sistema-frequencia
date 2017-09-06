/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Mes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorListarFrequenciasPorMesController extends ControladorPai{
    @FXML
    private TableView<Mes> tabelaMeses;
    @FXML
    private TableColumn<Mes,String> colunaMes;
    @FXML
    private TableColumn<Mes,String> colunaQtFuncionarios;
    private ObservableList<Mes> meses;
    @Override
    public void atualiza(){
        meses = FXCollections.observableArrayList(Mes.buscarMeses());
        tabelaMeses.setItems(meses);
    }
    public void visualizarMes(){
        final ObservableList<TablePosition> selectedCells = tabelaMeses.getSelectionModel().getSelectedCells();
        TablePosition pos = selectedCells.get(0);
        Mes mes = meses.get(pos.getRow());
        myController.getController(Principal.administradorRelatorioMensalID).setMes(mes.getMes()+"/"+mes.getAno());
        administradorRelatorioMensal();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaMes.setCellValueFactory(cellData -> cellData.getValue().mes());
        colunaQtFuncionarios.setCellValueFactory(cellData -> cellData.getValue().qtFuncionarios());
    }    
    
}
