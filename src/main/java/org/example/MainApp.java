package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainApp extends JFrame {
    DibujoCubo dibujo;

    public MainApp() {
        super("Dibujo de Cubo con Colores");
        dibujo = new DibujoCubo();
        add(dibujo);
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregar KeyListener para rotar el cubo
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                switch (key) {
                    case 'r': case 'R':
                        dibujo.rotar(10);   // Rotación en sentido horario
                        break;
                    case 'l': case 'L':
                        dibujo.rotar(-10);  // Rotación en sentido antihorario
                        break;
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.setVisible(true);
    }
}

class DibujoCubo extends JPanel {
    private int angulo = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        // Coordenadas en 3D de los vértices del cubo
        int[][] vertices3D = {
                {-50, -50, -50}, {50, -50, -50}, {50, 50, -50}, {-50, 50, -50},   // Cara trasera
                {-30, -30, 50}, {70, -30, 50}, {70, 70, 50}, {-30, 70, 50}        // Cara delantera
        };

        // Calcular el ángulo de rotación en radianes
        double radianes = Math.toRadians(angulo);

        // Proyectar el cubo en 2D y desplazarlo al centro de la ventana
        int xCentro = getWidth() / 2;
        int yCentro = getHeight() / 2;
        int[][] vertices2D = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int x = vertices3D[i][0];
            int y = vertices3D[i][1];
            int z = vertices3D[i][2];

            // Rotación en torno al eje Y
            int rotX = (int) (x * Math.cos(radianes) - z * Math.sin(radianes));
            int rotZ = (int) (x * Math.sin(radianes) + z * Math.cos(radianes));

            // Proyección en 2D
            vertices2D[i][0] = xCentro + rotX;
            vertices2D[i][1] = yCentro + y;
        }

        // Definir una paleta de 12 colores diferentes para cada línea del cubo
        Color[] colores = {
                Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,
                Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.PINK,
                Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK, Color.GRAY
        };

        // Dibujar las líneas de la cara trasera
        g2d.setColor(colores[0]);
        g2d.drawLine(vertices2D[0][0], vertices2D[0][1], vertices2D[1][0], vertices2D[1][1]);
        g2d.setColor(colores[1]);
        g2d.drawLine(vertices2D[1][0], vertices2D[1][1], vertices2D[2][0], vertices2D[2][1]);
        g2d.setColor(colores[2]);
        g2d.drawLine(vertices2D[2][0], vertices2D[2][1], vertices2D[3][0], vertices2D[3][1]);
        g2d.setColor(colores[3]);
        g2d.drawLine(vertices2D[3][0], vertices2D[3][1], vertices2D[0][0], vertices2D[0][1]);

        // Dibujar las líneas de la cara delantera
        g2d.setColor(colores[4]);
        g2d.drawLine(vertices2D[4][0], vertices2D[4][1], vertices2D[5][0], vertices2D[5][1]);
        g2d.setColor(colores[5]);
        g2d.drawLine(vertices2D[5][0], vertices2D[5][1], vertices2D[6][0], vertices2D[6][1]);
        g2d.setColor(colores[6]);
        g2d.drawLine(vertices2D[6][0], vertices2D[6][1], vertices2D[7][0], vertices2D[7][1]);
        g2d.setColor(colores[7]);
        g2d.drawLine(vertices2D[7][0], vertices2D[7][1], vertices2D[4][0], vertices2D[4][1]);

        // Dibujar las líneas que conectan ambas caras
        g2d.setColor(colores[8]);
        g2d.drawLine(vertices2D[0][0], vertices2D[0][1], vertices2D[4][0], vertices2D[4][1]);
        g2d.setColor(colores[9]);
        g2d.drawLine(vertices2D[1][0], vertices2D[1][1], vertices2D[5][0], vertices2D[5][1]);
        g2d.setColor(colores[10]);
        g2d.drawLine(vertices2D[2][0], vertices2D[2][1], vertices2D[6][0], vertices2D[6][1]);
        g2d.setColor(colores[11]);
        g2d.drawLine(vertices2D[3][0], vertices2D[3][1], vertices2D[7][0], vertices2D[7][1]);
    }

    // Método para actualizar el ángulo y redibujar
    public void rotar(int grados) {
        angulo += grados;
        repaint();
    }
}
