package com;

public class Reno extends Thread {

    @Override
    public void run() {
        vacaciones();
        try {
            Semaforos.mutex.acquire();
            Semaforos.renos += 1;
            if (Semaforos.renos == Semaforos.MAX_RENOS) {
                Semaforos.santaSem.release();
            }
            Semaforos.mutex.release();
            Semaforos.renosSem.acquire();
            engancharTrineo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void vacaciones() {
        System.out.println("Reno esperando a Santa");
    }

    public void engancharTrineo() {
        System.out.println("Reno enganchado en el trineo. Hay " + Semaforos.renos + " renos enganchados.");
    }

}
