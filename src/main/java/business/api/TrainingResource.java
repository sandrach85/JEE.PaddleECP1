package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.InvalidDateException;
import business.api.exceptions.InvalidIntervalTrainingDateFieldException;
import business.api.exceptions.InvalidTrainingQuotaFull;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.NotFoundTrainingIdException;
import business.api.exceptions.NotFoundUserIdException;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAINING)
public class TrainingResource {

    private TrainingController trainingController;

    @Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    @RequestMapping(value = Uris.CREATE_TRAINING, method = RequestMethod.POST)
    public void createTraining(@AuthenticationPrincipal User trainer, @RequestBody TrainingWrapper trainingWrapper)
            throws InvalidDateException, InvalidIntervalTrainingDateFieldException, NotFoundCourtIdException {
        this.validateFieldDate(trainingWrapper.getDateIni(), trainingWrapper.getDateEnd());
        this.validateFieldCourtExist(trainingWrapper.getCourt().getCourtId());
        this.trainingController.createTraining(trainingWrapper);
    }

    @RequestMapping(value = Uris.DELETE_TRAINING, method = RequestMethod.DELETE)
    public void deleteTraining(@AuthenticationPrincipal User trainer, @RequestParam(required = true) int id)
            throws NotFoundTrainingIdException {
        this.validateFieldTraininIdExist(id);
        this.trainingController.deleteTraining(id);
    }

    @RequestMapping(value = Uris.DELETE_TRAINING_PLAYER, method = RequestMethod.DELETE)
    public void deleteTrainingPlayer(@AuthenticationPrincipal User trainer, @RequestParam(required = true) int idT, int idP)
            throws NotFoundTrainingIdException, NotFoundUserIdException {
        this.validateFieldTraininIdExist(idT);
        this.validateFieldTrainingPlayerIdExist(idP);
        this.trainingController.deleteTrainingPlayer(idT, idP);
    }

    @RequestMapping(value = Uris.SHOW_TRAININGS, method = RequestMethod.GET)
    public List<TrainingWrapper> showTrainings() {
        return this.trainingController.showTraining();
    }

    @RequestMapping(value = Uris.REGISTER_TRAINING, method = RequestMethod.PUT)
    public void registerTraining(@AuthenticationPrincipal User player, @RequestParam(required = true) int idT, int idP)
            throws NotFoundTrainingIdException, NotFoundUserIdException, InvalidTrainingQuotaFull {
        this.validateFieldTraininIdExist(idT);
        this.validateFieldTrainingPlayerIdExist(idP);
        this.validateFieldQuotaAvailableTraining(idT);
        this.trainingController.registerTraining(idT, idP);

    }

    private void validateFieldDate(Calendar time1, Calendar time2) throws InvalidDateException, InvalidIntervalTrainingDateFieldException {
        if (!trainingController.validateTrainingDate(time1)) {
            throw new InvalidDateException("La fecha no puede ser un día pasado");
        }
        if (!trainingController.validateTrainingDate(time2)) {
            throw new InvalidDateException("La fecha no puede ser un día pasado");
        }
        if (!trainingController.validateIntervalTrainingDates(time1, time2)) {
            throw new InvalidIntervalTrainingDateFieldException("La fecha inicial no puede ser posterior a la final");
        }
    }

    private void validateFieldCourtExist(int id) throws NotFoundCourtIdException {
        if (!trainingController.validateFieldCourtExist(id)) {
            throw new NotFoundCourtIdException("Pista no encontrada o no disponible");
        }
    }

    private void validateFieldTraininIdExist(int id) throws NotFoundTrainingIdException {
        if (!trainingController.validateFieldTrainingId(id)) {
            throw new NotFoundTrainingIdException("Id de entrenamiento no encontrado");
        }
    }

    private void validateFieldTrainingPlayerIdExist(int id) throws NotFoundUserIdException {
        if (!trainingController.validateFieldTrainingPlayerId(id)) {
            throw new NotFoundUserIdException("Id de Jugador no encontrado");
        }
    }

    private void validateFieldQuotaAvailableTraining(int id) throws InvalidTrainingQuotaFull {
        if (!trainingController.validateFieldQuotaAvailable(id)) {
            throw new InvalidTrainingQuotaFull("Grupo completo de alumnos");
        }
    }
}
