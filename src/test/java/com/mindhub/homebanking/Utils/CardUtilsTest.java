package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.hamcrest.text.CharSequenceLength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class CardUtilsTest {


    //este test verifica que el método hasCardWithColor funciona correctamente
    // al retornar false  o tree cuando el cliente no tiene una tarjeta del color
    // especificado (SILVER en este caso).

    @Test
    void hasCardWithColorShouldReturnFalse() {
        // Crear un cliente
        Client client = new Client();

        // Crear una tarjeta con un color diferente al que queremos verificar
        Card card = new Card(CardColor.SILVER);

        // Agregar la tarjeta al cliente
        client.addCards(card);

        // Verificar si tiene una tarjeta del mismo color (en este caso, azul)
        assertFalse(CardUtils.hasCardWithColor(client, CardColor.SILVER));
    }




    // este testing verifica si la función generateCardNumber() genera un
    // número de tarjeta con una longitud específica de 19 caracteres.
    // Si la función cumple con esto, el test pasará; de lo contrario, fallará y
    // te indicará que el número de tarjeta generado no tiene la longitud esperada.
    @Test
    void generateCardNumber() {
        String cardNumber = CardUtils.generateCardNumber();
        assertThat(cardNumber, CharSequenceLength.hasLength(19));
    }


   //assertTrue(cvv >= 100 && cvv <= 999);: Esta es una aserción.
   // Verifica que la condición entre paréntesis sea verdadera.
    // En este caso, comprueba si el cvv es mayor o igual a 100 y
    // menor o igual a 999. Si esta condición es verdadera,
    // la prueba pasa. Si es falsa, la prueba falla.
    @Test
    void generateCvvShouldBeBetween100And999() {
        int cvv = CardUtils.generateCvv();
        assertTrue(cvv >= 100 && cvv <= 999);
    }
    // Test ClientController
    //Si el correo electrónico contiene "@", la prueba pasará sin problemas.
    // Si no contiene "@", la prueba fallará y mostrará un mensaje indicando que
    // la condición no se cumplió.
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void containsChart(){
        String email = clientRepository.findById(1L).get().getEmail();
        assertThat(email, containsString("@"));
    }


    //Si todos los clientes tienen un correo electrónico no vacío, la prueba pasará sin problemas.
    // Si al menos un cliente tiene un correo electrónico vacío, la prueba fallará y mostrará un mensaje
    // indicando que la condición no se cumplió.
    @Test
    public void hasEmail(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, everyItem(hasProperty("email", not(emptyString()))));
    }
    // Test de CardController
    //prueba unitaria para verificar que todos los objetos Card en el repositorio tienen un valor
    // no nulo  en el campo cvv.
    @Autowired
    CardRepository cardRepository;
    @Test
    public void cardCvvIsCreated(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,everyItem(hasProperty("cvv",is(not(nullValue()))))); //hasProperty verifica si un objeto tiene una propiedad con un nombre dado.
    }

    //el test cardNumberIsDifferent verifica que cada tarjeta en el repositorio tiene un número
    // de tarjeta que no es nulo y es de tipo Integer.
    @Test
    public void cardNumberIsDifferent(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,everyItem(hasProperty("number",notNullValue(Integer.class)))); //hasProperty verifica si un objeto tiene una propiedad con un nombre dado.
        //notNullValue(Integer.class) verifica que el valor de la propiedad no sea nulo y que sea de tipo Integer.
    }

    //TransactionController
    @Autowired
    TransactionRepository transactionRepository;

    //el test accountNotEmpty verifica que ninguna transacción en
    // el repositorio tiene un campo de cuenta (account) que sea nulo o una cadena de texto vacía.
    @Test
    public void accountNotEmpty() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, everyItem(hasProperty("account", not(emptyOrNullString()))));
        /* assertThat es un método de aserción proporcionado por la biblioteca Hamcrest que permite realizar afirmaciones más expresivas y legibles en los tests.

          everyItem es un matcher que verifica que cada elemento de una colección cumple con un cierto criterio.

          hasProperty verifica si un objeto tiene una propiedad con un nombre dado.

          not(emptyOrNullString()) verifica que el valor de la propiedad no sea nulo ni una cadena de texto vacía.*/
    }
    // Test Account
    @Autowired
    AccountRepository accountRepository;
    // Test Account
    //este test está verificando que la función findByCreationDateBefore del accountRepository puede
    // encontrar cuentas cuya fecha de creación sea anterior a la fecha proporcionada.
    @Test
    public void createdDateLessThanNow(){
        String date = "2023-09-11 11:51:04.2542";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        List<Account> accounts = accountRepository.findByCreationDateBefore(LocalDateTime.parse(date, formatter));
        assertThat(accounts, is(not(empty())));
    }
    /* assertThat(accounts, is(not(empty())));
    Esta es la afirmación que verifica si la lista de cuentas (accounts) no está vacía. Si la lista no está vacía,
    significa que se encontraron cuentas que cumplen con el criterio de búsqueda.*/


}

