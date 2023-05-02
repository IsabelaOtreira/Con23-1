import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

public class Visualizacao extends JPanel implements Runnable {

    private final String PREDIO_IMAGE_NAME = "predioze.jpg";
    private final String PASSAGEIRO_IMAGE_NAME = "passageiroze.jpg";
    private final String ELEVADOR_ABERTO_IMAGE_NAME = "elevadorabertoze.jpg";
    private final String ELEVADOR_FECHADO_IMAGE_NAME = "elevadorfechadoze.jpg";

    private final Image predioImage;
    private final Image passageiroImage;
    private final Image elevadorAbertoImage;
    private final Image elevadorFechadoImage;

    private final Predio predio;
    private final Elevador elevador;
    private final List<Passageiro> passageiros;

    public Visualizacao(Predio predio, Elevador elevador, List<Passageiro> passageiros) {
        this.predio = predio;
        this.elevador = elevador;
        this.passageiros = passageiros;

       
        
        passageiroImage = Toolkit.getDefaultToolkit().getImage(PASSAGEIRO_IMAGE_NAME);
        elevadorAbertoImage = Toolkit.getDefaultToolkit().getImage(ELEVADOR_ABERTO_IMAGE_NAME);
        elevadorFechadoImage = Toolkit.getDefaultToolkit().getImage(ELEVADOR_FECHADO_IMAGE_NAME);
        predioImage = Toolkit.getDefaultToolkit().getImage(PREDIO_IMAGE_NAME);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        g.drawImage(predioImage, 0, 0, 600, 600, this);

       
        Image elevadorImage = elevador.isPortasAbertas() ? elevadorAbertoImage : elevadorFechadoImage;
        int elevadorY = (5 - elevador.getAndarAtual()) * 100; 
        g.drawImage(elevadorImage, 250, elevadorY, 100, 100, this);

       
        for (Passageiro passageiro : passageiros) {
            int passageiroY = (5 - passageiro.getAndarAtual()) * 100; 
            g.drawImage(passageiroImage, passageiro.getX(), passageiroY, 50, 50, this);
        }
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            repaint(); 
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

   
}

