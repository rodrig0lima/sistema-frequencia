/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
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
public class Funcionario {
    private static DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
    private int id;
    private String login;
    private String senha;
    private String nome;
    private int tipoFuncionario;
    private byte[] template;
    
    public Funcionario(int id, String login, String senha, String nome, int tipoFuncionario) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoFuncionario = tipoFuncionario;
    }
    
    public Funcionario(String login, String senha, String nome, int tipoFuncionario) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoFuncionario = tipoFuncionario;
    }
    
    public Funcionario(String login, String senha, String nome, int tipoFuncionario,byte[] template) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoFuncionario = tipoFuncionario;
        this.template = template;
    }
    
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public int getTipoFuncionario() {
        return tipoFuncionario;
    }
    
    public byte[] getTemplate() {
        return template;
    }
    
    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoFuncionario(int tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }
    
    public boolean entrada(String data,String horaEntrada,String observacao){
        if(Registro.getUltimoRegistroAberto(id)==null){
            Registro registro = new Registro(id, data, horaEntrada,(observacao.equals("")?"":"Entrada:"+observacao));
            registro.guardar();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean saida(String horaSaida,String observacao){
        Registro registro = Registro.getUltimoRegistroAberto(id);
        if(registro==null){
            return false;
        }
        registro.setHoraSaida(horaSaida);
        registro.setObservacao(registro.getObservacao()+(observacao.equals("")?"":"Saida:"+observacao));
        registro.guardar();
        return true;
    }
    
    public boolean saida(String data,String horaSaida,String observacao){
        Registro registro = Registro.getUltimoRegistroAberto(id,data);
        if(registro==null){
            return false;
        }
        registro.setHoraSaida(horaSaida);
        registro.setObservacao(registro.getObservacao()+" Saida:"+observacao);
        registro.guardar();
        return true;
    }
    
    public int getQtMesesTrabalhados(){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT COUNT(DISTINCT(SUBSTRING(data,4,7))) AS meses FROM funcionario,registro WHERE registro.id_funcionario = '"+id+"'");
        int valor = 0;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                valor = resultSet.getInt("meses");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return valor;
    }
    
    public String getHorasTrabalhadas(String mes){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT SUBSTRING(hora_entrada,1,2) as hora_e,SUBSTRING(hora_entrada,4,2) as minuto_e,SUBSTRING(hora_entrada,7,2) as segundo_e,SUBSTRING(hora_saida,1,2) as hora_s,SUBSTRING(hora_saida,4,2) as minuto_s,SUBSTRING(hora_saida,7,2) as segundo_s FROM registro WHERE id_funcionario = "+id+" AND data LIKE '__/"+mes+"' AND hora_saida <> ''");
        int segundos = 0;
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                segundos += resultSet.getInt("hora_s")*3600+resultSet.getInt("minuto_s")*60+resultSet.getInt("segundo_s")-(resultSet.getInt("hora_e")*3600+resultSet.getInt("minuto_e")*60+resultSet.getInt("segundo_e"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        int hora = segundos/3600;
        int minuto = (segundos%3600)/60;
        int segundo = (segundos%3600)%60;
        return (hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto)+":"+(segundo<10?"0"+segundo:segundo);
    }
    
    
    public ArrayList<String> getMesesTrabalhados(){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT DISTINCT(SUBSTRING(data,4,7)) AS mes FROM funcionario,registro WHERE registro.id_funcionario = '"+id+"' ORDER BY SUBSTRING(data,7,4) DESC,SUBSTRING(data,4,2) DESC");
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
    
    public Registro getUltimoRegistro(){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM registro WHERE id_funcionario = "+id+" ORDER BY id DESC");
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
    
    public void guardar(){
        new Conexao();
        if(id==0){
            Conexao.exAtualizacao("INSERT INTO funcionario(login,senha,nome,tipo_funcionario) VALUES('"+login+"','"+senha+"','"+nome+"',"+tipoFuncionario+")");
            ResultSet resultSet = Conexao.exConsulta("SELECT MAX(id) as maxid FROM funcionario WHERE login = '"+login+"'");
            try{
                resultSet.beforeFirst();
                resultSet.next();
                id = resultSet.getInt("maxid");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            Conexao.exAtualizacao("UPDATE funcionario SET login='"+login+"',senha='"+senha+"',nome='"+nome+"',tipo_funcionario="+tipoFuncionario+" WHERE id = "+id);
        }
    }
    
    public void guardarTemplate(){
        new Conexao();
        if(id==0)
            return;
        Conexao.exAtualizacao("UPDATE funcionario SET template = ? WHERE id = "+id,template);
        
    }
    
    /*MÉTODOS ESTÁTICOS */
    
    public static Funcionario buscarFuncionario(int id){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT id,login,senha,nome,tipo_funcionario FROM funcionario WHERE id = "+id);
        Funcionario funcionario = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                funcionario = new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"), resultSet.getString("nome"), resultSet.getInt("tipo_funcionario"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return funcionario;
    }
    
    public static Funcionario buscarFuncionario(String login){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT id,login,senha,nome,tipo_funcionario FROM funcionario WHERE login = '"+login+"'");
        Funcionario funcionario = null;
        try{
            resultSet.last();
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                resultSet.next();
                funcionario = new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"), resultSet.getString("nome"), resultSet.getInt("tipo_funcionario"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return funcionario;
    }
    
    public static Funcionario buscarFuncionario(DPFPFeatureSet features){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT * FROM funcionario");
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                byte[] blob = resultSet.getBytes("template");
                DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
                template.deserialize(blob);
                DPFPVerificationResult result = 
                verificator.verify(features, template);
                if (result.isVerified())
                    return new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"),resultSet.getString("nome"), resultSet.getInt("tipo_funcionario"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Funcionario> buscarFuncionariosGerente(String palavraChave){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT id,login,senha,nome,tipo_funcionario FROM funcionario WHERE nome LIKE '%"+palavraChave+"%' AND tipo_funcionario<>1 AND id <> "+Principal.idFuncionarioLogado);
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                funcionarios.add(new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"),resultSet.getString("nome"), resultSet.getInt("tipo_funcionario")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return funcionarios;
    }
    
    public static ArrayList<Funcionario> buscarFuncionariosAdministrador(String palavraChave){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT id,login,senha,nome,tipo_funcionario FROM funcionario WHERE nome LIKE '%"+palavraChave+"%' AND id <> "+Principal.idFuncionarioLogado);
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                funcionarios.add(new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"),resultSet.getString("nome"), resultSet.getInt("tipo_funcionario")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return funcionarios;
    }
    
    public static ArrayList<Funcionario> buscarFuncionariosTrabalharamMes(String mes){
        new Conexao();
        ResultSet resultSet = Conexao.exConsulta("SELECT DISTINCT funcionario.*  FROM registro,funcionario WHERE registro.data LIKE '__/"+mes+"' AND funcionario.id = registro.id_funcionario");
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                funcionarios.add(new Funcionario(resultSet.getInt("id"),resultSet.getString("login"), resultSet.getString("senha"),resultSet.getString("nome"), resultSet.getInt("tipo_funcionario")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return funcionarios;
    }
    
    public static Funcionario verificarLogin(String login,String senha){
        Funcionario funcionario = buscarFuncionario(login);
        if(funcionario==null)
            return null;
        if(funcionario.senha.equals(senha)){
            return funcionario;
        }else{
            return null;
        }
    }
    
    public static void excluirFuncionario(int id){
        new Conexao();
        new Log(Principal.idFuncionarioLogado, Conexao.getHoraAtual(), Conexao.getDataAtual(),"Excluiu o(a) funcionário(a) "+buscarFuncionario(id).getNome()+" e todos os seus registros.").guarda();
        Conexao.exAtualizacao("DELETE FROM registro WHERE id_funcionario = "+id);
        Conexao.exAtualizacao("DELETE FROM log WHERE id_funcionario = "+id);
        Conexao.exAtualizacao("DELETE FROM funcionario WHERE id = "+id);
    }
    
    @Override
    public String toString(){
        return "\nFuncionario \nID : "+id+" Nome: "+nome+" login: "+login+" senha: "+senha+" tipoFuncionario: "+tipoFuncionario;
    }
    
    /*TABLE VIEW*/
    public StringProperty nomeProperty(){
        return new SimpleStringProperty(nome);
    }
    
    public StringProperty dataProperty(){
        Registro registro = getUltimoRegistro();
        if(registro!=null){
            return getUltimoRegistro().dataProperty();
        }else{
            return new SimpleStringProperty("-");
        }
    }
    
    public StringProperty horaEntradaProperty(){
        Registro registro = getUltimoRegistro();
        if(registro!=null){
            return getUltimoRegistro().horaEntradaProperty();
        }else{
            return new SimpleStringProperty("-");
        }
    }
    
    public StringProperty horaSaidaProperty(){
        Registro registro = getUltimoRegistro();
        if(registro!=null){
            return getUltimoRegistro().horaSaidaProperty();
        }else{
            return new SimpleStringProperty("-");
        }
    }
    
    public StringProperty totalHoras(){
        return new SimpleStringProperty(getHorasTrabalhadas(Conexao.getDataAtual().substring(3)));
    }
    
    public StringProperty totalHoras(String mes){
        return new SimpleStringProperty(getHorasTrabalhadas(mes));
    }
    
    public StringProperty qtMesesTrabalhados(){
        return new SimpleStringProperty(String.valueOf(getQtMesesTrabalhados()));
    }
}
