package tasks;

import common.Person;
import common.PersonService;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */

// Асимптотика O(n2*log(n)): [O(n*log(n)) = sorted] * [O(n) = personIds]
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    return persons.stream()
            .sorted((p1, p2) ->
                Integer.compare(personIds.indexOf(p1.id()), personIds.indexOf(p2.id())))
            .toList();
  }
}
