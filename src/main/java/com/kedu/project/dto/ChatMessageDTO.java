package com.kedu.project.dto;

import java.sql.Timestamp;



public class ChatMessageDTO {

	
		private String room_id;	 // 채팅방 ID pk
		private String sender;   // 보낸 사람
	    private String content;  // 메시지 내용
	    private Timestamp sendtime; // 보낸날짜시간
	    private String fileUrl;  // 파일 이미지경로
	    private MessageType type;  //메세지타입
	    private int read;       // 읽음 여부
	
	    
	    // enum을 내부에 정의
	    public enum MessageType {  
	    	// ENTER → 사용자가 채팅방에 입장했음을 알리는 메시지
	    	// TALK → 일반 대화 메시지
	    	// LEAVE → 사용자가 채팅방에서 나갔음을 알리는 메시지
	    	// SYSTEM → 서버나 관리자 공지 같은 시스템 메시지
	    	// FILE → 이미지, 문서 등 파일 전송 메시지
	        ENTER, TALK, LEAVE, SYSTEM, FILE
	    }

	    
	    
	    
		public ChatMessageDTO(String room_id, String sender, String content, Timestamp sendtime, String fileUrl,
				MessageType type, int read) {
			super();
			this.room_id = room_id;
			this.sender = sender;
			this.content = content;
			this.sendtime = sendtime;
			this.fileUrl = fileUrl;
			this.type = type;
			this.read = read;
		}


		public String getRoom_id() {
			return room_id;
		}


		public void setRoom_id(String room_id) {
			this.room_id = room_id;
		}


		public String getSender() {
			return sender;
		}


		public void setSender(String sender) {
			this.sender = sender;
		}


		public String getContent() {
			return content;
		}


		public void setContent(String content) {
			this.content = content;
		}


		public Timestamp getSendtime() {
			return sendtime;
		}


		public void setSendtime(Timestamp sendtime) {
			this.sendtime = sendtime;
		}


		public String getFileUrl() {
			return fileUrl;
		}


		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}


		public MessageType getType() {
			return type;
		}


		public void setType(MessageType type) {
			this.type = type;
		}


		public int getRead() {
			return read;
		}


		public void setRead(int read) {
			this.read = read;
		}

	
	
}
