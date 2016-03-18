package business.api.exceptions;

public class InvalidIntervalTrainingDateFieldException extends ApiException {
    

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Intervalo de fecha no válido";

    public static final int CODE = 401;

    public InvalidIntervalTrainingDateFieldException() {
        this("");
    }

    public InvalidIntervalTrainingDateFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
