module com.example.genetic_algo_sma {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;


    opens com.example.genetic_algo_sma.agents to javafx.fxml;
    exports com.example.genetic_algo_sma.agents to jade;
    exports com.example.genetic_algo_sma;
}