package lab.jrs;

import lab.jrs.core.HandlerRequestResolver;
import lab.jrs.core.Server;
import lab.jrs.service.PessoaService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Server servidor = new Server(8080, "/", new HandlerRequestResolver(new PessoaService()));
            servidor.start();
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
