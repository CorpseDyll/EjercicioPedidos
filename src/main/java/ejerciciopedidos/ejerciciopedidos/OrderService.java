package ejerciciopedidos.ejerciciopedidos;

import java.io.IOException;
import java.util.ArrayList;

public class OrderService {
    private final OrderPersistence orderPersistence;

    public OrderService() {
        this.orderPersistence = new OrderPersistence();
    }

    public void addOrder(Order order) throws IOException {
        System.out.println("Añadiendo pedido: " + order.toFileString());
        orderPersistence.saveOrder(order);
    }

    public ArrayList<Order> getAllOrders() throws IOException{
        return  orderPersistence.loadAllOrders();
    }
}
