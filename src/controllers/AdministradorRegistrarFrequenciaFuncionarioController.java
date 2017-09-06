/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import classes.Log;
import classes.Registro;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
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
public class AdministradorRegistrarFrequenciaFuncionarioController extends ControladorPai{
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
    private TableView<Registro> tabelaFrequencia;
    @FXML
    private TableColumn<Registro,String> colunaData;
    @FXML
    private TableColumn<Registro,String> colunaHoraEntrada;
    @FXML
    private TableColumn<Registro,String> colunaHoraSaida;
    
    ObservableList<Registro> registros = FXCollections.observableArrayList(new ArrayList<>());
    
    @Override
    public void setId(int id){
        idFuncionario = id;
    }
    @Override
    public void atualiza(){
        funcionario = null;
        funcionario = Funcionario.buscarFuncionario(idFuncionario);
        nome.setText(funcionario.getNome());
        data.setText(getDataAtual());
        hora.setText(getHoraAtual());
        observacao.setText("");
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, getDataAtual().substring(3)));
        tabelaFrequencia.setItems(registros);
    }
    public void registrarEntrada(){
        Matcher matcher = padraoData.matcher(data.getText());
        if(!matcher.find() ){
            JOptionPane.showMessageDialog(null, "Data inválida! Formato: DD/MM/AAAA");
            return;
        }
        matcher = padraoHora.matcher(hora.getText());
        if(!matcher.find() ){
            JOptionPane.showMessageDialog(null, "Hora inválida! Formato: HH:MM:SS");
            return;
        }
        funcionario.entrada(data.getText(), hora.getText(), observacao.getText());
        new Log(Principal.idFuncionarioLogado, getHoraAtual(), getDataAtual(), "Entrada para "+Funcionario.buscarFuncionario(idFuncionario).getNome()+", Data indicada: "+data.getText()+", Hora indicada: "+hora.getText()+(observacao.getText().equals("")?"":", Motivo: "+observacao.getText())).guarda();
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, getDataAtual().substring(3)));
        tabelaFrequencia.setItems(registros);
    }
    public void registrarSaida(){
        Matcher matcher = padraoData.matcher(data.getText());
        if(!matcher.find() ){
            JOptionPane.showMessageDialog(null, "Data inválida! Formato: DD/MM/AAAA");
            return;
        }
        matcher = padraoHora.matcher(hora.getText());
        if(!matcher.find() ){
            JOptionPane.showMessageDialog(null, "Hora inválida! Formato: HH:MM:SS");
            return;
        }
        funcionario.saida(data.getText(),hora.getText(), observacao.getText());
        new Log(Principal.idFuncionarioLogado, getHoraAtual(), getDataAtual(), "Saída para "+Funcionario.buscarFuncionario(idFuncionario).getNome()+", Data indicada: "+data.getText()+", Hora indicada: "+hora.getText()+(observacao.getText().equals("")?"":", Motivo: "+observacao.getText())).guarda();
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, getDataAtual().substring(3)));
        tabelaFrequencia.setItems(registros);
    }
    public void excluirRegistro(){
        final ObservableList<TablePosition> selectedCells = tabelaFrequencia.getSelectionModel().getSelectedCells();
        TablePosition pos = selectedCells.get(0);
        Registro registro = registros.get(pos.getRow());
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Tem certeza que deseja realizar excluir o registro?","Aviso",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            Registro.excluirRegistro(registro.getId());
            registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, getDataAtual().substring(3)));
            tabelaFrequencia.setItems(registros);
        }else{
            return;
        } 
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
