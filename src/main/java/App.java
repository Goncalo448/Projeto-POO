
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.*;

import static java.lang.System.out;

/**
 * A App é responsável pela execução do programa.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class App {
    static Scanner scan = new Scanner(System.in);
    //static Comunidade comunidade = new Comunidade("Jackson");
    static Controller controller = new Controller();

    /**
     * Método main que corre o programa.
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        View view = new View(controller, scan);
        view.run();

    }


}