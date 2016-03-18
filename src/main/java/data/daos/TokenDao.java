package data.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer>, TokenExtended {

    Token findByUser(User user);
    
    @Query(value="select * from Token", nativeQuery=true)
    List<Token> findAllToken();
    
    Token findByValue(String value);
}
