/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import classes.Registro;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class FuncionarioPrincipalController extends ControladorPai {
    private int idFuncionario;
    public Funcionario funcionario;
    /* TELA */
    @FXML
    private Label nome;
    @FXML
    private TextField data;
    @FXML
    private TextField hora;
    @FXML
    private TextArea observacao;
    @FXML
    private Button entrada;
    @FXML
    private Button saida;
    @FXML
    private TableView<Registro> tabelaFrequencia;
    @FXML
    private TableColumn<Registro,String> colunaData;
    @FXML
    private TableColumn<Registro,String> colunaHoraEntrada;
    @FXML
    private TableColumn<Registro,String> colunaHoraSaida;
    
    ObservableList<Registro> registros = FXCollections.observableArrayList(new ArrayList<>());
    @Override
    public void atualiza(){
        funcionario = null;
        idFuncionario = Principal.idFuncionarioLogado;
        funcionario = Funcionario.buscarFuncionario(idFuncionario);
        nome.setText(funcionario.getNome());
        data.setText(getDataAtual());
        hora.setText(getHoraAtual());
        observacao.setText("");
        if(Registro.getUltimoRegistroAberto(funcionario.getId())==null){
            saida.setVisible(false);
            entrada.setVisible(true);
        }else{
            saida.setVisible(true);
            entrada.setVisible(false);
        }
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, getDataAtual().substring(3)));
        tabelaFrequencia.setItems(registros);
    }
    public void registrarEntrada(){
        funcionario.entrada(data.getText(), hora.getText(), observacao.getText());
        entrada.setVisible(false);
        JOptionPane.showMessageDialog(null,"Entrada registrada com sucesso!");
        sair();
    }
    public void registrarSaida(){
        funcionario.saida(hora.getText(), observacao.getText());
        saida.setVisible(false);
        JOptionPane.showMessageDialog(null,"Saída registrada com sucesso!");
        sair();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        colunaHoraEntrada.setCellValueFactory(cellData->cellData.getValue().horaEntradaProperty());
        colunaHoraSaida.setCellValueFactory(cellData->cellData.getValue().horaSaidaProperty());
        tabelaFrequencia.setItems(registros);
    }    
    
}
