module ejerciciopedidos.ejerciciopedidos {
    requires javafx.controls;
    requires javafx.fxml;


    opens ejerciciopedidos.ejerciciopedidos to javafx.fxml;
    exports ejerciciopedidos.ejerciciopedidos;
}