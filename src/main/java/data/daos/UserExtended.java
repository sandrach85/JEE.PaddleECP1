package data.daos;

import data.entities.User;

public interface UserExtended {

    public User findByTokenValueNoExpired(String tokenValue);

}
