package data.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TrainingTest {
	
	private Training training;
	
	@Before
	public void before(){
		training= new Training();
	}


	@Test
	public void testAddUserInTraining(){
		User user = new User();
		assertEquals(0, training.getUsers().size());
		training.addUserInTraining(user);
		assertTrue(1==training.getUsers().size());
	}
	
	@Test
	public void testDeleteUser(){
		User user = new User();
		training.addUserInTraining(user);
		assertTrue(1==training.getUsers().size());
		training.deleteUser(user);
		assertTrue(0==training.getUsers().size());
	}
}
