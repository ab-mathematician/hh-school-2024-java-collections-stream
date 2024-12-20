package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */

/* BA = BogerAndrey */

public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  // BA: Удалять элементы нельзя (remove(0)), так как мы меняем входной массив, ну и он может быть не изменяемым.
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  // BA: distinct лишний, так как Set не содержит дублей
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  // BA: Не очень корректно обрабатываются ситуации с отсутствующими полями
  // BA: Третьим полем должно быть не secondName, а middleName (кажется).
  public String convertPersonToString(Person person) {
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // BA: не имя, а ФИО. Вообще есть претензия к названию метода.
  // BA: stream должны быть побыстрее, чем циклы.
  // BA: Странно создавать Map с размером 1, лучше уж с persons.size() .
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(
                    Person::id,
                    this::convertPersonToString,
                    (existing, recent) -> existing
                    )
            );
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // BA: странное название переменной has, лучше уж intersectionFlag
  // BA: Нет смысла пробегать по всем парам, можно выйти при первом совпадении. Для цикла это break, для stream - limit.
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    HashSet<Person> persons1Set = new HashSet<>(persons1);
    return persons2.stream().anyMatch(persons1Set::contains);
  }

  // Посчитать число четных чисел
  // BA: неприлично менять переменные внутри stream.
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // BA: Пояснение: Hash от int - сам int, а элементы коллекции упорядочены по остатку от деления hash на размер capacity.
  // Так как здесь capacity ( == число_элементов_HashSet / loadFactor) больше максимального hashCode,
  // то индекс в хэш-таблице будет вычисляться как остаток от деления значения элемента на размер таблицы.
  // Получается, элементы будут отсортированы по возрастанию.

  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
