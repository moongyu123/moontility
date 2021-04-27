package com.moongyu123.moontility.work.repository;

import com.moongyu123.moontility.work.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	public UserInfo findUserInfoById(int id);

	@Query(value = "select max(u.id) from user_info u", nativeQuery = true)
	public Integer findMaxId();





}
