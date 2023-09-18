package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {

    // Atributos de la clase Card
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDateTime thruDate;


    // Asociación ManyToOne con la clase Client mediante el atributo client_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    // Constructor vacío

    public Card() {
    }


    // Constructor con parámetros


    public Card(CardColor silver) {
    }

    public Card(Long id, String cardHolder, CardType type, CardColor color, String number, int cvv, LocalDate fromDate, LocalDateTime thruDate) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;


    }
    public Card(CardType cardType, String cardNumber, int cvv, String cardHolder, LocalDate fromDate, LocalDateTime thruDate, CardColor cardColor) {
        this.type = cardType;
        this.number = cardNumber;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.color = cardColor;
    }





    // Getters y setters para cada atributo de la clase
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}