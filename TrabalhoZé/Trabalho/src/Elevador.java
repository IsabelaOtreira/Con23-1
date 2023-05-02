import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevador {
    private int andarAtual;
    private int capacidade;
    private int numPassageiros;
    private List<Integer> destinos;
    private Semaphore mutex;
    private Semaphore semaforoAndar;
    private Semaphore semaforoPorta;
    private LinkedList<Integer> chamados = new LinkedList<Integer>();
	private Object passageiroAtual;
	private boolean portasAbertas;
	private final Lock lock = new ReentrantLock();
	 private List<Passageiro> passageiros;

    public Elevador(int andarInicial, int capacidade) {
        this.andarAtual = andarInicial;
        this.capacidade = capacidade;
        this.numPassageiros = 0;
        this.destinos = new ArrayList<>();
        this.mutex = new Semaphore(1);
        this.semaforoAndar = new Semaphore(0);
        this.semaforoPorta = new Semaphore(1);
        this.passageiros = new ArrayList<>();
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    public List<Integer> getDestinos() {
        return destinos;
    }
    
    public void adicionarPassageiro(Passageiro p) {
        lock.lock();
        try {
            passageiros.add(p);
        } finally {
            lock.unlock();
        }
    }

    public Semaphore getMutex() { 
        return mutex;
    }

    public Semaphore getSemaforoAndar() {
        return semaforoAndar;
    }

    public Semaphore getSemaforoPorta() {
        return semaforoPorta;
    }

    public void Entrar(int andar) {
        try {
            mutex.acquire();
            if (numPassageiros < capacidade) {
                destinos.add(andar);
                numPassageiros++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
    }

    public void Sair(int andar) {
        try {
            mutex.acquire();
            if (destinos.contains(andar)) {
                destinos.remove(Integer.valueOf(andar));
                numPassageiros--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
    }
    
    public void adicionarChamado(int andar) {
        chamados.add(andar); 
    }

    public void VisitarAndar() {
        semaforoAndar.release();
        try {
            semaforoPorta.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaforoPorta.release();
    }

    public void MoverParaAndar(int andar) {
        try {
            semaforoAndar.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.andarAtual = andar;
    }

    public void removerPassageiro() {
        Semaphore mutex = getMutex(); 
        try {
            mutex.acquire(); 
            this.passageiroAtual = null; 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release(); 
        }
    }

	
	public boolean isPortasAbertas() {
	    return this.portasAbertas;
	}

	public Passageiro[] getPassageiros() {
        lock.lock();
        try {
            return passageiros.toArray(new Passageiro[0]);
        } finally {
            lock.unlock();
        }
    }

	
		
	}

