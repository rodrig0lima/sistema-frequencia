/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Rodrigo
 */
public class Principal extends Application{
    public static String versao = "2.0.0";
    public static int idFuncionarioLogado = 0;
    public static String administradorAlterarFuncionarioID = "administradorAlterarFuncionario",administradorAlterarFuncionarioArquivo = "administradorAlterarFuncionario.fxml";
    public static String administradorCadastrarFuncionarioID = "administradorCadastrarFuncionario",administradorCadastrarFuncionarioArquivo = "administradorCadastrarFuncionario.fxml";
    public static String administradorCadastrarFuncionario1ID = "administradorCadastrarFuncionario1",administradorCadastrarFuncionario1Arquivo = "administradorCadastrarFuncionario1.fxml";
    public static String administradorListarFrequenciaPorFuncionarioID = "administradorListarFrequenciaPorFuncionario",administradorListarFrequenciaPorFuncionarioArquivo = "administradorListarFrequenciaPorFuncionario.fxml";
    public static String administradorListarFrequenciasPorMesID = "administradorListarFrequenciasPorMes",administradorListarFrequenciasPorMesArquivo = "administradorListarFrequenciasPorMes.fxml";
    public static String administradorListarFuncionariosID = "administradorListarFuncionarios",administradorListarFuncionariosArquivo = "administradorListarFuncionarios.fxml";
    public static String administradorPrincipalID = "administradorPrincipal",administradorPrincipalArquivo = "administradorPrincipal.fxml";
    public static String administradorRegistrarFrequenciaFuncionarioID = "administradorRegistrarFrequenciaFuncionario",administradorRegistrarFrequenciaFuncionarioArquivo = "administradorRegistrarFrequenciaFuncionario.fxml";
    public static String administradorRelatorioFuncionarioID = "administradorRelatorioFuncionario",administradorRelatorioFuncionarioArquivo = "administradorRelatorioFuncionario.fxml";
    public static String administradorRelatorioMensalID = "administradorRelatorioMensal",administradorRelatorioMensalArquivo = "administradorRelatorioMensal.fxml";
    public static String administradorVisualizarLogID = "administradorVisualizarLog",administradorVisualizarLogArquivo = "administradorVisualizarLog.fxml";
    public static String funcionarioPrincipalID = "funcionarioPrincipal",funcionarioPrincipalArquivo = "funcionarioPrincipal.fxml";
    public static String gerenteCadastrarFuncionarioID = "gerenteCadastrarFuncionario",gerenteCadastrarFuncionarioArquivo = "gerenteCadastrarFuncionario.fxml";
    public static String gerenteListarFuncionariosID = "gerenteListarFuncionarios",gerenteListarFuncionariosArquivo = "gerenteListarFuncionarios.fxml";
    public static String gerentePrincipalID = "gerentePrincipal",gerentePrincipalArquivo = "gerentePrincipal.fxml";
    public static String gerenteRegistrarFrequenciaFuncionarioID = "gerenteRegistrarFrequenciaFuncionario",gerenteRegistrarFrequenciaFuncionarioArquivo = "gerenteRegistrarFrequenciaFuncionario.fxml";
    public static String loginID = "login",loginArquivo = "login.fxml";
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Principal.administradorAlterarFuncionarioID, Principal.administradorAlterarFuncionarioArquivo);
        mainContainer.loadScreen(Principal.administradorCadastrarFuncionarioID, Principal.administradorCadastrarFuncionarioArquivo);
        mainContainer.loadScreen(Principal.administradorCadastrarFuncionario1ID, Principal.administradorCadastrarFuncionario1Arquivo);
        mainContainer.loadScreen(Principal.administradorListarFrequenciaPorFuncionarioID,Principal.administradorListarFrequenciaPorFuncionarioArquivo);
        mainContainer.loadScreen(Principal.administradorListarFrequenciasPorMesID,Principal.administradorListarFrequenciasPorMesArquivo);
        mainContainer.loadScreen(Principal.administradorListarFuncionariosID,Principal.administradorListarFuncionariosArquivo);
        mainContainer.loadScreen(Principal.administradorPrincipalID,Principal.administradorPrincipalArquivo);
        mainContainer.loadScreen(Principal.administradorRegistrarFrequenciaFuncionarioID,Principal.administradorRegistrarFrequenciaFuncionarioArquivo);
        mainContainer.loadScreen(Principal.administradorRelatorioFuncionarioID,Principal.administradorRelatorioFuncionarioArquivo);
        mainContainer.loadScreen(Principal.administradorRelatorioMensalID,Principal.administradorRelatorioMensalArquivo);
        mainContainer.loadScreen(Principal.administradorVisualizarLogID,Principal.administradorVisualizarLogArquivo);
        mainContainer.loadScreen(Principal.funcionarioPrincipalID, Principal.funcionarioPrincipalArquivo);
        mainContainer.loadScreen(Principal.gerenteCadastrarFuncionarioID, Principal.gerenteCadastrarFuncionarioArquivo);
        mainContainer.loadScreen(Principal.gerenteListarFuncionariosID, Principal.gerenteListarFuncionariosArquivo);
        mainContainer.loadScreen(Principal.gerentePrincipalID, Principal.gerentePrincipalArquivo);
        mainContainer.loadScreen(Principal.gerenteRegistrarFrequenciaFuncionarioID, Principal.gerenteRegistrarFrequenciaFuncionarioArquivo);
        mainContainer.loadScreen(Principal.loginID, Principal.loginArquivo);
        mainContainer.setScreen(Principal.loginID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Principal.stage = stage;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Controle de Frequência - v"+versao);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.sizeToScene();
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}