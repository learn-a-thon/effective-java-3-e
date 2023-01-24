package item3.serialization;

import java.io.*;
import java.time.LocalDate;

public class SerializationExample {

    private void serialize(Book book) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("book.obj"))) {
            out.writeObject(book);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Book deserialize() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("book.obj"))) {
            return (Book) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Book book = new Book("123", "이펙티브자바3판", "홍길동", LocalDate.of(2006, 9, 22));
        book.setNumberOfSold(200);

        SerializationExample example = new SerializationExample();
        example.serialize(book);
        Book deserializeBook = example.deserialize();

        System.out.println(book);
        System.out.println(deserializeBook);
    }
}
