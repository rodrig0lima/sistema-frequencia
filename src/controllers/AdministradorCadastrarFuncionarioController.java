/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorCadastrarFuncionarioController extends ControladorPai{
    @FXML
    private TextField nome;
    @FXML
    private ComboBox tipoFuncionario;
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @Override
    public void atualiza(){
        nome.setText("");
        tipoFuncionario.getSelectionModel().select("Selecione...");
        login.setText("");
        senha.setText("");
    }
    public void cadastrar(){
        if((nome.getText().equals(""))||(tipoFuncionario.getValue().equals("Selecione..."))||(login.getText().equals(""))||(senha.getText().equals(""))){
            JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
            return;
        }
        if(Funcionario.buscarFuncionario(login.getText())!=null){
            JOptionPane.showMessageDialog(null, "Login já cadastrado!");
            return;
        }
        if(senha.getText().length()<6){
            JOptionPane.showMessageDialog(null,"A senha deve ter no mínimo 6 caracteres!");
            return;
        }
        int tipoF;
        if(tipoFuncionario.getValue().equals("Administrador")){
            tipoF = 1;
        }else if(tipoFuncionario.getValue().equals("Caixa/Gerente")){
            tipoF = 2;
        }else{
            tipoF = 3;
        }
        Funcionario funcionario = new Funcionario(login.getText(), senha.getText(), nome.getText(), tipoF);
        funcionario.guardar();
        myController.getController(Principal.administradorCadastrarFuncionario1ID).setId(funcionario.getId());
        administradorCadastrarFuncionario1();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoFuncionario.getItems().addAll(
            "Selecione...",
            "Vendedor",
            "Caixa/Gerente",
            "Administrador"
        );
    }    
    
}
