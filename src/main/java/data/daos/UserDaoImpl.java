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
		Token token = tokenDao.findByValue(tokenValue);
		if (!token.isValid()) {
			return null;
		}
		return user;
	}

}
