package com;

public class Santa extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Santa está durmiendo");
                // Esperar el semáforo de Santa
                Semaforos.santaSem.acquire();
                // Esperar Mutex
                Semaforos.mutex.acquire();
                if (Semaforos.renos == Semaforos.MAX_RENOS) {
                    prepararTrineo();
                    for (int i = 0; i < Semaforos.MAX_RENOS; i++) {
                        Semaforos.renosSem.release();
                        Semaforos.renos = 0;
                    }
                } else if (Semaforos.duendes == Semaforos.MAX_DUENDES) {
                    ayudarDuendes();
                    for (int i = 0; i < Semaforos.MAX_DUENDES; i++) {
                        Semaforos.duendesSem.release();
                        Semaforos.duendes = 0;
                    }
                }
                Semaforos.mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepararTrineo() throws InterruptedException {
        System.out.println("Preparando trineo");
        Thread.sleep(100);
        System.out.println("Santa fue a repartir. Feliz Navidad!");
    }

    private void ayudarDuendes() {
        System.out.println("Ayudando un grupo de duendes");
    }

}
