/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
//RETIRAR ESSES COMENTÁRIOS
/*
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;*//*/

/**
 * @author Rodrigo
**/
public class GeradorRelatorioFrequencia {
    private String path; //Caminho base
    private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper

    //Recupera os caminhos para que a classe possa encontrar os relatórios
    public GeradorRelatorioFrequencia() {
        System.out.println("a");
        //System.out.println(this.getClass().getResource("FolhaDeFrequencia.jrxml"));
        this.path = this.getClass().getResource("FolhaDeFrequencia.jrxml").getPath();
        //this.pathToReportPackage = this.path + "relatorios/";
      //  System.out.println(path);
        //System.out.println(path);
    }

    //Imprime/gera uma lista de frequencia
    public void imprimir(ArrayList<Registro> registros) throws Exception {
        System.out.println("aqui");
        //RETIRAR ESSES COMENTÁRIOS
        /*JasperDesign jasperDesign = JRXmlLoader.load(new FileInputStream(new File("C:/Relatorios/FolhaDeFrequencia.jrxml")));
        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(registros));
        JasperExportManager.exportReportToPdfFile(print, "C:/Relatorios/Relatorio_de_Clientes.pdf");	*/	
    }

    public String getPathToReportPackage() {
        return this.pathToReportPackage;
    }

    public String getPath() {
        return this.path;
    }
}