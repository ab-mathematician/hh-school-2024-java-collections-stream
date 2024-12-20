package tasks;

import common.Area;
import common.Person;
import common.PersonService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */

// Асимптотика O(2n): [O(n) = personsMap] + [O(n) = personIds]
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personsMap = persons.stream()
        .collect(Collectors.toMap(person -> person.id(), person -> person));
    return personIds.stream().map(personId -> personsMap.get(personId)).toList();
  }
}
