package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    public CardService cardService;

    @Autowired
    public ClientService clientService;
    @Autowired
    public CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor) {
       /* try { } catch { } permite capturar y manejar excepciones que puedan ocurrir
       durante la ejecución de un bloque de código, evitando que el programa se detenga abruptamente y permitiendo tomar acciones específicas en caso de que ocurra una excepción.*/
        try {
            // Obtener el email del cliente autenticado
            String email = authentication.getName();
            // Buscar el cliente en la base de datos
            Optional<Client> clientOptional = cardService.toSearch(email);

            if (clientOptional.isPresent()) {
                // Obtener el cliente
                Client client = clientOptional.get();

                // Verificar si el cliente ya tiene una tarjeta del mismo tipo y color
                if (client.getCards().stream().filter(card -> card.getType() == cardType && card.getColor() == cardColor).count() == 1) {
                    // Si ya tiene una tarjeta del mismo tipo y color, retornar un error 403 (Forbidden)
                    return new ResponseEntity<>("You already have a " + cardColor + " " + cardType + " card", HttpStatus.FORBIDDEN);
                }

                // Verificar si el cliente ya tiene dos tarjetas de cualquier tipo y color
                if (client.getCards().stream().filter(card -> card.getColor() == cardColor).count() == 2) {
                    // Si ya tiene dos tarjetas de cualquier tipo y color, retornar un error 403 (Forbidden)
                    return new ResponseEntity<>("You already have two " + cardColor + " cards", HttpStatus.FORBIDDEN);
                }

                // Verificar si el cliente ya tiene tres tarjetas del mismo tipo
                long sameTypeCardsCount = client.getCards().stream().filter(card -> card.getType() == cardType).count();
                if (sameTypeCardsCount >= 3) {
                    // Si ya tiene tres tarjetas del mismo tipo, retornar un error 403 (Forbidden)
                    return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
                }

                // Verificar si se han creado dos tarjetas de débito o crédito del mismo color
                long debitCardsCount = client.getCards().stream().filter(c -> c.getType() == CardType.DEBIT && c.getColor() == cardColor).count();
                long creditCardsCount = client.getCards().stream().filter(c -> c.getType() == CardType.CREDIT && c.getColor() == cardColor).count();

                if (cardType == CardType.DEBIT && debitCardsCount >= 2) {
                    // Si ya se han creado dos tarjetas de débito del mismo color, retornar un mensaje exitoso
                    return new ResponseEntity<>("You already have two " + cardColor + " debit cards", HttpStatus.OK);
                } else if (cardType == CardType.CREDIT && creditCardsCount >= 2) {
                    // Si ya se han creado dos tarjetas de crédito del mismo color, retornar un mensaje exitoso
                    return new ResponseEntity<>("You already have two " + cardColor + " credit cards", HttpStatus.OK);
                }

                // Generar los datos de la nueva tarjeta
                String cardNumber = CardUtils.generateCardNumber();
                String cardHolder = client.getFirstName() + " " + client.getLastName();
                int cvv = CardUtils.generateCvv();
                LocalDate fromDate = LocalDate.now();
                LocalDateTime thruDate = LocalDateTime.now().plusYears(5);


                // Crear la nueva tarjeta y guardarla en la base de datos
                Card card = new Card(cardType,cardNumber,cvv,cardHolder,fromDate,thruDate,cardColor);
                card.setClient(client);


                cardService.save(card);
                System.out.println("Nuevo objeto Card: " + card);

                // Retornar una respuesta exitosa
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                // Si no se encuentra el cliente, retornar un error 500 (Internal Server Error)
                return new ResponseEntity<>("Client not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            // Si ocurre un error inesperado, imprimir el stack trace y retornar un error 500 (Internal Server Error)
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/clients/current/cards")
    public ResponseEntity<?> getCards(Authentication authentication) {
        // Buscar el cliente en la base de datos
        Client current = cardService.getCards(authentication.getName());

        if (current == null) {
            // Si no se encuentra el cliente, retornar un error 404 (Not Found)
            return ResponseEntity.notFound().build();
        }

        Set<Card> cards = current.getCards();
        if (cards == null || cards.isEmpty()) {
            // Si el cliente no tiene tarjetas, retornar una respuesta vacía
            return ResponseEntity.noContent().build();
        }

        // Convertir las tarjetas a DTOs y retornarlas en una lista
        // Convertir las tarjetas a DTOs y asignar el valor de expired
        List<CardDTO> cardDTOs = cards.stream().map(CardDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(cardDTOs);
    }
    @PatchMapping("clients/current/cards/delete")
    public ResponseEntity<Object> deleteCards(
            @RequestParam long id,
            Authentication authentication
    ){
        // Obtiene el cliente actual de la base de datos.
        Client current = clientService.getClientByEmail(authentication.getName());



        // Comprueba si el id de la tarjeta es inválido.
        if (id == 0 ){
            // Devuelve un mensaje de error.
            return new ResponseEntity<>("Esta tarjeta no existe",HttpStatus.FORBIDDEN);
        }
            // Obtiene la tarjeta de la base de datos.
            Card card = cardService.getCardById(id);
        if(card == null){
            // Devuelve un mensaje de error.
            return new ResponseEntity<>("Esta tarjeta no existe",HttpStatus.FORBIDDEN);

        }


        // Comprueba si el cliente actual tiene autorización para eliminar la tarjeta.
        if(current == null){
            // Devuelve un mensaje de error.
            return  new ResponseEntity<>("No tienes autorizacion", HttpStatus.FORBIDDEN);
        }

        // Comprueba si la tarjeta pertenece al cliente actual.
        if(!current.getCards().contains(card)){
            // Devuelve un mensaje de error.
            return new ResponseEntity<>("Esta tarjeta no te pertenece", HttpStatus.FORBIDDEN);
        }

        // Elimina la tarjeta de la base de datos.
        cardService.deleteCard(card);

        // Devuelve una respuesta exitosa.
        return  new ResponseEntity<>("Tarjeta borrada con exito", HttpStatus.ACCEPTED);
    }
}