package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private JPanel panel1;
    private JButton btnAddReno;
    private JButton btnAddDuende;
    private JPanel panelDuende;
    private JPanel panelSanta;
    private JPanel panelReno;
    private JLabel labelDuendes;
    private JLabel labelRenos;
    private JLabel labelImgDuendes;
    private JLabel labelImgSanta;
    private JLabel labelImgReno;
    private JLabel labelSanta;

    BufferedImage taller = ImageIO.read(new File("src/com/img/taller.jpg"));
    BufferedImage duende = ImageIO.read(new File("src/com/img/duende.jpg"));
    BufferedImage reno = ImageIO.read(new File("src/com/img/reno.png"));
    BufferedImage dormido = ImageIO.read(new File("src/com/img/dormido.jpg"));
    BufferedImage repartiendo = ImageIO.read(new File("src/com/img/repartiendo.jpg"));

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Main() throws IOException {
        labelImgDuendes.setIcon(new ImageIcon(new ImageIcon(duende).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        labelImgReno.setIcon(new ImageIcon(new ImageIcon(reno).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        labelImgSanta.setIcon(new ImageIcon(new ImageIcon(dormido).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        Santa santa = new Santa();
        Reno reno = new Reno();
        Duende duende = new Duende();

        Semaforos.init();
        santa.start();

        btnAddDuende.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                duende.run();
            }
        });

        btnAddReno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reno.run();
            }
        });
    }

    // --------------------------------
    // Clase de Santa
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
            labelImgSanta.setIcon(new ImageIcon(new ImageIcon(repartiendo).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            labelSanta.setText("Preparando trineo");
            Thread.sleep(3000);
            labelSanta.setText("Santa fue a repartir. Feliz Navidad!");
            labelImgSanta.setIcon(new ImageIcon(new ImageIcon(dormido).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            labelSanta.setText("Santa está dormido");
            labelDuendes.setText("Duendes esperando a Santa: 0");
            labelRenos.setText("Renos esperando en el trineo: 0");
        }

        private void ayudarDuendes() throws InterruptedException {
            labelImgSanta.setIcon(new ImageIcon(new ImageIcon(taller).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            labelSanta.setText("Ayudando a un grupo de duendes");
        }
    }

    // --------------------------------
    // Clase de Reno
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
            labelRenos.setText("Hay " + Semaforos.renos + " renos enganchados.");
        }

    }


    // --------------------------------
    // Clase de Duende
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
            labelDuendes.setText("Hay " + Semaforos.duendes + " duendes esperando.");
        }

    }


}
