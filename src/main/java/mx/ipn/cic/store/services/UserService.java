package mx.ipn.cic.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ipn.cic.store.entities.UserEntity;
import mx.ipn.cic.store.repositories.IUserRepository;

@Service
public class UserService {
	
	@Autowired
	private IUserRepository userRepository;

	public List<UserEntity> all() {
		return this.userRepository.findAll();
	}
	
	public UserEntity save(UserEntity user)
	{
		return this.userRepository.save(user);
	}

	public boolean delete(Integer id)
	{
		this.userRepository.deleteById(id);
		return true;
	}

	public UserEntity findById(Integer id)
	{
		Optional<UserEntity> user = this.userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public List<UserEntity> findByName(String name) {
		// TODO Auto-generated method stub
		return this.userRepository.findByNameContaining(name);
	}

	
}
