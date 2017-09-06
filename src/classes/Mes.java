/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Rodrigo
 */
public class Mes {
    private String mes;
    private String ano;
    private int funcionarios;

    public Mes(String mes, String ano, int funcionarios) {
        this.mes = mes;
        this.ano = ano;
        this.funcionarios = funcionarios;
    }

    public String getMes() {
        return mes;
    }

    public String getAno() {
        return ano;
    }

    public int getFuncionarios() {
        return funcionarios;
    }
    
    public String getNome(){
        switch(mes){
            case "01":
                return "Janeiro";
            case "02":
                return "Fevereiro";
            case "03":
                return "Março";
            case "04":
                return "Abril";
            case "05":
                return "Maio";
            case "06":
                return "Junho";
            case "07":
                return "Julho";
            case "08":
                return "Agosto";
            case "09":
                return "Setembro";
            case "10":
                return "Outubro";
            case "11":
                return "Novembro";
            case "12":
                return "Dezembro";
            default:
                return "-";
        }
    }
    
    public static ArrayList<Mes> buscarMeses(){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT DISTINCT SUBSTRING(data,4,2) as mes, SUBSTRING(data,7,4) as ano FROM registro ORDER BY SUBSTRING(data,7,4) DESC, SUBSTRING(data,4,2) DESC");
        ArrayList<Mes> meses = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                meses.add(new Mes(resultSet.getString("mes"), resultSet.getString("ano"), 0));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        for (Mes mes : meses) {
            resultSet = Conexao.exConsulta("SELECT COUNT(DISTINCT(id_funcionario)) as qt  FROM registro WHERE registro.data LIKE '__/"+mes.mes+"/"+mes.ano+"'");
            try{
                resultSet.last();
                if(resultSet.getRow()!=0){
                    resultSet.beforeFirst();
                    resultSet.next();
                    mes.funcionarios = resultSet.getInt("qt");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return meses;
    }
    
    @Override
    public String toString(){
        return "Mês: \n Mes: "+mes+" Ano:"+ano+" Trabalharam: "+funcionarios;
    }
    
    /* TABLE VIEW */
    public StringProperty qtFuncionarios(){
        return new SimpleStringProperty(String.valueOf(funcionarios));
    }
    
    public StringProperty mes(){
        return new SimpleStringProperty(getNome()+"/"+ano);
    }
}
