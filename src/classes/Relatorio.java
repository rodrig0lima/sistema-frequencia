/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Rodrigo
 */
public class Relatorio {
    public static void main(String [] args){
        GeradorRelatorioFrequencia relatorio = new GeradorRelatorioFrequencia();
        try{
            relatorio.imprimir(Registro.buscarRegistros(2, "01/2016"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
