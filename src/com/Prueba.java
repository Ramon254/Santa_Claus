package com;
import java.util.Scanner;

public class Prueba {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Semaforos.init();
        Santa santa = new Santa();
        Reno reno = new Reno();
        Duende duende = new Duende();

        santa.start();

        /* Para la interfaz:
            Esto se imprime en desorden a veces ya que imprime cada hilo, por lo que a veces se ejecuta uno antes que otro.
            Para hacer la interfaz, lo único sería que en lugar de esperar una entrada de texto, cada que se hiciera click
            en el botón de renos o duendes, correr su función run(), y ya se hace el desmadre, en algunos
            Las partes donde está para imprimir solo habría que cambiar para que ponga las imágenes.
         */
        while (true) {
            System.out.println("[1] Nuevo duende [2] Nuevo reno");
            String opc = sc.next();
            if (opc.equals("1")) {
                duende.run();
            } else if (opc.equals("2")) {
                reno.run();
            }
        }
    }
}
