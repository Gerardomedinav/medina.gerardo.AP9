package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Optional<Client> toSearch(String email) {
        return clientRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }
    @Override
    public Client getCards(String authentication) {
        return clientRepository.findByEmail(authentication);
    }

    @Override
    public Card getCardById(long id) {
        return cardRepository.findById(id).orElse(null);
    }
    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }
}
