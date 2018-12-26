package pt.uac.qa.client;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 10-12-2018.
 */
public final class AuthorizationException extends ClientException {
    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
