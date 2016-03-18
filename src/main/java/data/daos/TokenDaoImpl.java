package data.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Token;


@Repository
public class TokenDaoImpl implements TokenExtended{

	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public void deleteAllTokenExpired(){
		List<Token> listToken=tokenDao.findAllToken();
		for (int i=0; i<listToken.size(); i++){
			if (!listToken.get(i).isValid()){
				tokenDao.delete(listToken.get(i));
			}
		}
	}
}
