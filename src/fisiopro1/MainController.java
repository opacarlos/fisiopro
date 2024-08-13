package fisiopro1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController implements Initializable {
    @FXML
    private TextField tf_usuario;
    @FXML
    private TextField tf_senha;
    @FXML
    private Text incorreto;

    @FXML
    private void entrar(ActionEvent event) throws Exception{
        Connection conexao = null;
        PreparedStatement sql = null;
        ResultSet resultado = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND senha = ?");
            sql.setString(1, tf_usuario.getText());
            sql.setString(2, tf_senha.getText());
            resultado = sql.executeQuery();
            if(resultado.next()){
                Parent root = null;
                root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("FisioPro - Home");
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();
            }else{
                incorreto.setText("Usuário ou senha incorretos!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void entrar_enter(KeyEvent event) throws Exception{
        if (event.getCode() == KeyCode.ENTER) {
            Connection conexao = null;
            PreparedStatement sql = null;
            ResultSet resultado = null;
            try{
                conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
                sql = conexao.prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND senha = ?");
                sql.setString(1, tf_usuario.getText());
                sql.setString(2, tf_senha.getText());
                resultado = sql.executeQuery();
                if(resultado.next()){
                    Parent root = null;
                    root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("FisioPro - Home");
                    stage.setScene(new Scene(root));
                    stage.setMaximized(true);
                    stage.show();
                }else{
                    incorreto.setText("Usuário ou senha incorretos!");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}