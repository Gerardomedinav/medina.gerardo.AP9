package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.Optional;

public interface CardService {

   public Optional<Client> toSearch(String email);
    void save(Card card);
   public Client getCards(String authentication);

    public Card getCardById(long id);

    public void deleteCard(Card card);
}
