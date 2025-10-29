package com.kedu.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kedu.project.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}