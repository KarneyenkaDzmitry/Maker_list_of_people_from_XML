import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*I'm sorry. I didn't have enough time to write comments. I'm really sorry.*/
public class PeopleXML {

    private static String help = "Recall the program and put an exist XML file as the first argument and push \"enter\"";
    private static Set<Person> setOfPeople = new TreeSet<Person>();//it help us to sort people by alphabet.
    private static int maxName = 0;
    private static int minName = Integer.MAX_VALUE;
    private static Map<Integer, Set<Person>> mapOfPerson = new TreeMap<>();//It helps us to sort person by length of name

    public static void main(String[] args) throws IOException {
        /*We check input parameters. Valid or not.*/
        if (args.length > 0) {
            if (args[0].endsWith(".xml")) {
                Path path = Paths.get(args[0]);
                if (Files.exists(path)) {
                    findPeople(path);//Call method that parses XML file and forms Set, and map.
                    System.out.println("There is list of all people:");
                    for (Person per : setOfPeople) {
                        System.out.println(per.toString());
                    }
                    System.out.println("\nWho has min length of all names: " + minName + " chars :");
                    for (Person per : mapOfPerson.get(minName)) {
                        System.out.println(per.toString());
                    }
                    System.out.println("\nWho has max length of all names: " + maxName + " chars :");
                    for (Person per : mapOfPerson.get(maxName)) {
                        System.out.println(per.toString());
                    }
                } else {
                    System.out.println("File doesn't exist.");
                    System.out.println(help);
                }
            } else {
                System.out.println("The file isn't XML");
            }
        } else {
            System.out.println(help);
        }
    }

    /*The method finds all people and put them into Set and Map*/
    private static void findPeople(Path path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
        Pattern pattern = Pattern.compile("\\\"{1}\\W+\\\"{1}");//It needs to find "Петров Пётр Петрович"
        while (reader.ready()) {
            String line = reader.readLine();
            if (line.contains("<person fio=\"")) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String fio = matcher.group();
                    fio = fio.replaceAll("\"", "");
                    String[] names = fio.split(" ");
                    if (names.length == 3) {
                        Person person = new Person(names[0], names[1], names[2]);
                        setOfPeople.add(person);
                        int length = names[0].length() + names[1].length() + names[2].length();
                        if (minName > length) {
                            minName = length;
                        }
                        if (maxName < length) {
                            maxName = length;
                        }
                        if (!mapOfPerson.containsKey(length)) {
                            Set<Person> per = new TreeSet<>();
                            per.add(person);
                            mapOfPerson.put(length, per);
                        } else {
                            mapOfPerson.get(length).add(person);
                        }
                    }
                }
            }
        }
        reader.close();
    }
}
