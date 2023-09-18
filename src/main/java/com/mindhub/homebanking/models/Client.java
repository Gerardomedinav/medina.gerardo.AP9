package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Anotación que indica que esta clase es una entidad de la base de datos
public class Client {
    @Id // Anotación que indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Anotación que indica que el valor de este campo se genera automáticamente
    @GenericGenerator(name = "native", strategy = "native") // Anotación que indica el generador de valor para la clave primaria
    private long id; // Campo que representa la clave primaria de la entidad

    private String firstName; // Campo que representa el nombre del cliente
    private String lastName; // Campo que representa el apellido del cliente
    @Column(unique = true)//campo unico no se puede repetir en la base de datos
    private String email; // Campo que representa el correo electrónico del cliente
    private String password;// campo de la contraseña del cliente


    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //  indica que esta entidad tiene una relación "uno a muchos" con otra entidad
    private Set<Account> accounts = new HashSet<>(); // Campo que representa las cuentas asociadas a este cliente

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) //  indica que esta entidad tiene una relación "uno a muchos" con otra entidad
    private Set<ClientLoan> loans = new HashSet<>(); // Campo que representa los préstamos asociados a este cliente


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)//relacion de uno a muchos client - cards
    private Set<Card> cards = new HashSet<>();

    //constructores
    public Client () {  }

    public Client (String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password= password;



    }



    //metodos para acceder a los campos de la clase
    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Account> getAccounts()
    {
        return accounts;
    }



    public void addAccount(Account account)
    {
        account.setClient(this);  // Asigna este cliente a la cuenta especificada y agrega la cuenta al conjunto de cuentas del cliente
        accounts.add(account);
    }

    public Set<ClientLoan> getLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCards(Card card){
        card.setClient(this);
        cards.add(card);
    }
}

   /* La clase  representa a un cliente.
   Contiene información básica del cliente como su nombre, apellido y correo electrónico,
    así como también las cuentas y préstamos y tarjetas asociados a este cliente.
     */
