package org.lambda;

import org.lambda.patterns.observer.Observer;
import org.lambda.patterns.observer.impl.ObservedImpl;
import org.lambda.patterns.observer.impl.ObserverImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    private static List<Transaction> getTransactions() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader walsh = new Trader("Walsh","Qipbridge");
        Trader brian = new Trader("Brian","Vonbury");
        Trader hart = new Trader("Hart","Kriver");
        Trader collins = new Trader("Collins","Rurg");
        Trader palmer = new Trader("Palmer","Odondon");
        Trader chapman = new Trader("Chapman","Krido");
        Trader davis = new Trader("Davis","Fririe");
        Trader stephens = new Trader("Stephens","Oreledo");
        Trader wilson = new Trader("Wilson","Isonshire");
        Trader wells = new Trader("Wells","Nerough");

        return Arrays.asList(
                new Transaction(raoul, 2011, 200), new Transaction(mario, 2012, 400),
                new Transaction(alan, 2023, 1000),  new Transaction(walsh, 2022, 1500),
                new Transaction(brian, 2020, 3000), new Transaction(hart, 2019, 2000),
                new Transaction(collins, 2016, 1750), new Transaction(palmer, 2015, 1250),
                new Transaction(chapman, 2018, 950), new Transaction(davis, 2013, 450),
                new Transaction(stephens, 2021, 750), new Transaction(wilson, 2014, 350),
                new Transaction(wells, 2016, 1350)
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        * Вариант если синхронизируются пользователи после добавления новых
        * */

        List<User> inputFromAnyService = Arrays.asList(
                new User(1, "kamegatze", "sh", "al", "pl"),
                new User(2, "sap", "sh", "al", "pl"),
                new User(3, "kamegatze", "sh", "al", "pl"),
                new User(4, "km", "sh", "al", "pl"),
                new User(5, "kamegatze", "sh", "al", "pl"),
                new User(6, "kamegatze", "sh", "al", "pl"),
                new User(7, "opl", "sh", "al", "pl"),
                new User(8, "kamegatze", "sh", "al", "pl")
        );

        List<User> inputFromDatabase = Arrays.asList(
                new User(1, "kamegatze", "sh", "al", "pl"),
                new User(2, "kamegatze", "sh", "al", "pl"),
                new User(3, "kamegatze", "sh", "al", "pl"),
                new User(4, "kamegatze", "sh", "al", "pl"),
                new User(5, "kamegatze", "sh", "al", "pl"),
                new User(6, "kamegatze", "sh", "al", "pl"),
                new User(7, "kamegatze", "sh", "al", "pl"),
                new User(8, "kamegatze", "sh", "al", "pl")
        );

        Set<User> inputFromDatabaseSet = new HashSet<>(inputFromDatabase);

        List<User> notInDatabase = new ArrayList<>();
        /*
        * O(inputFromAnyService.length) * O(1) = O(inputFromAnyService.length)
        * */
        inputFromAnyService.forEach(user -> {
            if (!inputFromDatabaseSet.contains(user)) {
                notInDatabase.add(user);
            }
        });
        ObservedImpl<User> observed = new ObservedImpl<>();
        observed.removeAll();
        Observer<User> observerOne = data -> {
            System.out.printf("get data from one lambda: %s\n", data);
        };
        Observer<User> observerTwo = data -> {
            System.out.printf("get data from two lambda: %s\n", data);
        };
        observed.addObserver(observerOne);
        observed.addObserver(observerTwo);
        if (!notInDatabase.isEmpty()) {
            observed.addAll(notInDatabase);
        }

    }
}

record Trader(String name, String city) {}

class User {
    private final Integer id;
    private final String login;
    private final String lastName;
    private final String firstName;
    private final String patronymic;

    public User(Integer id, String login, String lastName, String firstName, String patronymic) {
        this.id = id;
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) &&
                Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) &&
                Objects.equals(patronymic, user.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, lastName, firstName, patronymic);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}

class Transaction {
    private final Trader trader;
    private final Integer year;
    private final Integer value;

    public Trader getTrader() {
        return trader;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }

    public Transaction(Trader trader, Integer year, Integer value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }
}