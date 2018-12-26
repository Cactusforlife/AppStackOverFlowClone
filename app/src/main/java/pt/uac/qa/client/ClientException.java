package pt.uac.qa.client;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 10-12-2018.
 */
public class ClientException extends Exception {
    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
