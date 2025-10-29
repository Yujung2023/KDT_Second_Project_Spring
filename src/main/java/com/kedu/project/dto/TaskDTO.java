package com.kedu.project.dto;

import java.sql.Timestamp;
import java.util.List;

public class TaskDTO {
	
		private int seq;
	    private String group_name;
	    private String description;
	    private String manager_id;
	    private List<String> members; // 여러 명 추가
	    private Timestamp created_at;
	    private Timestamp updated_at;
	    
		public TaskDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public TaskDTO(int seq, String group_name, String description, String manager_id, List<String> members,
				Timestamp created_at, Timestamp updated_at) {
			super();
			this.seq = seq;
			this.group_name = group_name;
			this.description = description;
			this.manager_id = manager_id;
			this.members = members;
			this.created_at = created_at;
			this.updated_at = updated_at;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public String getGroup_name() {
			return group_name;
		}
		public void setGroup_name(String group_name) {
			this.group_name = group_name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getManager_id() {
			return manager_id;
		}
		public void setManager_id(String manager_id) {
			this.manager_id = manager_id;
		}
		public List<String> getMembers() {
			return members;
		}
		public void setMembers(List<String> members) {
			this.members = members;
		}
		public Timestamp getCreated_at() {
			return created_at;
		}
		public void setCreated_at(Timestamp created_at) {
			this.created_at = created_at;
		}
		public Timestamp getUpdated_at() {
			return updated_at;
		}
		public void setUpdated_at(Timestamp updated_at) {
			this.updated_at = updated_at;
		}
	    
	    
	
	    
}
