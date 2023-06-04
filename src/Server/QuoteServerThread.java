package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class QuoteServerThread {
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super();
        System.out.println( "This PC IP address is: "+ InetAddress.getLocalHost() );
        socket = new DatagramSocket(4445,InetAddress.getLocalHost() );

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

   /* public void run() {
        System.out.println("ready on "+ socket.getLocalSocketAddress() );
        while (moreQuotes) {
            try {
                byte[] buf = new byte[2560];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.println("listening...");
                socket.receive(packet);

                // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();

                buf = dString.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("request from "+address+" on "+port);
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }*/
}
