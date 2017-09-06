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
public class GerenteListarFuncionariosController extends ControladorPai {
    @FXML
    private TextField nome;
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;
    @FXML
    private TableColumn<Funcionario,String> colunaNome;
    @FXML
    private TableColumn<Funcionario,String> colunaData;
    @FXML
    private TableColumn<Funcionario,String> colunaHoraEntrada;
    @FXML
    private TableColumn<Funcionario,String> colunaHoraSaida;
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
    public void registrarParaFuncionario(){
        final ObservableList<TablePosition> selectedCells = tabelaFuncionarios.getSelectionModel().getSelectedCells();
        TablePosition pos = selectedCells.get(0);
        Funcionario funcionario = funcionarios.get(pos.getRow());
        myController.getController(Principal.gerenteRegistrarFrequenciaFuncionarioID).setId(funcionario.getId());
        gerenteRegistrarFrequenciaFuncionario();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        colunaHoraEntrada.setCellValueFactory(cellData -> cellData.getValue().horaEntradaProperty());
        colunaHoraSaida.setCellValueFactory(cellData -> cellData.getValue().horaSaidaProperty());
    }    
    
}
