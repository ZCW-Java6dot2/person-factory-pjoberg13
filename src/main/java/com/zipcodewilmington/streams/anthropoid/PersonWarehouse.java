package com.zipcodewilmington.streams.anthropoid;

import com.zipcodewilmington.streams.tools.ReflectionUtils;
import com.zipcodewilmington.streams.tools.logging.LoggerHandler;
import com.zipcodewilmington.streams.tools.logging.LoggerWarehouse;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by leon on 5/29/17.
 * The warehouse is responsible for storing, retrieving, and filtering personSequence
 *
 * @ATTENTION_TO_STUDENTS You are FORBIDDEN from using loops of any sort within the definition of this class.
 */
public final class PersonWarehouse implements Iterable<Person> {
    private final LoggerHandler loggerHandler = LoggerWarehouse.getLogger(PersonWarehouse.class);
    private final List<Person> people = new ArrayList<>();

    /**
     * @param person the Person object to add to the warehouse
     * @ATTENTION_TO_STUDENTS You are FORBIDDEN from modifying this method
     */
    public void addPerson(Person person) {
        loggerHandler.disbalePrinting();
        loggerHandler.info("Registering a new person object to the person warehouse...");
        loggerHandler.info(ReflectionUtils.getFieldMap(person).toString());
        people.add(person);
    }

    /**
     * @return list of names of Person objects
     */ // TODO
    public List<String> getNames() {
        //List<String> namesList = new ArrayList<>();

        return people.stream()
                //.map(Person::getName)
                .map(P -> P.getName())
                .collect(Collectors.toList());
    }


    /**
     * @return list of uniquely named Person objects
     */ //TODO
    public Stream<Person> getUniquelyNamedPeople() {
        //implementation of the helper method and solution Josh came up with during open house
        //return people.stream().filter(P -> getOccurrences(P.getName()) == 1L);

        //implementation of code I more or less copy/pasted from the internet. I have no idea how this shit works
        return people.stream().filter(distinctByKey(P -> P.getName()));
    }

    /**
     *
     * @param keyExtractor
     * @param <T>
     * @return returns a map with all the Person objects and their associated names
     */
    public <T>Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * helper function needed for the solution Josh came up with during open house
     * @param name, name of person object being evaluated
     * @return returns a Long representing the number of Person objects with that name associated
     */
    public Long getOccurrences(String name) {
        System.out.println(people.stream().filter(person -> person.getName().equals(name)).count());
        return people.stream().filter(P -> P.getName().equals(name)).count();
    }

    /**
     * @param character starting character of Person objects' name
     * @return a Stream of respective
     */ //TODO
    public Stream<Person> getUniquelyNamedPeopleStartingWith(Character character) {
        return getUniquelyNamedPeople().filter(P -> P.getName().charAt(0) == character);
    }

    /**
     * @param
     * @return a Stream of respective
     */ //TODO
    public Stream<Person> getFirstNUniquelyNamedPeople(int n) {
        return getUniquelyNamedPeople().limit(n);
    }

    /**
     * @return a mapping of Person Id to the respective Person name
     */ // TODO
    public Map<Long, String> getIdToNameMap() {
        return people.stream()
                .collect(Collectors.toMap(Person::getPersonalId, Person::getName));
    }


    /**
     * @return Stream of Stream of Aliases
     */ // TODO
    public Stream<Stream<String>> getNestedAliases() {
        return Stream.of(people.stream().flatMap(Person -> Arrays.stream(Person.getAliases())));
    }


    /**
     * @return Stream of all Aliases
     */ // TODO
    public Stream<String> getAllAliases() {
        return people.stream().flatMap(Person -> Arrays.stream(Person.getAliases()));
    }

    // DO NOT MODIFY
    public Boolean contains(Person p) {
        return people.contains(p);
    }

    // DO NOT MODIFY
    public void clear() {
        people.clear();
    }

    // DO NOT MODIFY
    public int size() {
        return people.size();
    }

    @Override // DO NOT MODIFY
    public Iterator<Person> iterator() {
        return people.iterator();
    }
}
