import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Predio {

    private static final int NUM_PASSAGEIROS = 10;
    private static final int NUM_ANDARES = 3;
    private static final int ANDAR_INICIAL_ELEVADOR = 1;

    private Elevador elevador;
    private List<Thread> passageiros;
   
   

    public Predio(int numAndares, int andarInicialElevador, int capacidadeElevador) {
        Semaphore mutex = new Semaphore(1);
        Semaphore entrada = new Semaphore(0);
        Semaphore saida = new Semaphore(0);

        this.elevador = new Elevador( 0, ANDAR_INICIAL_ELEVADOR);

        Thread elevadorThread = new Thread();
        elevadorThread.start();

        this.passageiros = new ArrayList<>();

        for (int i = 0; i < NUM_PASSAGEIROS; i++) {
            int andarInicial = new Random().nextInt(NUM_ANDARES) + 1; 
            int andarDestino = new Random().nextInt(NUM_ANDARES) + 1; 
            while (andarDestino == andarInicial) {
                andarDestino = new Random().nextInt(NUM_ANDARES) + 1; 
            }

            Passageiro passageiro = new Passageiro(andarInicial, andarDestino, this, elevador);
            Thread passageiroThread = new Thread(passageiro);
            passageiros.add(passageiroThread);
            passageiroThread.start();
        }
    }

    public static void main(String[] args) {
        new Predio(0, 0, 0);
    }

    public void chamarElevador(int andar) throws InterruptedException {
        Semaphore mutexElevador = elevador.getMutex();
        mutexElevador.acquire(); 

        
        elevador.adicionarChamado(andar);

        mutexElevador.release(); 
    }

	public void entrarElevador(int andarDestino)     {
	Semaphore mutex = elevador.getMutex(); 
    try {
        mutex.acquire(); 
        elevador.adicionarChamado(andarDestino); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        mutex.release();
    }
}

	public void sairElevador() {
	    Semaphore mutex = elevador.getMutex(); 
	    try {
	        mutex.acquire(); 
	        elevador.removerPassageiro(); 
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } finally {
	        mutex.release(); 
	    }
	}

	
	public int getAndarAtualElevador() {
	    return this.elevador.getAndarAtual();
	}

	public void removerPassageiro(Passageiro passageiro) {
			
	}
	
	public List<Passageiro> getPassageiros(int andar) {
	    List<Passageiro> passageirosNoAndar = new ArrayList<>();
	    for(Passageiro passageiro : elevador.getPassageiros()){
	        if (passageiro.getAndarAtual() == andar) {
	            passageirosNoAndar.add(passageiro);
	        }
	    }
	    return passageirosNoAndar;
	}

}
