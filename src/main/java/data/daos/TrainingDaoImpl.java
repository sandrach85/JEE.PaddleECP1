package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Court;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;

@Repository
public class TrainingDaoImpl implements TrainingExtended {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private ReserveDao reserveDao;

    @Override
    public Training createTraining(Calendar dateIni, Calendar dateEnd, Court court, User trainer, int id) {
        Training training = trainingDao.findById(id);
        if (training == null) {
            Training trainingC = new Training(dateIni, dateEnd, court, trainer);
            trainingDao.save(trainingC);
            createReserves(dateIni, dateEnd, court, trainingC.getId());
            return trainingC;
        } else
            return training;
    }

    private void createReserves(Calendar dateI, Calendar dateE, Court court, int id) {
        long dateMilis = dateI.getTimeInMillis();
        long weekAdd = 1000 * 60 * 60 * 24 * 7;
        Calendar dateAdd = dateI;

        for (int i = 0; i < daysBetweenDates(dateI, dateE); i++) {
            dateAdd.setTimeInMillis(dateMilis + (weekAdd * i));
            reserveDao.save(new Reserve(court, trainingDao.findIdTrainer(id), dateAdd));
        }
    }

    @Override
    public boolean deleteUserTraining(int idT, int idU) {
        User user = trainingDao.findUserInTraining(idT, idU);
        Training training = trainingDao.findById(idT);
        if (user != null) {
            training.deleteUser(user);
            trainingDao.save(training);
            return true;
        }
        return false;
    }

    @Override
    public void deleteTrainingAndReserves(int id) {
        if (trainingDao.findById(id) != null) {
            Training training = trainingDao.findById(id);
            deleteReserves(training.getDateIni(), training.getDateEnd(), training.getCourt());
            trainingDao.delete(training.getId());
        }
    }

    @Override
    public User findUserInTraining(int idT, int idU) {
        List<User> listUsers = trainingDao.findUsersTraining(idT);
        if (listUsers.size() > 0) {
            for (int i = 0; i < listUsers.size(); i++) {
                if (listUsers.get(i).getId() == idU) {
                    return listUsers.get(i);
                }
            }
        }
        return null;
    }

    private void deleteReserves(Calendar dateI, Calendar dateE, Court court) {
        Calendar dateDel = dateI;
        long dateMilis = dateI.getTimeInMillis();
        long weekDel = 1000 * 60 * 60 * 24 * 7;

        for (int i = 0; i < daysBetweenDates(dateI, dateE); i++) {
            if (reserveDao.findByCourtAndDate(court, dateDel) != null) {
                dateDel.setTimeInMillis(dateMilis + (weekDel * i));
                Reserve reserve = reserveDao.findByCourtAndDate(court, dateDel);
                reserveDao.delete(reserve);
            }
        }
    }

    private int daysBetweenDates(Calendar dateI, Calendar dateE) {
        long milisec = dateE.getTimeInMillis() - dateI.getTimeInMillis();
        int days = (int) (milisec / (1000 * 60 * 60 * 24));
        return days;
    }
}
