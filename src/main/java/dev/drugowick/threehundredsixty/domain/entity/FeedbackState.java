package dev.drugowick.threehundredsixty.domain.entity;

public enum FeedbackState {
    NOT_PROCESSED("Não processada"),
    NOT_STARTED("Não iniciada"),
    STARTED("Iniciada"),
    FINISHED("Finalizada");

    public String state;

    FeedbackState(String state) {
        this.state = state;
    }
}
