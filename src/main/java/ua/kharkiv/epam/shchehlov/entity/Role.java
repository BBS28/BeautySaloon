package ua.kharkiv.epam.shchehlov.entity;

public enum Role {
    CLIENT, ADMIN, MASTER;

    public String getName() {
        return name().toLowerCase();
    }
}
