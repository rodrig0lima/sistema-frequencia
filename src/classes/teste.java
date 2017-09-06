/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class teste {
    public static void main(String [] args){
        //System.out.println(Funcionario.verificarLogin("rodrigo", "853428"));
        
       /* ArrayList<Funcionario> funcionarios = Funcionario.buscarFuncionarios("R");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }*/
        //Registro registro = new Registro(1, Conexao.getDataAtual(), Conexao.getHoraAtual(), "");
        Funcionario funcionario = Funcionario.buscarFuncionario(1);
        funcionario.saida(Conexao.getHoraAtual(), "");
//        System.out.println("a:"+Conexao.getDataAtual());
        System.out.println(funcionario.getHorasTrabalhadas("01/2016"));
        System.out.println(funcionario.getQtMesesTrabalhados());
        ArrayList<String> trabalhados = funcionario.getMesesTrabalhados();
        for (String trabalhado : trabalhados) {
            System.out.println(trabalhado);
        }
        System.out.println(funcionario.getUltimoRegistro());
        //new Funcionario("teste", "teste", false, "Teste1", 3).guardar();
        //new Funcionario("teste2", "teste2", false, "Teste2", 3).guardar();
        //System.out.println(funcionario.entrada(Conexao.getDataAtual(),"18:00:00", ""));
        //System.out.println(funcionario.saida(Conexao.getHoraAtual(), "Teste"));
        //System.out.println(funcionario.entrada("21/12/2015","12:00:00", ""));
        Registro registro = Registro.getUltimoRegistroAberto(1);
        System.out.println(registro);
        ArrayList<Registro> registros = Registro.buscarRegistros(1, "01/2016");
        for (Registro registro1 : registros) {
            System.out.println(registro1);
        }
        new Feriado("01/01/2016").guarda();
        new Feriado("02/01/2016").guarda();
        System.out.println("Feriados em janeiro:");
        ArrayList<Feriado> feriados = Feriado.buscarFeriados("01/2016");
        for (Feriado feriado : feriados) {
            System.out.println(feriado);
        }
        System.out.println(Feriado.verificaFeriado("02/01/2016"));
        ArrayList<Mes> meses = Mes.buscarMeses();
        for (Mes mes : meses) {
            System.out.println(mes);
        }
        ArrayList<Log> logs = Log.buscarLogPorMesFuncionario("Tes", "01/2016");
        for (Log log : logs) {
            System.out.println(log);
        }
        
    }
}
