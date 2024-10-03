package ejerciciopedidos.ejerciciopedidos;

import java.io.*;
import java.util.ArrayList;

public class OrderPersistence {
    private final String DIRECTORY_PATH = "./persistence";
    private final String FILE_PATH = DIRECTORY_PATH + "orders.txt";

    public OrderPersistence(){
        try{
            // Crea el directorio si no existe.
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + DIRECTORY_PATH);
            }
            // Crear el archivo si no existe
            File file = new File(FILE_PATH);
            if (!file.exists() && !file.createNewFile()) {
                throw new IOException("No se pudo crear el archivo: " + FILE_PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrder(Order order) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
        BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(order.toFileString() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error escribiendo la cita en el archivo: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<Order> loadAllOrders() throws IOException{
        ArrayList<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                orders.add(Order.fromFileString(line));
            }
        }
        return orders;
    }
}
