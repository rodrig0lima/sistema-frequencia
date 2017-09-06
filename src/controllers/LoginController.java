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
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class LoginController extends ControladorPai {
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @FXML
    private ImageView imagem;

    @Override
    public void atualiza() {
        login.setText("");
        senha.setText("");
        imagem.setImage(null);
        start();
    }

    public void logar() {
        if ((!login.getText().equals("")) && (!senha.getText().equals(""))) {
            Funcionario funcionario = Funcionario.verificarLogin(login.getText(), senha.getText());
            if (funcionario != null) {
                Principal.idFuncionarioLogado = funcionario.getId();
                parar();
                switch (funcionario.getTipoFuncionario()) {
                    case 1:
                        myController.setScreen(Principal.administradorPrincipalID);
                        break;
                    case 2:
                        myController.setScreen(Principal.gerentePrincipalID);
                        break;
                    case 3:
                        myController.setScreen(Principal.funcionarioPrincipalID);
                        break;
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Login e/ou senha Inválidos!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha os campos!");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start();
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {

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
                        //makeReport("The fingerprint reader was connected.");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //makeReport("The fingerprint reader was disconnected.");
                    }
                });
            }
        });
    }

    protected void start() {
        capturer.startCapture();
        //setPrompt("Using the fingerprint reader, scan your fingerprint.");
    }

    protected void parar() {
        capturer.stopCapture();
    }

    protected void process(DPFPSample sample) {
        drawPicture(convertSampleToBitmap(sample));
        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        // Check quality of the sample and start verification if it's good
        if (features != null) {
            Funcionario funcionario = Funcionario.buscarFuncionario(features);
            if(funcionario!=null){
                Principal.idFuncionarioLogado = funcionario.getId();
                parar();
                switch (funcionario.getTipoFuncionario()) {
                    case 1:
                        myController.setScreen(Principal.administradorPrincipalID);
                        break;
                    case 2:
                        myController.setScreen(Principal.gerentePrincipalID);
                        break;
                    case 3:
                        myController.setScreen(Principal.funcionarioPrincipalID);
                        break;
                }
            }
        }
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
    
}
