package api;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.daos.CourtDao;
import data.daos.DaosService;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();

    private Calendar date1;

    private Calendar date2;

    private CourtState court;

    private User u1;

    private UserWrapper userWrapper;

    private TrainingWrapper trainingWrapper;

    @Autowired
    private DaosService daosService;

    @Autowired
    private CourtDao courtDao;

    @Before
    public void before() {
        date1 = Calendar.getInstance();
        date2 = Calendar.getInstance();
        date2.set(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH), date1.get(Calendar.DATE), date1.get(Calendar.HOUR_OF_DAY),
                date1.get(Calendar.MINUTE));
        court = new CourtState(courtDao.findOne(1));
        u1 = (User) daosService.getMap().get("u6");
        userWrapper = new UserWrapper(u1.getUsername(), u1.getEmail(), u1.getPassword(), u1.getBirthDate());
        trainingWrapper = new TrainingWrapper(date1, date2, court, userWrapper);
    }

    @Test
    public void testErrorForbiddenCreateTraining() {
        try {
            String token = restService.loginAdmin();
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.CREATE_TRAINING).basicAuth(token, "")
                    .body(trainingWrapper).get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.FORBIDDEN, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }

    @Test
    public void testErrorConflicCreateTraining() {
        try {
            String token = restService.registerAndLoginPlayer();
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.CREATE_TRAINING).basicAuth(token, "")
                    .body(trainingWrapper).get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.CONFLICT, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }

    @Test
    public void testErrorForbiddenDeleteTraining() {
        try {
            String token = restService.loginAdmin();
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.DELETE_TRAINING).basicAuth(token, "").param("idT", "1")
                    .get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.FORBIDDEN, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }

    @Test
    public void testErrorForbiddenDeleteTrainingPlayer() {
        try {
            String token = restService.loginAdmin();
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.DELETE_TRAINING_PLAYER).basicAuth(token, "")
                    .param("idT", "1").param("idU", "1").get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.FORBIDDEN, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }

    @Test
    public void testErrorUnauthorizedShowTrainings() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.SHOW_TRAININGS).basicAuth("", "").get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }

    @Test
    public void testErrorUnauthorizedRegisterTraining() {
        try {
            String token = restService.loginTrainer();
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAINING).path(Uris.REGISTER_TRAINING).basicAuth(token, "").param("idT", "1")
                    .param("idU", "3").get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            System.out.println("ERROR>>>>> " + httpError.getMessage());
        }
    }
}
