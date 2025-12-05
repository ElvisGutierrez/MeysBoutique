module com.example.meysboutique {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;


    opens com.example.meysboutique to javafx.fxml;
    exports com.example.meysboutique;
}