import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends JFrame {
    JPanel panel;
    JPanel footerPanel;


    JButton id1Btn1;
    JTextField txtF1;

    JTextArea msgArea;
    JScrollPane scrollPane;

    public final String placeHolder1 = "Write Your message Here";


    Socket s = null;
    InputStreamReader in = null;
    OutputStreamWriter out = null;

    BufferedReader br = null;
    BufferedWriter bw = null;


    Client() {

        panel = new JPanel();
        footerPanel = new JPanel();
        id1Btn1 = new JButton("SendMsg ");
        txtF1 = new JTextField(placeHolder1);

        msgArea = new JTextArea();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(msgArea);

        // Message area setup
        msgArea.setEditable(false); // prevent user from editing
        msgArea.setLineWrap(true);  // wrap long lines
        msgArea.setWrapStyleWord(true);







        // In this BorderLayout we have only 4 place to place our components,
        // we can't place the button and the txtField in the footer
        // The solution is create another panel, txtF to W, Button to E



        panel.setLayout(new BorderLayout());
        footerPanel.setLayout(new BorderLayout());

        footerPanel.add(txtF1,BorderLayout.CENTER);  // we can use Center -> it takes the remaining space
        footerPanel.add(id1Btn1,BorderLayout.EAST);

        panel.add(footerPanel,BorderLayout.SOUTH);
        panel.add(scrollPane,BorderLayout.CENTER);

        footerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), "Chat Input"));


        id1Btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = txtF1.getText();
                if (!message.isEmpty() && !message.equals(placeHolder1)) {

                    try {
                        // send the msg:
                        bw.write(message);
                        bw.newLine();
                        bw.flush();  // send the msg when the EnterKey is pressed not when the buffer is full


                        // Show what u have said:
                        System.out.println("You: " + message);
                        addMsg("You: "+message);


                        if(message.equalsIgnoreCase("BYE"))
                        {
                            addMsg("System: Connection closed by Client.");
                            System.exit(0);  // Exit the app

                        }

                        //Clear input:
                        txtF1.setText(placeHolder1);



                        // Wait for response
                        String response = br.readLine();
                        if (response != null) {
                            addMsg("Server: " + response);

                            // Check if server says BYE
                            if (response.equalsIgnoreCase("BYE")) {
                                addMsg("System: Connection closed by server.");
                                System.exit(0);  // Exit the app
                            }

                        } else {
                            addMsg("Server: [no response]");
                        }


                    } catch (IOException ex) {
                        addMsg("System: Error sending Message");
                        ex.printStackTrace();

                    }





                }
            }
        });


        // Enter key to send message
        txtF1.addActionListener(e -> id1Btn1.doClick());

        // Memic PlaceHolder behaviour:
        txtF1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtF1.getText().equals(placeHolder1))
                {
                    txtF1.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txtF1.getText().isEmpty())
                {
                    txtF1.setText(placeHolder1);
                }
            }
        });


        //Connect To the Server:

        try {
            s = new Socket("localhost",1234);
            in = new InputStreamReader(s.getInputStream());
            out = new OutputStreamWriter(s.getOutputStream());

            br = new BufferedReader(in);
            bw = new BufferedWriter(out);


        } catch (IOException e) {
            e.printStackTrace();

        }






        this.setContentPane(panel);
        this.setTitle("Natsapp");
        this.setSize(400,500);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // centers the window

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To end the program entirely not just hide It



        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (bw != null) {
                        bw.write("BYE");
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    }
                    if (br != null) br.close();
                    if (s != null) s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }


    public void addMsg(String message)
    {
        msgArea.append(message+'\n');
    }

    public static void main(String[] args) {

        Client c = new Client();





    }
}
