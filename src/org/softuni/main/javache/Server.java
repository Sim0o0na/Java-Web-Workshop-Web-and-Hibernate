package javache;

import javache.http.HttpSession;
import javache.http.HttpSessionImpl;

import java.io.*;
import java.net.*;
import java.util.concurrent.FutureTask;

public class Server {
    private static final String LISTENING_MESSAGE = "Listening on port: ";

    private static final String TIMEOUT_DETECTION_MESSAGE = "Timeout detected!";

    private static final Integer SOCKET_TIMEOUT_MILLISECONDS = 5000;

    private int port;

    private int timeouts;

    private ServerSocket server;

    private Application application;

    public Server(int port, Application application) {
        this.port = port;
        this.timeouts = 0;
        this.application = application;
    }

    public void run() throws IOException {
        this.server = new ServerSocket(this.port);
        System.out.println(LISTENING_MESSAGE + this.port);

        this.server.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

        HttpSession session = new HttpSessionImpl();
        application.setSession(session);

        while(true) {
            try(Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler
                        = new ConnectionHandler(clientSocket, new RequestHandler(application));

                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch(SocketTimeoutException e) {
                System.out.println(TIMEOUT_DETECTION_MESSAGE);
                this.timeouts++;
            }
        }
    }
}