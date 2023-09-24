package lab.jrs.core;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class Server {

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static final int PORT_DEFAULT = 8080;
    private static final String PATH_DEFAULT = "/";

    private int port;
    private String path;

    private HttpServer server = null;

    public Server(int port, String path, HttpHandler handler) throws IOException {
        this.port = port;
        this.path = path;
        this.server =  HttpServer.create(new InetSocketAddress(this.port), 50);
        this.server.createContext(path, handler);
    }

    public Server(HttpHandler handler) throws IOException{
        this(PORT_DEFAULT, PATH_DEFAULT, handler);
    }

    public void start(){
        if(this.server == null){
            throw new IllegalAccessError("Method not permited for running");
        }
        this.server.start();
        logger.info("Server is running at port "+this.server.getAddress().getPort());
    }
    public void stop(int delay){
        if(this.server == null){
            throw new IllegalAccessError("Method not permited for running");
        }

        this.server.stop(delay);
        logger.info("Server stop ... ");
    }
}
