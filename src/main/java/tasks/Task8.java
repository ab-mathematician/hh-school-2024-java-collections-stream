package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
  Еще один вариант задачи обогащения
  На входе имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */

public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<Resume> resumes = personService.findResumes(
        persons.stream()
            .map(person -> person.id())
            .collect(Collectors.toSet())
    );

    // Составляем HashMap из resumes. Так как они Set и каждый раз бегать по всему Set будет накладно.
    Map<Integer, Set<Resume>> resumesMap = resumes.stream().
        collect(
            Collectors.groupingBy(
                Resume::personId,
                Collectors.toSet()
            )
        );

      return persons.stream()
            .map(person -> new PersonWithResumes(
                    person,
                    resumesMap.getOrDefault(person.id(), Set.of())))
            .collect(Collectors.toSet());
  }
}
