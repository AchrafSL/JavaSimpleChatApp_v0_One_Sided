import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args)
    {
        Socket s = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while(true)
        {
            // This first while loop is: to create a new socket when a client accepts a connection
            try{
                s = serverSocket.accept();
                inputStreamReader = new InputStreamReader(s.getInputStream());
                outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);


                //Second while loop for data communication between client & server:
                while(true)
                {
                    String msgCli = bufferedReader.readLine();  // waits for the client response
                    System.out.println(msgCli);

                    System.out.println("Client: "+msgCli);
                    bufferedWriter.write("MSG received");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(msgCli.equalsIgnoreCase("BYE")) break;



                }


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
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
