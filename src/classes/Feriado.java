/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public class Feriado {
    private String data;

    public Feriado(String data) {
        this.data = data;
    }
    

    public String getData() {
        return data;
    }
    
    public static Feriado buscarFeriado(String data){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT data FROM feriado WHERE data = '"+data+"'");
        Feriado feriado = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                feriado = new Feriado(resultSet.getString("data"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return feriado;
    }
    
    public void guarda(){
        new Conexao();
        if(buscarFeriado(data)==null){
            Conexao.exAtualizacao("INSERT INTO feriado(data) VALUES('"+data+"')");
        }
    }
    
    /* MÉTODOS ESTÁTICOS */
    
    public static boolean verificaFeriado(String data){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT data FROM feriado WHERE data = '"+data+"'");
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static ArrayList<Feriado> buscarFeriados(String mes){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT data FROM feriado WHERE data LIKE '__/"+mes+"'");
        ArrayList<Feriado> feriados = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                feriados.add(new Feriado(resultSet.getString("data")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return feriados;
    }
    
    @Override
    public String toString(){
        return "Data: "+data;
    }
}
