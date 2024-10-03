package ejerciciopedidos.ejerciciopedidos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Product> productsList = new ArrayList<>();
    private ListView<String> productosListView = new ListView<>();
    private TextField codigoField = new TextField();
    private TextField nombreField = new TextField();
    private TextField precioField = new TextField();
    private OrderService orderService = new OrderService();
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label labelCodigo = new Label("Código del producto:");
        Label labelNombre = new Label("Nombre del producto:");
        Label labelPrecio = new Label("Precio del producto:");

        Button agregarProductoBtn = new Button("Agregar Producto");
        agregarProductoBtn.setOnAction(e -> agregarProducto());

        Button finalizarPedidoBtn = new Button("Finalizar Pedido");
        finalizarPedidoBtn.setOnAction(e -> {
            try {
                finalizarPedido();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Botón para mostrar los pedidos guardados
        Button mostrarPedidosBtn = new Button("Mostrar Pedidos");
        mostrarPedidosBtn.setOnAction(e -> {
            try {
                mostrarPedidos();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        root.getChildren().addAll(labelCodigo, codigoField, labelNombre, nombreField, labelPrecio, precioField,
                agregarProductoBtn, productosListView, finalizarPedidoBtn, mostrarPedidosBtn);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Gestión de Pedidos");
        stage.show();
    }

    private void agregarProducto() {
        String codigo = codigoField.getText();
        String nombre = nombreField.getText();
        double precio = Double.parseDouble(precioField.getText());
        Product products = new Product(codigo, nombre, precio);
        productsList.add(products);
        productosListView.getItems().add(products.toString());
        codigoField.clear();
        nombreField.clear();
        precioField.clear();
    }

    private void finalizarPedido() throws IOException {
        Order order = new Order("Pedido:" + System.currentTimeMillis(), LocalDate.now(), productsList);
        orderService.addOrder(order);
        productsList.clear();
        productosListView.getItems().clear();
    }

    private void mostrarPedidos() throws IOException {
        ArrayList<Order> orders = orderService.getAllOrders();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pedidos Guardados");
        alert.setHeaderText("Lista de pedidos almacenados:");
        alert.setContentText(mostrarListaPedidos(orders));
        alert.showAndWait();
    }

    private String mostrarListaPedidos(ArrayList<Order> orders) {
        StringBuilder stringBuilder =new StringBuilder(" ");
        for (Order order : orders){
            stringBuilder.append(order.toFileString());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        launch();
    }
}