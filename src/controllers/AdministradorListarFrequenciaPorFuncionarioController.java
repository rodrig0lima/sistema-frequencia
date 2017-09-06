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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorListarFrequenciaPorFuncionarioController extends ControladorPai{
    @FXML
    private TextField nome;
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;
    @FXML
    private TableColumn<Funcionario,String> colunaNome;
    @FXML
    private TableColumn<Funcionario,String> colunaHoras;
    @FXML
    private TableColumn<Funcionario,String> colunaMeses;
    private ObservableList<Funcionario> funcionarios;
    
    @Override
    public void atualiza(){
        funcionarios = FXCollections.observableArrayList(Funcionario.buscarFuncionariosGerente(""));
        tabelaFuncionarios.setItems(funcionarios);
        nome.setText("");
    }
    public void buscar(){
        funcionarios = FXCollections.observableArrayList(Funcionario.buscarFuncionariosGerente(nome.getText()));
        tabelaFuncionarios.setItems(funcionarios);
    }
    public void relatorioFuncionario(){
        final ObservableList<TablePosition> selectedCells = tabelaFuncionarios.getSelectionModel().getSelectedCells();
        TablePosition pos = selectedCells.get(0);
        Funcionario funcionario = funcionarios.get(pos.getRow());
        myController.getController(Principal.administradorRelatorioFuncionarioID).setId(funcionario.getId());
        administradorRelatorioFuncionario();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaHoras.setCellValueFactory(cellData ->cellData.getValue().totalHoras());
        colunaMeses.setCellValueFactory(cellData ->cellData.getValue().qtMesesTrabalhados());
    }    
    
}
