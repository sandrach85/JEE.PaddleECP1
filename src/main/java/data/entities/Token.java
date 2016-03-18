package data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String value;

    @Column(nullable = false)
    private Calendar createdDate;

    @ManyToOne
    @JoinColumn
    private User user;

    public Token() {
    }

    public Token(User user) {
        assert user != null;
        this.user = user;
        this.value = new Encrypt()
                .encryptInBase64UrlSafe("" + user.getId() + user.getUsername() + Long.toString(new Date().getTime()) + user.getPassword());
        this.createdDate = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar date) {
        this.createdDate = date;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Token) obj).id;
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(createdDate.getTime());
        return "Token [id=" + id + ", value=" + value + ", createdDate=" + date + ", user=" + user + "]";
    }

    public boolean isValid() {
        if ((Calendar.getInstance().getTimeInMillis() - createdDate.getTimeInMillis()) < 3600000)
            return true;
        else
            return false;
    }
}
