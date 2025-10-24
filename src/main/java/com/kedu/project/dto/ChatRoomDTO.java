package com.kedu.project.dto;

import java.sql.Timestamp;

public class ChatRoomDTO {
	
	    private String room_id;          // 채팅방 고유 ID (UUID)
	    private String type;            // 채팅방 타입 (SINGLE, GROUP 등)
	    private String roomMembers;     // 참여자 ID 리스트 (JSON 문자열 등)
	    private String lastMessage;     // 마지막 메시지 내용
	    private Timestamp lastUpdatedAt; // 마지막 메시지 생성 시간
	    
	    
	    
		public ChatRoomDTO(String room_id, String type, String roomMembers, String lastMessage,
				Timestamp lastUpdatedAt) {
			super();
			this.room_id = room_id;
			this.type = type;
			this.roomMembers = roomMembers;
			this.lastMessage = lastMessage;
			this.lastUpdatedAt = lastUpdatedAt;
		}
		public String getRoom_id() {
			return room_id;
		}
		public void setRoom_id(String room_id) {
			this.room_id = room_id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getRoomMembers() {
			return roomMembers;
		}
		public void setRoomMembers(String roomMembers) {
			this.roomMembers = roomMembers;
		}
		public String getLastMessage() {
			return lastMessage;
		}
		public void setLastMessage(String lastMessage) {
			this.lastMessage = lastMessage;
		}
		public Timestamp getLastUpdatedAt() {
			return lastUpdatedAt;
		}
		public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
			this.lastUpdatedAt = lastUpdatedAt;
		}
	    
	    
	    
	    
	}
    
    
