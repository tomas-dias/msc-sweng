package renaming.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args) throws IOException {
        Path root = Paths.get(args[0]);

        int numEmployees = (int) Files.walk(root)
                .filter(p -> !p.getFileName().toString().equals("tree"))
                .count();

        Manager manager = new Manager(numEmployees);

        Customer customer = new Customer(root, args[1], args[2], manager.getAddress());

        customer.getAddress().sendMessage(new BootstrapMessage());
    }
}