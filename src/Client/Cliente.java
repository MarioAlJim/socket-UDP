package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
/*{
    Mario Alberto Jimenez Jimenez
        }*/
public class Cliente {
    public static void main(String[] args) {
        String mensaje = "";
        InetAddress direccionServidor;
        int puertoServidor;
        try {
            direccionServidor = InetAddress.getByName("127.0.0.1"); // Dirección IP del servidor
            puertoServidor = 5555;
            DatagramSocket socketUDP = new DatagramSocket();
            do {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Escribe <salir para terminar> una expresión matemática: ");
                mensaje = in.readLine();
                if (mensaje.compareToIgnoreCase("salir") == 0) {
                    byte[] datos = mensaje.getBytes();
                    DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccionServidor, puertoServidor);

                    socketUDP.send(paquete);
                    System.out.println("Mensaje enviado al servidor: " + mensaje);
                    break;
                }
                byte[] datos = mensaje.getBytes();
                DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccionServidor, puertoServidor);

                socketUDP.send(paquete);
                System.out.println("Mensaje enviado al servidor: " + mensaje);

                byte[] buffer = new byte[1024]; // Buffer para almacenar los datos recibidos
                DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);

                socketUDP.receive(paqueteRespuesta);

                String mensajeRespuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                System.out.println("Respuesta recibida del servidor: " + mensajeRespuesta);
            } while (true);
            // Cierre del socket
            socketUDP.close();
            System.out.println("Socket UDP cerrado.");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
