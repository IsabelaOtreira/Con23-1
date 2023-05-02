import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Teste {
    public static void main(String[] args) {     
       
        int numAndares = 3;
        int andarInicialElevador = 1;
        int capacidadeElevador = 1;
        int numPassageiros = 10; 
        JFrame frame = new JFrame();
        Predio predio = new Predio(numAndares, andarInicialElevador, capacidadeElevador);
        Elevador elevador = new Elevador (numPassageiros, numPassageiros);
        
        
       // criar passageiros
        List<Thread> threadsPassageiros = new ArrayList<>();
        for (int i = 0; i < numPassageiros; i++) {
            int andarOrigem = (int) (Math.random() * numAndares);
            int andarDestino = (int) (Math.random() * numAndares);
            while (andarOrigem == andarDestino) {
                andarDestino = (int) (Math.random() * numAndares);
            }
            Passageiro passageiro = new Passageiro(andarOrigem, andarDestino, predio, elevador);
            threadsPassageiros.add(new Thread(passageiro));
        }

        // converte a lista de passageiros em um array de passageiros
        Passageiro[] passageiros = new Passageiro[threadsPassageiros.size()];
        for (int i = 0; i < threadsPassageiros.size(); i++) {
            passageiros[i] = (Passageiro) threadsPassageiros.get(i);
        }

        // instancia a classe Visualizacao
        Visualizacao visualizacao = new Visualizacao(predio, elevador, passageiros);

        for (Thread thread : threadsPassageiros) {
            thread.start();
        }

        while (true) {
            visualizacao.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
      
        // iniciar threads dos passageiros
        for (Thread t : threadsPassageiros) {
            t.start();
        }

        // aguardar término das threads dos passageiros
        for (Thread t : threadsPassageiros) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
