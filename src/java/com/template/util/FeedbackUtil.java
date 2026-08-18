package com.template.util;

import javafx.scene.control.Label;


public class FeedbackUtil {
    
    public static void sucesso(Label label, String mensagem) {
        label.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        label.setText(mensagem);
    }

    
    public static void erro(Label label, String mensagem) {
        label.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        label.setText(mensagem);
    }

    public static void limpar(Label label) {
        label.setStyle("");
        label.setText("");
    }
}
