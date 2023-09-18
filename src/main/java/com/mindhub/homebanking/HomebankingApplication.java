package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication // Anotación que indica que esta clase es la clase principal de la aplicación Spring Boot
public class HomebankingApplication {

	public static void main(String[] args) // Punto de entrada de la aplicación Spring Boot
	{
		SpringApplication.run(HomebankingApplication.class, args); // Ejecuta la aplicación llamando al método "run" de la clase "SpringApplication"
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean // Anotación que indica que este método devuelve un objeto que debe ser registrado como un bean en el contenedor de Spring
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoansRepository clientLoansRepository, CardRepository cardRepository)
	{
		return (args) -> // Devuelve un objeto de tipo CommandLineRunner que ejecuta un conjunto de tareas al inicio de la aplicación
		{
			//instancia del primer client
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123")); // Crea un nuevo objeto "Client" con los parámetros especificados
			clientRepository.save(client); // Guarda el objeto "Client" en la base de datos

			//intanciar  las cuentas: account and account1
			Account account = new Account("VIN001", LocalDateTime.now(), 5000); // Crea un nuevo objeto "Account" con los parámetros especificados
			client.addAccount(account); // Agrega la cuenta al cliente
			accountRepository.save(account); // Guarda la cuenta en la base de datos

			Account account1 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500); // Crea un nuevo objeto "Account" con los parámetros especificados
			client.addAccount(account1); // Agrega la cuenta al cliente
			accountRepository.save(account1); // Guarda la cuenta en la base de datos

			//instanciar  transacciones en la account VIN001 de client
			Transaction transaction = new Transaction(TransactionType.CREDIT, 600, "Salary", LocalDateTime.now()); // Crea un nuevo objeto "Transaction" con los parámetros especificados
			account.addTransaction(transaction); // Agrega la transacción a la cuenta
			transactionRepository.save(transaction); // Guarda la transacción en la base de datos

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 300, "Sales", LocalDateTime.now()); // Crea un nuevo objeto "Transaction" con los parámetros especificados
			account.addTransaction(transaction1); // Agrega la transacción a la cuenta
			transactionRepository.save(transaction1); // Guarda la transacción en la base de datos

			Transaction transaction2 = new Transaction(TransactionType.DEBIT, 100, "Loan Payment", LocalDateTime.now()); // Crea un nuevo objeto "Transaction" con los parámetros especificados
			account.addTransaction(transaction2); // Agrega la transacción a la cuenta
			transactionRepository.save(transaction2); // Guarda la transacción en la base de datos

			//instanciar transacciones a la acocunt1
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 240, "Sales", LocalDateTime.now()); // Crea un nuevo objeto "Transaction" con los parámetros especificados
			account1.addTransaction(transaction3); // Agrega la transacción a la cuenta1
			transactionRepository.save(transaction3); // Guarda la transacción en la base de datos

			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 300, "Sales", LocalDateTime.now()); // Crea un nuevo objeto "Transaction" con los parámetros especificados
			account1.addTransaction(transaction4); // Agrega la transacción a la cuenta1
			transactionRepository.save(transaction4); // Guarda la transacción en la base de datos

			Loan loan = new Loan("Hipotecary Loan", 500000.0, List.of(12, 24, 36,48,60)); // Crea un nuevo objeto "Loan" con los par&aacute;metros especificados
			loanRepository.save(loan); // Guarda el objeto "Loan" en la base de datos

			Loan loan1 = new Loan("Personal Loan", 100000.0, List.of(24, 12, 24)); // Crea un nuevo objeto "Loan" con los parámetros especificados
			loanRepository.save(loan1); // Guarda el objeto "Loan" en la base de datos

			Loan loan2 = new Loan("Automotive Loan", 300000.0, List.of(6, 12, 24,36)); // Crea un nuevo objeto "Loan" con los parámetros especificados
			loanRepository.save(loan2); // Guarda el objeto "Loan" en la base de datos

			//PRUEBA de clientLoan
			ClientLoan clientLoan = new ClientLoan(400000.00, 60, client, loan); // Crea un nuevo objeto "ClientLoan" con los parámetros especificados
			clientLoansRepository.save(clientLoan); // Guarda el objeto "ClientLoan" en la base de datos

			ClientLoan clientLoan1 = new ClientLoan(50000.00, 12, client, loan1); // Crea un nuevo objeto "ClientLoan" con los parámetros especificados
			clientLoansRepository.save(clientLoan1); // Guarda el objeto "ClientLoan" en la base de datos

			//Agrego  clientes nuevos
			Client client1 = new Client("Gerardo", "Medina", "gerardomedinav@gmail.com",passwordEncoder.encode("123")); // Crea un nuevo objeto "Client" con los parámetros especificados
			clientRepository.save(client1); // Guarda el objeto "Client" en la base de datos

			Client client2 = new Client("Sandra", "Gomez", "sandrag@gmail.com", passwordEncoder.encode("123")); // Crea un nuevo objeto "Client" con los parámetros especificados
			clientRepository.save(client2); // Guarda el objeto "Client" en la base de datos

			//crea  cliente en 1 sola línea!
			clientRepository.save(new Client("Juan", "Perez", "PerezJuan@gmail.com", passwordEncoder.encode("123"))); // Crea y guarda un nuevo objeto "Client" en la base de datos

			// Crear dos instancias de la clase Card para cada cliente melba
			Card card1 = new Card(CardType.DEBIT, "1223-4512-3456-8565", 542, client.getFirstName() + " " + client.getLastName(), LocalDate.now(), LocalDateTime.now().plusYears(5), CardColor.GOLD);
			Card card2 = new Card(CardType.CREDIT, "5522-5678-9234-4632", 325, client.getFirstName() + " " + client.getLastName(), LocalDate.now(), LocalDateTime.now().plusYears(5), CardColor.TITANIUM);

			// Asociar cada instancia de Card con el objeto Client correspondiente
			card1.setClient(client);
			card2.setClient(client);

			// Guardar cada instancia de Card en la base de datos utilizando el repositorio cardRepository
			cardRepository.save(card1);
			cardRepository.save(card2);

			// Agregar cada instancia de Card a la lista de cards del objeto Client correspondiente
			client.addCards(card1);
			client.addCards(card2);

		};
	}

}
	/* El código crea una aplicación Spring Boot que inicializa una base de datos y guarda algunos objetos
	(clientes, cuentas bancarias, transacciones y préstamos) en ella. El método initData() se ejecuta al
	inicio de la aplicación y realiza las tareas mencionadas.
	 */
