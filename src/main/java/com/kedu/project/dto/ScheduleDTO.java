package com.kedu.project.dto;

import java.sql.Timestamp;

public class ScheduleDTO {

   private int seq;
   private String category;
   private String title;
   private String content;
   private Timestamp startAt;
   private Timestamp endAt;
   private String place;
   private String color;
   private String importantYn;
   private String created_id;
   private String updated_id;
   private Timestamp createdAt;
   private Timestamp updatedAt;
   
   public ScheduleDTO() {}

   public ScheduleDTO(int seq, String category, String title, String content, Timestamp startAt, Timestamp endAt,
		String place, String color, String importantYn, String created_id, String updated_id, Timestamp createdAt,
		Timestamp updatedAt) {
	super();
	this.seq = seq;
	this.category = category;
	this.title = title;
	this.content = content;
	this.startAt = startAt;
	this.endAt = endAt;
	this.place = place;
	this.color = color;
	this.importantYn = importantYn;
	this.created_id = created_id;
	this.updated_id = updated_id;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
   }

   public int getSeq() {
	return seq;
   }

   public void setSeq(int seq) {
	this.seq = seq;
   }

   public String getCategory() {
	return category;
   }

   public void setCategory(String category) {
	this.category = category;
   }

   public String getTitle() {
	return title;
   }

   public void setTitle(String title) {
	this.title = title;
   }

   public String getContent() {
	return content;
   }

   public void setContent(String content) {
	this.content = content;
   }

   public Timestamp getStartAt() {
	return startAt;
   }

   public void setStartAt(Timestamp startAt) {
	this.startAt = startAt;
   }

   public Timestamp getEndAt() {
	return endAt;
   }

   public void setEndAt(Timestamp endAt) {
	this.endAt = endAt;
   }

   public String getPlace() {
	return place;
   }

   public void setPlace(String place) {
	this.place = place;
   }

   public String getColor() {
	return color;
   }

   public void setColor(String color) {
	this.color = color;
   }

   public String getImportantYn() {
	return importantYn;
   }

   public void setImportantYn(String importantYn) {
	this.importantYn = importantYn;
   }

   public String getCreated_id() {
	return created_id;
   }

   public void setCreated_id(String created_id) {
	this.created_id = created_id;
   }

   public String getUpdated_id() {
	return updated_id;
   }

   public void setUpdated_id(String updated_id) {
	this.updated_id = updated_id;
   }

   public Timestamp getCreatedAt() {
	return createdAt;
   }

   public void setCreatedAt(Timestamp createdAt) {
	this.createdAt = createdAt;
   }

   public Timestamp getUpdatedAt() {
	return updatedAt;
   }

   public void setUpdatedAt(Timestamp updatedAt) {
	this.updatedAt = updatedAt;
   }
}
