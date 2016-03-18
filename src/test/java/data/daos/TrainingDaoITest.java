package data.daos;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindById() {
        Training training = (Training) daosService.getMap().get("u62");
        assertTrue(training.getId() == trainingDao.findById(training.getId()).getId());
    }

    @Test
    public void testFindIdTrainer() {
        Training training = (Training) daosService.getMap().get("u41");
        assertTrue(training.getTrainer().getId() == 6);
    }
    
    @Test
    public void testFindUserInTraining(){
        Training training = (Training) daosService.getMap().get("u62");
        assertTrue(trainingDao.findUserInTraining(training.getId(), 12)==null);
        User user = (User) daosService.getMap().get("u10");
        training.addUserInTraining(user);
        trainingDao.save(training);
        assertTrue(trainingDao.findUserInTraining(training.getId(), 12)!=null);
    }

    @Test
    public void testCreateTraining() {
        Training training = (Training) daosService.getMap().get("u41");
        Training trainingC = trainingDao.createTraining(training.getDateIni(), training.getDateEnd(), training.getCourt(),
                training.getTrainer(), 12);
        assertEquals(4, trainingC.getId());
    }

    @Test
    public void testDeleteTrainingAndReserves() {
        Training training = (Training) daosService.getMap().get("u41");
        int id = training.getId();
        trainingDao.deleteTrainingAndReserves(training.getId());
        assertNull(trainingDao.findById(id));
    }

    @Test
    public void testDeleteUserTraining() {
        Training training = (Training) daosService.getMap().get("u41");
        List<User> usersBef = trainingDao.findUsersTraining(training.getId());
        trainingDao.deleteUserTraining(training.getId(), 3);
        List<User> usersAft = trainingDao.findUsersTraining(training.getId());
        assertTrue(usersBef != usersAft);
    }

}
