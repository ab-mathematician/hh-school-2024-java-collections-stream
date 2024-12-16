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
/* Не понятно, как должен работать / работает сервис PersonService, а конкретно, его методы.
   Потому что я ожидаю, что буду передавать в findResumes() personId, а не Collection<Integer>.
   Если для каждого personId существует ровно один Person, то можно предположить, что findPersons() вернет
   набор Set<Person> из ровно того же числа элементов, что и на входе. Но при этом не понятно, чего ожидать
   от findResumes(), если одному personId может соответствовать несколько резюме.
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
    Map<Integer, Set<Resume>> resumesMap;

    // Составляем HashMap из resumes. Так как они Set и каждый раз бегать по всему Set будет накладно.
    resumesMap = resumes.stream()
        .collect(Collectors.toMap(
            Resume::personId,
            resume -> Set.of(resume),
            (existing, recent) -> Stream.concat(existing.stream(), recent.stream())
                    .collect(Collectors.toSet())));

      return persons.stream()
            .map(person -> new PersonWithResumes(
                    person,
                    resumesMap.getOrDefault(person.id(), Set.of())))
            .collect(Collectors.toSet());
  }
}
