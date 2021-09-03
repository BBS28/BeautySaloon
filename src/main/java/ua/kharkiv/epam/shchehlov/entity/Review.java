package ua.kharkiv.epam.shchehlov.entity;

public class Review extends Entity {
    private String text;
    private int rate;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
