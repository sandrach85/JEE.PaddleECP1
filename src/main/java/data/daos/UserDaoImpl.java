package data.daos;

import org.springframework.beans.factory.annotation.Autowired;

import data.entities.Token;
import data.entities.User;

public class UserDaoImpl implements UserExtended {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Override
    public User findByTokenValueNoExpired(String tokenValue) {
        User user = userDao.findByTokenValue(tokenValue);
        if (user != null) {
            Token token = tokenDao.findByUser(user);
            if (token != null) {
                if (token.isValid()) {
                    return user;
                }
            }
        }
        return null;
    }
}
