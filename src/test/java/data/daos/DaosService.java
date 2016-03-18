package data.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.entities.Authorization;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Role;
import data.entities.Token;
import data.entities.Training;
import data.entities.User;
import data.services.DataService;

@Service
public class DaosService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private CourtDao courtDao;

    @Autowired
    private ReserveDao reserveDao;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DataService genericService;

    private Map<String, Object> map;

    @PostConstruct
    public void populate() {
        map = new HashMap<>();
        User[] users = this.createPlayers(0, 8);

        for (User user : users) {
            map.put(user.getUsername(), user);
        }
        for (Token token : this.createTokens(users)) {
            map.put("t" + token.getUser().getUsername(), token);
        }
        for (User user : this.createPlayers(8, 8)) {
            map.put(user.getUsername(), user);
        }
        this.createCourts(1, 4);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 4; i++) {
            date.add(Calendar.HOUR_OF_DAY, 1);
            reserveDao.save(new Reserve(courtDao.findOne(i + 1), users[i], date));
        }
        for (Training training : this.createTraining(0, 4)) {
            map.put(training.getTrainer().getUsername() + training.getId(), training);
        }
        AddUsersInTraining(2, 3, 4, 1);
    }

    public Training[] createTraining(int initial, int size) {
        Training[] training = new Training[size];
        List<Court> court = courtDao.findAll();
        List<User> user = userDao.findAll();
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        for (int i = 0; i < size; i++) {
            date2.set(date1.get(Calendar.YEAR), (date1.get(Calendar.MONTH) + 1), date1.get(Calendar.DATE), date1.get(Calendar.HOUR_OF_DAY),
                    date1.get(Calendar.MINUTE));
            training[i] = trainingDao.createTraining(date1, date2, court.get(i), user.get(i + 5), i);
            training[i].addUserInTraining(user.get(i));
            training[i].addUserInTraining(user.get(i + 1));
            training[i].addUserInTraining(user.get(i + 2));
            trainingDao.save(training[i]);
        }
        return training;
    }

    public Training AddUsersInTraining(int id1, int id2, int id3, int idT) {
        Training training = trainingDao.findById(idT);
        List<User> users = addUsers(id1, id2, id3);
        training.setUsers(users);
        trainingDao.save(training);
        return training;
    }

    private List<User> addUsers(int id1, int id2, int id3) {
        List<User> users = new ArrayList<User>();
        users.add(userDao.findById(id1));
        users.add(userDao.findById(id2));
        users.add(userDao.findById(id3));
        return users;
    }

    public User[] createPlayers(int initial, int size) {
        User[] users = new User[size];
        for (int i = 0; i < size / 2; i++) {
            users[i] = new User("u" + (i + initial), "u" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
            userDao.save(users[i]);
            authorizationDao.save(new Authorization(users[i], Role.PLAYER));
        }
        for (int i = size / 2; i < size; i++) {
            users[i] = new User("u" + (i + initial), "u" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
            userDao.save(users[i]);
            authorizationDao.save(new Authorization(users[i], Role.TRAINER));
        }
        return users;
    }

    public List<Token> createTokens(User[] users) {
        List<Token> tokenList = new ArrayList<>();
        Token token;
        for (User user : users) {
            token = new Token(user);
            tokenDao.save(token);
            tokenList.add(token);
        }
        return tokenList;
    }

    public void createCourts(int initial, int size) {
        for (int id = 0; id < size; id++) {
            courtDao.save(new Court(id + initial));
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void deleteAll() {
        genericService.deleteAllExceptAdmin();
    }
}
