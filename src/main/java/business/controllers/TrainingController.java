package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Training;
import data.entities.User;

@Controller
public class TrainingController {

	private TrainingDao trainingDao;

	private CourtDao courtDao;

	private UserDao userDao;

	@Autowired
	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}

	@Autowired
	public void setCourtDao(CourtDao courtDao) {
		this.courtDao = courtDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean createTraining(TrainingWrapper trainingWrapper) {
		Training training = new Training();
		training.setDateIni(trainingWrapper.getDateIni());
		training.setDateEnd(trainingWrapper.getDateEnd());
		training.setCourt(courtDao.findOne(trainingWrapper.getCourt().getCourtId()));
		training.setTrainer(trainingDao.findIdTrainer(userDao.findByUsernameOrEmail(
				trainingWrapper.getTrainer().getUsername()).getId()));
		trainingDao.save(training);
		return true;
	}

	public void deleteTraining(int id) {
		Training training = trainingDao.findById(id);
		if (training != null) {
			trainingDao.deleteTrainingAndReserves(training.getId());
		}
	}

	public void deleteTrainingPlayer(int idT, int idP) {
		User player = trainingDao.findUserInTraining(idT, idP);
		if (player != null) {
			System.out.println("mi usuario es:"+player.toString());
			trainingDao.deleteUserTraining(idT, idP);
		}
		System.out.println("salgo despues de borrar");
	}

	public List<TrainingWrapper> showTraining() {
		List<TrainingWrapper> trainings = new ArrayList<>();
		for (Training training : trainingDao.findAll()) {
			trainings.add(new TrainingWrapper(training));
		}
		return trainings;
	}

	public boolean registerTraining(int idT, int idP) {
		Training training = trainingDao.findById(idT);
		User player = userDao.findById(idP);
		if (training != null) {
			if (player != null) {
				training.addUserInTraining(player);
				trainingDao.save(training);
				return true;
			}
		}
		return false;
	}

	public boolean validateFieldQuotaAvailable(int id) {
		Training training = trainingDao.findById(id);
		if (training.getUsers().size() < training.MAX_USERS) {
			return true;
		}
		return false;
	}

	public boolean validateFieldTrainingPlayerId(int id) {
		User player = userDao.findById(id);
		if (player == null) {
			return false;
		}
		return true;
	}

	public boolean validateFieldTrainingId(int id) {
		Training training = trainingDao.findById(id);
		if (training == null) {
			return false;
		}
		return true;
	}

	public boolean validateTrainingDate(Calendar date) {
		Calendar timeNow = Calendar.getInstance();
		if (date.before(timeNow)) {
			return false;
		}
		return true;
	}

	public boolean validateIntervalTrainingDates(Calendar date1, Calendar date2) {
		if (date2.before(date1)) {
			return false;
		}
		return true;
	}

	public boolean validateFieldCourtExist(int id) {
		if (courtDao.findOne(id) == null || !courtDao.findOne(id).isActive()) {
			return false;
		}
		return true;
	}

}
