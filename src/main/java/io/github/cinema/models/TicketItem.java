package io.github.cinema.models;

public class TicketItem {
    private final String seat;
    private final String film;
    private final double price;

    public TicketItem(String seat, String film, double price) {
        this.seat = seat;
        this.film = film;
        this.price = price;
    }

    public String getSeat() { return seat; }
    public String getFilm() { return film; }
    public double getPrice() { return price; }
}