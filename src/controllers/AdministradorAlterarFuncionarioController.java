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
public class AdministradorAlterarFuncionarioController extends ControladorPai{
    private int idFuncionario;
    public Funcionario funcionario;
    @FXML
    private TextField nome;
    @FXML
    private ComboBox tipoFuncionario;
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @Override
    public void setId(int id){
        idFuncionario = id;
    }
    @Override
    public void atualiza(){
        funcionario = null;
        funcionario = Funcionario.buscarFuncionario(idFuncionario);
        nome.setText(funcionario.getNome());
        int tipoF = funcionario.getTipoFuncionario();
        switch(tipoF){
            case 1:
                tipoFuncionario.getSelectionModel().select("Administrador");
                break;
            case 2:
                tipoFuncionario.getSelectionModel().select("Caixa/Gerente");
                break;
            case 3:
                tipoFuncionario.getSelectionModel().select("Vendedor");
        }
        login.setText(funcionario.getLogin());
        senha.setText("");
    }
    public void alterar(){
        if((nome.getText().equals(""))||(tipoFuncionario.getValue().equals("Selecione..."))||(login.getText().equals(""))){
            JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
            return;
        }
        if((Funcionario.buscarFuncionario(login.getText())!=null)&&(Funcionario.buscarFuncionario(login.getText()).getId()!=funcionario.getId())){
            JOptionPane.showMessageDialog(null, "Login já cadastrado!");
            return;
        }
        if((senha.getText().length()<6)&&(senha.getText().length()>0)){
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
        if(senha.getText().length()>0){
            funcionario.setSenha(senha.getText());
        }
        funcionario.setNome(nome.getText());
        funcionario.setLogin(login.getText());
        funcionario.setTipoFuncionario(tipoF);
        funcionario.guardar();
    }
    public void cadastrarDigital(){
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
