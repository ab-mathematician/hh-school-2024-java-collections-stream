package tasks;

import common.Person;
import common.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.junit.StrictStubsRunnerTestListener;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class Task9Test {

  Task9 task9 = new Task9();
  private Person person1;
  private Person fakePerson;
  private Person person2;
  private Person person3;
  private Person person4;
  private Resume resume1;
  private Resume resume2;
  private Resume resume3;
  private Resume resume4;
  private Set<Person> persons1;
  private Set<Person> persons2;
  private Set<Person> persons3;

  @BeforeEach
  void initEach() {
    fakePerson = new Person(1, "Fake", "Fake", "Fake", Instant.now());
    person1 = new Person(1, "Name1", "Family1", "Father1", Instant.now());
    person2 = new Person(2, "Name2", null, "Father2", Instant.now());
    person3 = new Person(3, "Name3", "Family3", null, Instant.now());
    person4 = new Person(4, null, "Family4", null, Instant.now());
    resume1 = new Resume(1, 1, "text1");
    resume2 = new Resume(1, 2, "text1");
    resume3 = new Resume(2, 3, "text1");
    resume4 = new Resume(2, 4, "text1");
  }

  @Test
  public void getNamesTest(){
    List<Person> persons1 = List.of(fakePerson, person1, person1, person2, person3);
    assertEquals(List.of("Name1", "Name1", "Name2", "Name3"),
            task9.getNames(persons1));

    List<Person> persons2 = List.of(fakePerson);
    assertEquals(List.of(),
            task9.getNames(persons2));

    List<Person> persons3 = List.of();
    assertEquals(List.of(),
            task9.getNames(persons3));
  }

  @Test
  public void getDifferentNamesTest(){
    List<Person> persons1 = List.of(fakePerson, person1, person1, person2, person3);
    assertEquals(Set.of("Name1", "Name2", "Name3"),
            task9.getDifferentNames(persons1));

    List<Person> persons2 = List.of(fakePerson);
    assertEquals(Set.of(),
            task9.getDifferentNames(persons2));

    List<Person> persons3 = List.of();
    assertEquals(Set.of(), task9.getDifferentNames(persons3));
  }

  @Test
  public void convertPersonToStringTest(){
    assertEquals("Family1 Name1 Father1", task9.convertPersonToString(person1));
    assertEquals("Name2 Father2",         task9.convertPersonToString(person2));
    assertEquals("Family3 Name3",         task9.convertPersonToString(person3));
    assertEquals("Family4",               task9.convertPersonToString(person4));
  }

  @Test
  public void getPersonNamesTest() {
    List<Person> personsList1 = List.of(person1, person2, person3, person4, person1);
    HashMap<Integer, String> personsMap1 = new HashMap<Integer, String>(4);
    personsMap1.put(1, "Family1 Name1 Father1");
    personsMap1.put(2, "Name2 Father2");
    personsMap1.put(3, "Family3 Name3");
    personsMap1.put(4, "Family4");
    assertEquals(personsMap1, task9.getPersonNames(personsList1));
  }

  @Test
  public void hasSamePersonsTest() {
    persons1 = Set.of(person1, person2, person3);
    persons2 = Set.of(person1, person2);
    persons3 = Set.of();

    assertTrue (task9.hasSamePersons(persons1, persons2));
    assertFalse(task9.hasSamePersons(persons2, persons3));
    assertFalse(task9.hasSamePersons(persons3, persons2));
  }

  @Test
  public void countEvenTest() {
    assertEquals(0, task9.countEven(Stream.of()));
    assertEquals(1, task9.countEven(Stream.of(0)));
    assertEquals(1, task9.countEven(Stream.of(1,2,3)));
    assertEquals(2, task9.countEven(Stream.of(-1, -2, 1, 2)));
  }

  @Test
  public void listVsSetTest() {
    task9.listVsSet();

    Integer value = 223;
    assertEquals(value, value.hashCode());
  }
}
