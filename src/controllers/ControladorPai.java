/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import telas.ControlledScreen;
import telas.Principal;
import telas.ScreensController;

/**
 *
 * @author Rodrigo
 */
public abstract class ControladorPai implements Initializable ,ControlledScreen{
    protected DecimalFormat df = new DecimalFormat("0.00");
    protected Pattern padraoData = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    protected Pattern padraoHora = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
    protected ScreensController myController;
    @Override
    public void setId(int id){
        System.out.println("Sem uso!");
    }
    @Override
    public void setMes(String mes){
        System.out.println("Sem uso!");
    }
    /* MENU */
    
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    public void sair(){
        myController.setScreen(Principal.loginID);
    }
    public void administradorAlterarFuncionario(){
        myController.setScreen(Principal.administradorAlterarFuncionarioID);
    }
    public void administradorCadastrarFuncionario(){
        myController.setScreen(Principal.administradorCadastrarFuncionarioID);
    }
    public void administradorCadastrarFuncionario1(){
        myController.setScreen(Principal.administradorCadastrarFuncionario1ID);
    }
    public void administradorListarFrequenciaPorFuncionario(){
        myController.setScreen(Principal.administradorListarFrequenciaPorFuncionarioID);
    }
    public void administradorListarFrequenciasPorMes(){
        myController.setScreen(Principal.administradorListarFrequenciasPorMesID);
    }
    public void administradorListarFuncionarios(){
        myController.setScreen(Principal.administradorListarFuncionariosID);
    }
    public void administradorPrincipal(){
        myController.setScreen(Principal.administradorPrincipalID);
    }
    public void administradorRegistrarFrequenciaFuncionario(){
        myController.setScreen(Principal.administradorRegistrarFrequenciaFuncionarioID);
    }
    public void administradorRelatorioFuncionario(){
        myController.setScreen(Principal.administradorRelatorioFuncionarioID);
    }
    public void administradorRelatorioMensal(){
        myController.setScreen(Principal.administradorRelatorioMensalID);
    }
    public void administradorVisualizarLog(){
        myController.setScreen(Principal.administradorVisualizarLogID);
    }
    public void gerenteCadastrarFuncionario(){
        myController.setScreen(Principal.gerenteCadastrarFuncionarioID);
    }
    public void gerenteListarFuncionarios(){
        myController.setScreen(Principal.gerenteListarFuncionariosID);
    }
    public void gerentePrincipal(){
        myController.setScreen(Principal.gerentePrincipalID);
    }
    public void gerenteRegistrarFrequenciaFuncionario(){
        myController.setScreen(Principal.gerenteRegistrarFrequenciaFuncionarioID);
    }
    public void sobre(){
        JOptionPane.showMessageDialog(null,"Sistema para controle de frequência\nVersão v"+Principal.versao);
    }
    public String getDataAtual(){
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        DateFormat formataData = DateFormat.getDateInstance();
        return formataData.format(data);
    }
    public String getHoraAtual(){
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        DateFormat hora = DateFormat.getTimeInstance();
        return hora.format(data);
    }
}
