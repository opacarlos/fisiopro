package fisiopro1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class EditarController implements Initializable {
    
    @FXML
    private TextField editar_nome;
    @FXML
    private TextField editar_cpf;
    @FXML
    private TextField editar_data_de_nascimento;
    @FXML
    private TextField editar_endereco;
    @FXML
    private TextField editar_bairro;
    @FXML
    private TextField editar_cidade;
    @FXML
    private TextField editar_contato;
    @FXML
    private TextField editar_email;
    @FXML
    private TextField editar_data_agendamento;
    @FXML
    private TextField editar_hora;
    @FXML
    private TextArea editar_observacao;
    @FXML
    public static AnchorPane anchorPane;
    @FXML
    private Button salvar;
    
    //public TableView<Clientes> tabela;
    private TableView<Clientes> tableView;
    private ObservableList<Clientes> data;
    
    @FXML
    private void salvar(ActionEvent event) throws Exception{
        Connection conexao = null;
        PreparedStatement sql = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("UPDATE clientes SET nome = ?, cpf = ?, data_de_nascimento = ?, endereco = ?, bairro = ?, cidade = ?, contato = ?, email = ?, data_agendamento = ?, hora = ?, observacao = ? WHERE id = ?");
            sql.setString(1, editar_nome.getText());
            sql.setString(2, editar_cpf.getText());
            sql.setString(3, editar_data_de_nascimento.getText());
            sql.setString(4, editar_endereco.getText());
            sql.setString(5, editar_bairro.getText());
            sql.setString(6, editar_cidade.getText());
            sql.setString(7, editar_contato.getText());
            sql.setString(8, editar_email.getText());
            sql.setString(9, editar_data_agendamento.getText());
            sql.setString(10, editar_hora.getText());
            sql.setString(11, editar_observacao.getText());
            sql.setInt(12, HomeController.selecionado.getId());
            int resultado = sql.executeUpdate();
            if(resultado > 0){
                System.out.println("Usuário salvo!");
                Stage stage = (Stage) salvar.getScene().getWindow();
                stage.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        Connection conexao = null;
        PreparedStatement sql = null;
        ResultSet resultado = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("SELECT * FROM clientes WHERE id = ?");
            sql.setInt(1, HomeController.selecionado.getId());
            resultado = sql.executeQuery();
            while(resultado.next()){
                editar_nome.setText(resultado.getString("nome"));
                editar_cpf.setText(resultado.getString("cpf"));
                editar_data_de_nascimento.setText(resultado.getString("data_de_nascimento"));
                editar_endereco.setText(resultado.getString("endereco"));
                editar_bairro.setText(resultado.getString("bairro"));
                editar_cidade.setText(resultado.getString("cidade"));
                editar_contato.setText(resultado.getString("contato"));
                editar_email.setText(resultado.getString("email"));
                editar_data_agendamento.setText(resultado.getString("data_agendamento"));
                editar_hora.setText(resultado.getString("hora"));
                editar_observacao.setText(resultado.getString("observacao"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
