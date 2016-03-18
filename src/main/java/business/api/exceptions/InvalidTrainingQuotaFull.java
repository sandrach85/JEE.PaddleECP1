package business.api.exceptions;

public class InvalidTrainingQuotaFull extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Grupo de alumnos completo para el entrenamiento seleccionado";

    public static final int CODE = 1;

    public InvalidTrainingQuotaFull() {
        this("");
    }

    public InvalidTrainingQuotaFull(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
