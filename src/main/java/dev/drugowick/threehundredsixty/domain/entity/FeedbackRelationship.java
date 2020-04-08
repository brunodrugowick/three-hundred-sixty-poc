package dev.drugowick.threehundredsixty.domain.entity;

public enum FeedbackRelationship {
    SUBORDINATE("Subordinado"),
    PEER("Par"),
    SUPERIOR("Superior"),
    SELF("Auto-avaliação");

    public String relationship;

    FeedbackRelationship(String relationship) {
        this.relationship = relationship;
    }
}
