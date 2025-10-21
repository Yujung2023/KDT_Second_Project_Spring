package com.kedu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kedu.project.dao.MemberDAO;
import com.kedu.project.dto.MemberDTO;
import com.kedu.project.utils.Encrypt;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDao;

	public int checkId(String id) {
		System.out.println("id:"+id);
		int value = memberDao.checkId(id);
		System.out.println(value);
		return value;
	}

	public int register(MemberDTO memberDTO) throws RuntimeException {
        try {
        	memberDTO.setPassword(Encrypt.encrypt(memberDTO.getPassword()));
        	memberDTO.setOfficeEmail(memberDTO.getId()+"@infinity.com");
    		int result = memberDao.register(memberDTO); 
        	return result;
        } catch (DataIntegrityViolationException e) {
            // ORA-00001 같은 unique constraint 에러 잡기
        	System.out.println("register 에러");
            return 0;
        }
    }

}
