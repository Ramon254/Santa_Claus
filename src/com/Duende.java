package com;

public class Duende extends Thread {

    @Override
    public void run() {
        try {
            Semaforos.mutex.acquire();
            Semaforos.duendes += 1;
            if (Semaforos.duendes == Semaforos.MAX_DUENDES) {
                Semaforos.santaSem.release();
            }
            Semaforos.mutex.release();
            Semaforos.duendesSem.acquire();
            obteniendoAyuda();
            Semaforos.mutex.acquire();
            Semaforos.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void obteniendoAyuda() {
        System.out.println("Duende esperando la ayuda de Santa. Hay " + Semaforos.duendes + " duendes esperando.");
    }

}
