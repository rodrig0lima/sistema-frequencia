/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorRelatorioMensalController extends ControladorPai{
    private String mes;
    
    @FXML
    private Label labelMes;
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;
    @FXML
    private TableColumn<Funcionario,String> colunaNome;
    @FXML
    private TableColumn<Funcionario,String> colunaTotalHoras;
    private ObservableList<Funcionario> funcionarios;
    @Override
    public void setMes(String mes){
        this.mes = mes;
    }
    
    @Override
    public void atualiza(){
        funcionarios = FXCollections.observableArrayList(Funcionario.buscarFuncionariosTrabalharamMes(mes));
        tabelaFuncionarios.setItems(funcionarios);
        labelMes.setText(mes);
    }
    
    public void imprimirTodas(){
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaTotalHoras.setCellValueFactory(cellData -> cellData.getValue().totalHoras(mes));
    }    
    
}
