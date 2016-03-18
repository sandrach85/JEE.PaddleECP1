package data.entities;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    private User user;

    private Token token;

    @Before
    public void before() {
        this.user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        this.token = new Token(user);
    }

    @Test
    public void testTokenUser() {
        assertTrue(token.getValue().length() > 20);
    }

    @Test
    public void testTokenIsValid() {
        assertTrue(token.isValid());
        Calendar date = Calendar.getInstance();
        date.set(2016, Calendar.MARCH, 01);
        token.setCreatedDate(date);
        assertTrue(!token.isValid());
    }

}
