/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public class Conexao {
    private static Connection conexao;
    private static Statement statement;
    
    public Conexao(){
        if(conexao==null){
            try{
                Class.forName("com.mysql.jdbc.Driver");  
                conexao = DriverManager.getConnection("jdbc:mysql://localhost/frequencia","root","");
                //conexao = DriverManager.getConnection("jdbc:mysql://localhost/frequencia","root","123456");
                statement = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            }catch(SQLException e){
                e.printStackTrace();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        "Erro ao conectar com o banco de dados...",
                        "Atenção", JOptionPane.WARNING_MESSAGE); 
                e.printStackTrace();
            }
        }
    }
    
    public static ResultSet exConsulta(String sql) {
        ResultSet rst = null;
        try {
            rst = statement.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                        e.getMessage(),
                        "Atenção", JOptionPane.WARNING_MESSAGE); 
        }
        return rst;
    }
    
    public static void exAtualizacao(String sql){
        try{
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                    e.getMessage(),
                    "Atenção", JOptionPane.WARNING_MESSAGE); 
            /*JOptionPane.showMessageDialog(null, 
                    "Erro ao realizar operação no banco de dados...",
                    "Atenção", JOptionPane.WARNING_MESSAGE); */
        }
        
    }
    
    public static void exAtualizacao(String sql,byte[] vetor){
        try{
            PreparedStatement pstmt =  conexao.prepareStatement(sql);
            pstmt.setBytes(1, vetor);
            pstmt.execute();
        }catch(SQLException e){
            
            e.printStackTrace();
        }
    }
    
    public static String getDataAtual(){
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        DateFormat formataData = DateFormat.getDateInstance();
        return formataData.format(data);
    }
    
    public static String getHoraAtual(){
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        DateFormat hora = DateFormat.getTimeInstance();
        return hora.format(data);
    }
}
