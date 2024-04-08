package com.example.demo.dao;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<Users, Long> {
    // 사용자 정의 메서드를 여기에 추가할 수 있습니다. 예: 사용자 이름으로 검색
    Users findByUsername(String username);
}
