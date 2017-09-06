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
import telas.Principal;

/**
 *
 * @author Rodrigo
 */
public class Registro {
    private int id;
    private int idFuncionario;
    private String data;
    private String horaEntrada;
    private String horaSaida;
    private String observacao;

    public Registro(int id, int idFuncionario, String data, String horaEntrada, String horaSaida, String observacao) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.observacao = observacao;
    }
    
    public Registro(int id, int idFuncionario, String data, String horaEntrada, String observacao) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.observacao = observacao;
        this.horaSaida = "";
    }

    public Registro(int idFuncionario, String data, String horaEntrada, String observacao) {
        this.idFuncionario = idFuncionario;
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.observacao = observacao;
        this.horaSaida = "";
    }

    public int getId() {
        return id;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public String getData() {
        return data;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public String getTotalHoras(){
        if(horaSaida.equals(""))
            return "-";
        String hora[] = horaEntrada.split(":");
        int hora_e = Integer.valueOf(hora[0]);
        int minuto_e = Integer.valueOf(hora[1]);
        int segundo_e = Integer.valueOf(hora[2]);
        hora = horaSaida.split(":");
        int hora_s = Integer.valueOf(hora[0]);
        int minuto_s = Integer.valueOf(hora[1]);
        int segundo_s = Integer.valueOf(hora[2]);
        int segundos = (hora_s-hora_e)*3600+(minuto_s-minuto_e)*60+(segundo_s-segundo_e);
        int horaT = segundos/3600;
        int minutoT = (segundos%3600)/60;
        int segundoT = (segundos%3600)%60;
        return (horaT<10?"0"+horaT:horaT)+":"+(minutoT<10?"0"+minutoT:minutoT)+":"+(segundoT<10?"0"+segundoT:segundoT);
    }
    /* RELATORIO */
    public String getNome(){
        return Funcionario.buscarFuncionario(idFuncionario).getNome();
    }
    
    public String getTotal(){
        return Funcionario.buscarFuncionario(idFuncionario).getHorasTrabalhadas(getMes());
    }
    
    public String getMes(){
        return data.substring(3);
    }
    
    public String isFeriado(){
        return Feriado.verificaFeriado(data)?"X":"";
    }
    
    public void guardar(){
        new Conexao();
        if(id==0){
            Conexao.exAtualizacao("INSERT INTO registro(id_funcionario,data,hora_entrada,hora_saida,observacao) VALUES("+idFuncionario+",'"+data+"','"+horaEntrada+"','"+horaSaida+"','"+observacao+"')");
            ResultSet resultSet = Conexao.exConsulta("SELECT MAX(id) as maxid FROM registro WHERE id_funcionario = "+idFuncionario+" AND data = '"+data+"' AND hora_entrada='"+horaEntrada+"' AND observacao = '"+observacao+"'");
            try{
                resultSet.beforeFirst();
                resultSet.next();
                id = resultSet.getInt("maxid");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            Conexao.exAtualizacao("UPDATE registro SET id_funcionario="+idFuncionario+",data='"+data+"',hora_entrada='"+horaEntrada+"',hora_saida='"+horaSaida+"',observacao='"+observacao+"' WHERE id = "+id);
        }
    }
    
    /* MÉTODOS ESTÁTICOS */
    
    public static Registro getUltimoRegistroAberto(int idFuncionario){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id_funcionario = "+idFuncionario+" AND hora_saida = '' AND data='"+Conexao.getDataAtual()+"' ORDER BY id DESC");
        Registro registro = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                registro = new Registro(resultSet.getInt("id"),resultSet.getInt("id_funcionario"), resultSet.getString("data"), resultSet.getString("hora_entrada"), resultSet.getString("observacao"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return registro;
    }
    
    public static Registro getUltimoRegistroAberto(int idFuncionario,String data){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id_funcionario = "+idFuncionario+" AND hora_saida = '' AND data='"+data+"' ORDER BY id DESC");
        Registro registro = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                registro = new Registro(resultSet.getInt("id"),resultSet.getInt("id_funcionario"), resultSet.getString("data"), resultSet.getString("hora_entrada"), resultSet.getString("observacao"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return registro;
    }
    
    public static Registro buscarRegistro(int id){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id ="+id+"");
        Registro registro = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                registro = new Registro(resultSet.getInt("id"),resultSet.getInt("id_funcionario"), resultSet.getString("data"), resultSet.getString("hora_entrada"),resultSet.getString("hora_saida"), resultSet.getString("observacao"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return registro;
    }
    
    public static ArrayList<Registro> buscarRegistros(int idFuncionario){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id_funcionario = '"+idFuncionario+"'  ORDER BY SUBSTRING(data,7,4) DESC, SUBSTRING(data,4,2) DESC, SUBSTRING(data,1,2) DESC, SUBSTRING(hora_entrada,1,2) DESC, SUBSTRING(hora_entrada,4,2) DESC");
        ArrayList<Registro> registros = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                registros.add(new Registro(resultSet.getInt("id"),resultSet.getInt("id_funcionario"), resultSet.getString("data"), resultSet.getString("hora_entrada"),resultSet.getString("hora_saida"), resultSet.getString("observacao")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return registros;
    }
    
    public static ArrayList<Registro> buscarRegistros(int idFuncionario,String mes){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id_funcionario = "+idFuncionario+" AND data LIKE '__/"+mes+"' ORDER BY SUBSTRING(data,7,4) DESC, SUBSTRING(data,4,2) DESC, SUBSTRING(data,1,2) DESC, SUBSTRING(hora_entrada,1,2) DESC, SUBSTRING(hora_entrada,4,2) DESC");
        ArrayList<Registro> registros = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                registros.add(new Registro(resultSet.getInt("id"),resultSet.getInt("id_funcionario"), resultSet.getString("data"), resultSet.getString("hora_entrada"),resultSet.getString("hora_saida"), resultSet.getString("observacao")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return registros;
    }
    
    public static void excluirRegistro(int id){
        new Conexao();
        Registro registro = Registro.buscarRegistro(id);
        new Log(Principal.idFuncionarioLogado, Conexao.getHoraAtual(), Conexao.getDataAtual(),Funcionario.buscarFuncionario(Principal.idFuncionarioLogado).getNome()+" excluiu um reg. de "+Funcionario.buscarFuncionario(registro.idFuncionario).getNome()+". Registro: Data:"+registro.data+" Hr Ent.: "+registro.horaEntrada+" Hr Saida: "+registro.horaSaida).guarda();
        Conexao.exAtualizacao("DELETE FROM registro WHERE id = "+id);
    }
    
    @Override
    public String toString(){
        return "Registro: \n ID: "+id+" ID Funcionario: "+idFuncionario+" Data: "+data+" Hora Entrada: "+horaEntrada+" Hora Saida: "+horaSaida+" Observacao: "+observacao+"\n";
    }
    
    /* TABLE VIEW*/
    public StringProperty dataProperty(){
        return new SimpleStringProperty(data);
    }
    
    public StringProperty feriadoProperty(){
        return new SimpleStringProperty(Feriado.verificaFeriado(data)?"X":"");
    }
    
    public StringProperty horaEntradaProperty(){
        return new SimpleStringProperty(horaEntrada);
    }
    
    public StringProperty horaSaidaProperty(){
        return new SimpleStringProperty((!horaSaida.equals("")?horaSaida:"Aberto"));
    }
    
    public StringProperty observacaoProperty(){
        return new SimpleStringProperty(observacao);
    }
    
    public StringProperty totalHorasProperty(){
        return new SimpleStringProperty(getTotalHoras());
    }
}
