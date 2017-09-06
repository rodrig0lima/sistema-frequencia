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
public class Log {
    private int id;
    private int idFuncionario;
    private String hora;
    private String data;
    private String descricao;

    public Log(int id, int idFuncionario, String hora, String data, String descricao) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.hora = hora;
        this.data = data;
        this.descricao = descricao;
    }

    public Log(int idFuncionario, String hora, String data, String descricao) {
        this.idFuncionario = idFuncionario;
        this.hora = hora;
        this.data = data;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public String getHora() {
        return hora;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public static ArrayList<String> getMeses(){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT DISTINCT(SUBSTRING(data,4,7)) AS mes FROM log ORDER BY SUBSTRING(data,7,4) DESC,SUBSTRING(data,4,2) DESC");
        ArrayList<String> meses = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                meses.add(resultSet.getString("mes"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return meses;
    }
    
    public void guarda(){
        new Conexao();
        if(id==0){
            Conexao.exAtualizacao("INSERT INTO log(id_funcionario,data,hora,descricao) VALUES("+idFuncionario+",'"+data+"','"+hora+"','"+descricao+"')");
            ResultSet resultSet = Conexao.exConsulta("SELECT MAX(id) as maxid FROM log WHERE data = '"+data+"' AND hora ='"+hora+"' AND descricao='"+descricao+"'");
            try{
                resultSet.beforeFirst();
                resultSet.next();
                id = resultSet.getInt("maxid");
            }catch(SQLException e){
                e.printStackTrace();
            }
        } //Se houver id o log nao poderá ser alterado, não há update aqui
    }
    
    /* MÉTODOS ESTÁTICOS */
    
    public static ArrayList<Log> buscarLogPorFuncionario(int idFuncionario){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT id,id_funcionario,data,hora,descricao FROM log WHERE id_funcionario = "+idFuncionario);
        ArrayList<Log> logs = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                logs.add(new Log(resultSet.getInt("id"),resultSet.getInt("id_funcionario"),resultSet.getString("hora"), resultSet.getString("data"), resultSet.getString("descricao")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return logs;
    }
    
    public static ArrayList<Log> buscarLogPorMesFuncionario(String palavraChave,String mes){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT log.id,log.id_funcionario,log.data,log.hora,log.descricao FROM funcionario,log WHERE funcionario.id = log.id_funcionario AND funcionario.nome LIKE '%"+palavraChave+"%' AND log.data LIKE '__/"+mes+"' ORDER BY id ASC");
        ArrayList<Log> logs = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                logs.add(new Log(resultSet.getInt("id"),resultSet.getInt("id_funcionario"),resultSet.getString("hora"), resultSet.getString("data"), resultSet.getString("descricao")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return logs;
    }
    
    @Override
    public String toString(){
        return "Log: \n ID: "+id+" ID Funcionario: "+idFuncionario+" Data: "+data+" Hora: "+hora+" Descricao: "+descricao;
    }
    
    /* TABLE VIEW */
    public StringProperty nomeFuncionarioProperty(){
        return Funcionario.buscarFuncionario(idFuncionario).nomeProperty();
    }
    
    public StringProperty dataProperty(){
        return new SimpleStringProperty(data);
    }
    
    public StringProperty horaProperty(){
        return new SimpleStringProperty(hora);
    }
    public StringProperty descricaoProperty(){
        return new SimpleStringProperty(descricao);
    }
}
