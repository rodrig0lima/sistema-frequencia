/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Funcionario;
import classes.Registro;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import telas.Principal;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class AdministradorRelatorioFuncionarioController extends ControladorPai{
    Funcionario funcionario;
    int idFuncionario;
    @FXML
    public Label nome;
    @FXML
    public ComboBox meses;
    @FXML
    public TableView<Registro> tabelaRegistros;
    @FXML
    public TableColumn<Registro,String> colunaData;
    @FXML
    public TableColumn<Registro,String> colunaFeriado;
    @FXML
    public TableColumn<Registro,String> colunaHoraEntrada;
    @FXML
    public TableColumn<Registro,String> colunaHoraSaida;
    @FXML
    public TableColumn<Registro,String> colunaObservacao;
    @FXML
    public TableColumn<Registro,String> colunaTotalHoras;
    @FXML
    public Label totalHoras;
    public ObservableList<Registro> registros;
    @Override
    public void setId(int id){
        idFuncionario = id;
    }
    @Override
    public void atualiza(){
        funcionario = Funcionario.buscarFuncionario(idFuncionario);
        meses.setItems(FXCollections.observableArrayList(funcionario.getMesesTrabalhados()));
        meses.getSelectionModel().selectFirst();
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, meses.getValue().toString()));
        tabelaRegistros.setItems(registros);
        totalHoras.setText(funcionario.getHorasTrabalhadas(meses.getValue().toString()));
        nome.setText(funcionario.getNome());
    }
    public void buscar(){
        registros = FXCollections.observableArrayList(Registro.buscarRegistros(idFuncionario, meses.getValue().toString()));
        tabelaRegistros.setItems(registros);
        totalHoras.setText(funcionario.getHorasTrabalhadas(meses.getValue().toString()));
    }
    public void imprimirRelatorio(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatorio");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
        System.out.println(funcionario.getNome());
        File file = fileChooser.showSaveDialog(Principal.stage);
        if (file != null) {
            /* CRIAR O RELATÓRIO AQUI */
            try{
                Document document = new Document(PageSize.A4);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();
                Paragraph para = new Paragraph("Teste"); //Define apenas para busca da fonte
                Font fontBold = new Font(para.getFont().getFamily(), para.getFont().getSize(), Font.BOLD);
                para = new Paragraph("Lista de Frequência Mensal",fontBold);
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.add(new Paragraph("Mês/ano: "+meses.getValue().toString()));
                document.add(new Paragraph("Funcionário: "+funcionario.getNome()));
                document.add(new Paragraph(" "));
                para = new Paragraph("Registros de frequência");
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.add(new Paragraph(" "));
                PdfPTable table = new PdfPTable(6);
                Phrase pr;
                //table.setTotalWidth(new float[]{ 40, 40,40,80 }); //Define o tamanho de cada coluna
                //table.setLockedWidth(true);
                
                PdfPCell celula = new PdfPCell(new Phrase("Data",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                celula.setPaddingBottom(5);
                table.addCell(celula);
                
                celula = new PdfPCell(new Phrase("Feriado",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(celula);
                
                celula = new PdfPCell(new Phrase("Entrada",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(celula);
                
                celula = new PdfPCell(new Phrase("Saída",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(celula);
                
                celula = new PdfPCell(new Phrase("Observação",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(celula);
                
                celula = new PdfPCell(new Phrase("Total",fontBold));
                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(celula);
                
                for (Registro registro : registros) {
                    celula = new PdfPCell(new Phrase(registro.getData()));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celula.setPaddingBottom(4);
                    table.addCell(celula);
                    celula = new PdfPCell(new Phrase(registro.isFeriado()));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(celula);
                    celula = new PdfPCell(new Phrase(registro.getHoraEntrada()));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(celula);
                    celula = new PdfPCell(new Phrase(registro.getHoraSaida()));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(celula);
                    table.addCell(registro.getObservacao());
                    celula = new PdfPCell(new Phrase(registro.getTotalHoras()));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(celula);
                }
                System.out.println(PageSize.A4.getWidth());
                table.setTotalWidth(new float[]{77,55,60,60,200,60});
                table.setLockedWidth(true);
                document.add(table);
                para = new Paragraph("Total de horas trabalhadas no mês: "+funcionario.getHorasTrabalhadas(meses.getValue().toString()));
                para.setIndentationRight(12);
                para.setSpacingBefore(10);
                para.setAlignment(Element.ALIGN_RIGHT);
                document.add(para);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                para = new Paragraph("____________________________________________________________");
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                para = new Paragraph(funcionario.getNome());
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                para = new Paragraph("Assinatura do Funcionário");
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.close();
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                fos.write(baos.toByteArray());
                fos.close();
                System.out.println("Gera relatorio "+file.getAbsolutePath());
            }catch(DocumentException e){
                System.out.println(e.getMessage());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
            /* FIM DO RELATÓRIO */
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        colunaFeriado.setCellValueFactory(cellData -> cellData.getValue().feriadoProperty());
        colunaHoraEntrada.setCellValueFactory(cellData -> cellData.getValue().horaEntradaProperty());
        colunaHoraSaida.setCellValueFactory(cellData -> cellData.getValue().horaSaidaProperty());
        colunaObservacao.setCellValueFactory(cellData -> cellData.getValue().observacaoProperty());
        colunaTotalHoras.setCellValueFactory(cellData -> cellData.getValue().totalHorasProperty());
    }    
    
}
