package com.kedu.project.dto;

import java.util.List;

public class LeaveRequestPayload {

    private List<Item> items;
    private List<Approver> approvers;
    private List<Approver> references;

    // ===== getter / setter =====
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

    public List<Approver> getReferences() {
        return references;
    }

    public void setReferences(List<Approver> references) {
        this.references = references;
    }


    // ===== 내부 클래스 =====
    public static class Item {
        private String date;
        private String type;
        private String reason;

        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }

        public String getReason() {
            return reason;
        }
        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class Approver {
        private String id;
        private String name;
        private String rank_code;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getRank_code() {
            return rank_code;
        }
        public void setRank_code(String rank_code) {
            this.rank_code = rank_code;
        }
    }
}
