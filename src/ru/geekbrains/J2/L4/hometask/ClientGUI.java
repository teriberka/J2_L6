package ru.geekbrains.J2.L4.hometask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.logging.Logger;


// ДЗ #04:
// 1. Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
// done
//
// 2. Создать лог в файле (показать комментарием, где и как Вы планируете писать сообщение в файловый журнал).
// done
//
// 3. Прочитать методичку к следующему уроку
// done


public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

//    // обьявляем логер
//    private static Logger logger = Logger.getLogger(ClientGUI.class.getName());

    private static final String logFile = "/Users/leonid/IdeaProjects/J2/J2_L6/src/ru/geekbrains/J2/L4/hometask/client_app.log";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"};
        userList.setListData(users);
        scrollUser.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);

        // добавил лисенер на кнопку сенд
        btnSend.addActionListener(this);

        // добавил кей лисенер на клавишу ентер
        tfMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    // записываем событие в лог
//                    logger.info("click Enter! send: " + tfMessage.getText());
                    myLogger("click Enter! send: " + tfMessage.getText());

                    System.out.println("click Enter! send: " + tfMessage.getText());
                }
            }
        });

        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    // метод записывающий лог
    public void myLogger(String s){
        try(FileWriter writer = new FileWriter(logFile, true))
        {
            writer.write(s);
            writer.append('\n');

            writer.flush();
        }
        catch(IOException ex){
           System.out.println("Can't open log file and write!");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            // записываем событие в лог
//            logger.info("click always on top!");
            myLogger("click always on top!");

            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if( src == btnSend) {
//            // добавляем обработку кнопки сенд, для теста добавил вывод сообщения в консоль
//            System.out.println("click btnSend!");

            // записываем событие в лог
//            logger.info("click btnSend! send: " + tfMessage.getText());
            myLogger("click btnSend! send: " + tfMessage.getText());

            // добавляем отправку содержимого поля меседж в консоль
            System.out.println("click btnSend! send: " + tfMessage.getText());

        } else {
            // записываем событие в лог
//            logger.info("except, src : " + src);
            myLogger("Unknown source: " + src);

            throw new RuntimeException("Unknown source: " + src);
        }
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = String.format("Exception in thread \"%s\" %s: %s\n\t at %s",
                t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
