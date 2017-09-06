/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Log;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorVisualizarLogController extends ControladorPai{
    @FXML
    private ComboBox mes;
    @FXML
    private TextField nome;
    @FXML
    private TableView<Log> tabelaLog;
    @FXML
    private TableColumn<Log,String> colunaNome;
    @FXML
    private TableColumn<Log,String> colunaData;
    @FXML
    private TableColumn<Log,String> colunaHora;
    @FXML
    private TableColumn<Log,String> colunaDescricao;
    private ObservableList<Log> logs;
    
    @Override
    public void atualiza(){
        mes.setItems(FXCollections.observableArrayList(Log.getMeses()));
        mes.getSelectionModel().selectFirst();
        logs = FXCollections.observableArrayList(Log.buscarLogPorMesFuncionario("", mes.getValue().toString()));
        tabelaLog.setItems(logs);
        nome.setText("");
    }
    
    public void buscar(){
        logs = FXCollections.observableArrayList(Log.buscarLogPorMesFuncionario(nome.getText(), mes.getValue().toString()));
        tabelaLog.setItems(logs);
    }
    
    public void imprimir(){
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeFuncionarioProperty());
        colunaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        colunaHora.setCellValueFactory(cellData -> cellData.getValue().horaProperty());
        colunaDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
    }    
    
}
