package data.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Training {

    @Id
    @GeneratedValue
    private int id;

    private Calendar dateIni;

    private Calendar dateEnd;

    @ManyToOne
    @JoinColumn
    private Court court;

    @OneToOne
    @JoinColumn
    private User trainer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;

    public final int MAX_USERS = 4;

    public Training() {
        this.users = new ArrayList<User>();
    }

    public Training(Calendar dateIni, Calendar dateEnd, Court court, User trainer) {
        super();
        this.dateIni = dateIni;
        this.dateEnd = dateEnd;
        this.court = court;
        this.trainer = trainer;
        this.users = new ArrayList<User>();
    }

    public Calendar getDateIni() {
        return dateIni;
    }

    public void setDateIni(Calendar dateIni) {
        this.dateIni = dateIni;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Training other = (Training) obj;
        if (court == null) {
            if (other.court != null)
                return false;
        } else if (!court.equals(other.court))
            return false;
        if (dateEnd == null) {
            if (other.dateEnd != null)
                return false;
        } else if (!dateEnd.equals(other.dateEnd))
            return false;
        if (dateIni == null) {
            if (other.dateIni != null)
                return false;
        } else if (!dateIni.equals(other.dateIni))
            return false;
        if (id != other.id)
            return false;
        if (trainer == null) {
            if (other.trainer != null)
                return false;
        } else if (!trainer.equals(other.trainer))
            return false;
        if (users == null) {
            if (other.users != null)
                return false;
        } else if (!users.equals(other.users))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String dateI = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(dateIni.getTime());
        String dateE = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(dateEnd.getTime());
        return "Training [id=" + id + ", dateIni=" + dateI + ", dateEnd=" + dateE + ", court=" + court + ", trainer=" + trainer + ", users="
                + users + "]";
    }

    public void addUserInTraining(User user) {
        if (this.users.size() < MAX_USERS) {
            this.users.add(user);
        }
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

}
