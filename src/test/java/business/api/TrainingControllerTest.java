package business.api;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.controllers.TrainingController;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.daos.DaosService;
import data.daos.TrainingDao;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingControllerTest {

    @Autowired
    private DaosService daosService;

    @Autowired
    private TrainingController trainingController;

    @Autowired
    private TrainingDao trainingDao;

    @Test
    public void testCreateTraining() {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.set(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH) + 2, date1.get(Calendar.DATE), date1.get(Calendar.HOUR_OF_DAY),
                date1.get(Calendar.MINUTE));
        CourtState court = new CourtState(1, true);
        User u1 = (User) daosService.getMap().get("u6");
        UserWrapper userWrapper = new UserWrapper(u1.getUsername(), u1.getEmail(), u1.getPassword(), u1.getBirthDate());
        TrainingWrapper trainingWrapper = new TrainingWrapper(date1, date2, court, userWrapper);

        assertNull(trainingDao.findById(4));
        trainingController.createTraining(trainingWrapper);
        assertTrue(trainingDao.findById(4) != null);
    }

    @Test
    public void testDeleteTraining() {
        Training training = (Training) daosService.getMap().get("u62");
        assertTrue(trainingDao.findById(training.getId()) != null);
        trainingController.deleteTraining(training.getId());
        assertNull(trainingDao.findById(training.getId()));
    }

    @Test
    public void testDeleteTrainingPlayer() {
        assertTrue(trainingDao.findUserInTraining(1, 2) != null);
        trainingController.deleteTrainingPlayer(1, 2);
        assertTrue(trainingDao.findUserInTraining(1, 2) == null);
    }

    @Test
    public void testRegisterTraining() {
        assertTrue(trainingDao.findUserInTraining(1, 5) == null);
        trainingController.registerTraining(1, 5);
        assertTrue(trainingDao.findUserInTraining(1, 5) != null);
    }

}
