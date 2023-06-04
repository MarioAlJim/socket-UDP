package Server;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*{
    Mario Alberto Jimenez Jimenez
        }*/
public class Servidor {

    public static void main(String[] args) throws IOException {
        int puerto = 5555; // Puerto que deseas utilizar
        try {
            DatagramSocket socketUDP = new DatagramSocket(puerto);
            System.out.println("Servidor UDP iniciado. Esperando mensajes...");

            byte[] buffer = new byte[2048];

            while (true) {
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(paqueteRecibido);

                String mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Mensaje recibido: " + mensajeRecibido);
                if (mensajeRecibido.equals("salir")) {
                    break;
                }

                String mensajeRespuesta = resolverExpresion(mensajeRecibido);
                byte[] datosRespuesta = mensajeRespuesta.getBytes();
                DatagramPacket paqueteRespuesta = new DatagramPacket(datosRespuesta, datosRespuesta.length,
                        paqueteRecibido.getAddress(), paqueteRecibido.getPort());

                socketUDP.send(paqueteRespuesta);
                System.out.println("Respuesta enviada al cliente: " + mensajeRespuesta);
            }
            socketUDP.close();
            System.out.println("Socket UDP cerrado.");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String resolverExpresion(String expresion){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine se = scriptEngineManager.getEngineByName("JavaScript");
        String resultado = null;
        //System.out.println(expresion);
        try{
            expresion.replaceAll("\\n", "");
            resultado = se.eval(expresion).toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
}