/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.awt.Graphics2D;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorCadastrarFuncionarioController1 extends ControladorPai{
    private int idFuncionario;
    public Funcionario funcionario;
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    @FXML
    private ImageView imagem;
    @FXML
    private Label feedback;
    @FXML
    private Label prompt;
    @FXML
    private Label status;
    @FXML
    private Label nome;
    @FXML
    private Button botaoCadastrar;
    @Override
    public void setId(int id){
        idFuncionario = id;
    }
    @Override
    public void atualiza(){
        enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        updateStatus();
        botaoCadastrar.setDisable(true);
        funcionario = null;
        funcionario = Funcionario.buscarFuncionario(idFuncionario);
        nome.setText(funcionario.getNome());
        prompt.setText("");
        imagem.setImage(null);
        start();
    }
    public void cadastrar(){
        funcionario.setTemplate(enroller.getTemplate().serialize());
        funcionario.guardarTemplate();
        administradorListarFuncionarios();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //makeReport("A impressão digital foi capturada.");
                        feedback.setText("Posicione o mesmo dedo novamente.");
                        process(e.getSample());
                    }
                });
            }
        });
        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        makeReport("O leitor de impressões digitais foi conectado.");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        makeReport("O leitor de impressões digitais foi desconectado.");
                    }
                });
            }
        });
        updateStatus();
    }
    @FXML
    public void exitApplication(ActionEvent event) {
        stop();
        Platform.exit();
    }

    
    protected void stop() {
        capturer.stopCapture();
    }
    
    protected void start() {
        capturer.startCapture();
        feedback.setText("Usando o leitor de impressões digitais, posicione o dedo no sensor.");
    }
    
    public void drawPicture(java.awt.Image awtImage) {
        //ImageIcon img = new ImageIcon(image.getScaledInstance(224, 153, Image.SCALE_DEFAULT));
        BufferedImage bImg ;
        if (awtImage instanceof BufferedImage) {
            bImg = (BufferedImage) awtImage ;
        } else {
            bImg = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bImg.createGraphics();
            graphics.drawImage(awtImage, 0, 0, null);
            graphics.dispose();
        }
        Image fxImage = SwingFXUtils.toFXImage(bImg, null);
        imagem.setImage(fxImage);
    }

    protected java.awt.Image convertSampleToBitmap(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    
    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    public void makeReport(String string) {
        String texto = prompt.getText();
        prompt.setText(texto + "\n"+string);
    }
    
    void process(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));
        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Check quality of the sample and add to enroller if it's good
        if (features != null) {
            try {
                makeReport("Impressão digital capturada.");
                enroller.addFeatures(features);		// Add feature set to template.
            } catch (DPFPImageQualityException ex) {
            } finally {
                updateStatus();
                // Check if template has been created.
                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:	// report success and stop capturing
                        stop();
                        feedback.setText("Clique em finalizar para concluir o cadastro!");
                        botaoCadastrar.setDisable(false);
                    break;
                    case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
                        botaoCadastrar.setDisable(true);
                        enroller.clear();
                        stop();
                        updateStatus();
                        JOptionPane.showMessageDialog(null, "Impressões digitais inválidas! Tente novamente!", "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                    default:
                        System.out.println("Default");
                }
            }
        }
    }
    public void tentarNovamente(){
        botaoCadastrar.setDisable(true);
        enroller.clear();
        stop();
        updateStatus();
        prompt.setText("");
        funcionario.setTemplate(null);
        imagem.setImage(null);
        start();
    }
    public void cancelar(){
        stop();
        administradorListarFuncionarios();
    }
    private void updateStatus(){
        status.setText(String.format("Impressões digitais necessárias: %1$s", enroller.getFeaturesNeeded()));
    }
}
