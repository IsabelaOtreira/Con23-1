import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Teste {
    private static List<Passageiro> passageiros;
    
    public static void main(String[] args) {     
       
        int numAndares = 3;
        int andarInicialElevador = 1;
        int capacidadeElevador = 1;
        int numPassageiros = 10; 
        JFrame frame = new JFrame();
       
        Predio predio = new Predio(numAndares, andarInicialElevador, capacidadeElevador);
        Elevador elevador = new Elevador (numPassageiros, numPassageiros);
  
            passageiros = new ArrayList<>();
        for (int i = 0; i < numPassageiros; i++) {
            int andarOrigem = (int) (Math.random() * numAndares);
            int andarDestino = (int) (Math.random() * numAndares);
            while (andarOrigem == andarDestino) {
                andarDestino = (int) (Math.random() * numAndares);
            }
            Passageiro passageiro = new Passageiro(andarOrigem, andarDestino, predio, elevador);
            passageiros.add(passageiro);
        }

   
        Visualizacao visualizacao = new Visualizacao(predio, elevador, passageiros);
        frame.getContentPane().add(visualizacao);
        frame.pack();
        frame.setVisible(true);
        Thread threadVisualizacao = new Thread(visualizacao);
        threadVisualizacao.start();
       


      
        List<Thread> threadsPassageiros = new ArrayList<>();
        for (Passageiro passageiro : passageiros) {
            threadsPassageiros.add(new Thread(passageiro));
        }
        
   

        for (Thread t : threadsPassageiros) {
            t.start();
        }

      
        for (Thread t : threadsPassageiros) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
        threadVisualizacao.interrupt();
    }
}

