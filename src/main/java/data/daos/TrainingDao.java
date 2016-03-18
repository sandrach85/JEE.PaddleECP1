package data.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.Training;
import data.entities.User;

public interface TrainingDao  extends JpaRepository<Training, Integer>, TrainingExtended {

	@Query("select t.users from Training t where t.id= ?1")
	List<User> findUsersTraining(int id);
	
	Training findById(int id);
	
	@Query("select t.trainer from Training t where t.id= ?1")
	User findIdTrainer(int id);
	
	
}
