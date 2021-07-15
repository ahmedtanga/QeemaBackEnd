package com.qeema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qeema.app.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

	@Query("select s from UsersEntity s where s.email=:email")
	public UsersEntity getUserByEmail(@Param("email") String email);

	@Query("select s from UsersEntity s where s.email=:email AND s.password=:password")
	public UsersEntity getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query("select count(s) from UsersEntity s where s.isLogged=true")
	public Long getCountOfUser();
}
