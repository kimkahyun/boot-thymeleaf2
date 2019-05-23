package iducs.springboot.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iducs.springboot.board.domain.User;

public interface UserRepository 
	extends JpaRepository<User, Long> {
	List<User> findByName(String name);
	List<User> findByNameOrderByIdAsc(String name);
	List<User> findByCompany(String company);
	List<User> findByCompanyOrderByIdAsc(String company);
}
