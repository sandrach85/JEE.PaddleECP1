package data.daos;

import java.util.Calendar;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public interface TrainingExtended {
	
	public boolean deleteUserTraining(int idT, int idU);
	public void deleteTrainingAndReserves(int id);
	public Training createTraining(Calendar dateIni, Calendar dateEnd, Court court, User trainer, int id);
	public User findUserInTraining(int idT, int idU);
	

}
