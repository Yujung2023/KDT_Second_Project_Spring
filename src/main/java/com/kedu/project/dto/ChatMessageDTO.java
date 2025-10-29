package com.kedu.project.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

    private String room_id;
    private String sender;
    private String content;
    private String type; //  추가
    private Timestamp sendtime;
    private String fileUrl;
    private int read;
	
    
    
}

	   
	    
	    
	    
		

