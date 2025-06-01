import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// This class represents the server-side application for the chat system.
// It listens for client connections and facilitates message exchange.
public class Server {
    public static void main(String[] args)
    {
        // Network components
        Socket s = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = null;

        try {
            // Initialize the server socket on port 1234
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continuously listen for client connections
        while(true)
        {
            // This first while loop is: to create a new socket when a client accepts a connection
            try{
                s = serverSocket.accept();
                inputStreamReader = new InputStreamReader(s.getInputStream());
                outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                // Second while loop for data communication between client & server:
                while(true)
                {
                    // Wait for the client response
                    String msgCli = bufferedReader.readLine();
                    System.out.println(msgCli);

                    System.out.println("Client: "+msgCli);
                    bufferedWriter.write("MSG received");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    // Break the loop if the client sends "BYE"
                    if(msgCli.equalsIgnoreCase("BYE")) break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                // Close all resources
                try{
                    if (s != null)
                        s.close();

                    if (inputStreamReader != null)
                        inputStreamReader.close();
                    if(outputStreamWriter != null)
                        outputStreamWriter.close();

                    if(bufferedReader != null)
                        bufferedReader.close();

                    if(bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
