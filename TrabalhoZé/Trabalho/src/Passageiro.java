import java.util.List;
import java.util.concurrent.Semaphore;

public class Passageiro implements Runnable {
    private int andarOrigem;
    private int andarDestino;
    private Predio predio;
    private Elevador elevador;

    public Passageiro(int andarOrigem, int andarDestino, Predio predio, Elevador elevador) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.predio = predio;
        this.elevador = elevador;
    }

   
    
    public void run() {
        try {
            predio.chamarElevador(andarOrigem);
            predio.entrarElevador(andarDestino);
            predio.sairElevador();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void esperarElevador() throws InterruptedException {
     
        int tempoEspera = (int) (Math.random() * 5000) + 1000;
        Thread.sleep(tempoEspera);
    }
    	public int getAndarAtual() {
            return elevador.getAndarAtual();
    	
    }

    	public int getX() {
    	    int andarAtual = getAndarAtual();
    	    List<Passageiro> passageiros = predio.getPassageiros(andarAtual);
    	    int index = passageiros.indexOf(this);
    	    int xInicial = 50; 
    	    int espacamento = 50; 
    	    return xInicial + index * espacamento;
    	}
}
