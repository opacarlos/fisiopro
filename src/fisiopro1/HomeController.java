package fisiopro1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class HomeController implements Initializable{
    @FXML
    private Tab tab_clientes;
    @FXML
    private Tab tab_novo_cliente;
    @FXML
    private Tab tab_procurar_cliente;
    @FXML
    private Tab tab_agendamentos;
    @FXML
    private TabPane tabpane_primaria;
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private TextField data_de_nascimento;
    @FXML
    private TextField endereco;
    @FXML
    private TextField bairro;
    @FXML
    private TextField cidade;
    @FXML
    private TextField contato;
    @FXML
    private TextField email;
    @FXML
    private TextArea observacao;
    @FXML
    private Text cadastrado;
    @FXML
    private Text verificar;
    @FXML
    private TextField tf_buscar;
    @FXML
    private Button buscar;
    @FXML
    private TableView<Clientes> tabela;
    @FXML
    private TableView<Clientes> tabela_agendamento;
    @FXML
    private TableColumn<Clientes, Integer> coluna_id;
    @FXML
    private TableColumn<Clientes, String> coluna_nome;
    @FXML
    private TableColumn<Clientes, String> coluna_cpf;
    @FXML
    private TableColumn<Clientes, String> coluna_cpf_agendamento;
    @FXML
    private TableColumn<Clientes, String> coluna_nome_agendamento;
    @FXML
    private TableColumn<Clientes, String> coluna_data_agendamento;
    @FXML
    private TableColumn<Clientes, String> coluna_hora_agendamento;
    @FXML
    private Button editar;
    @FXML
    private Button deletar;
    @FXML
    private RadioButton radio_nome;
    @FXML
    private RadioButton radio_cpf;
    @FXML
    private TextField data_agendamento;
    @FXML
    private TextField hora;
    
    private RadioButton radio_selecionado;
    
    private ToggleGroup toggleGroup2;
    
    public static Clientes selecionado;
    
    public static Clientes selecionado_agendamento;
    
    private List<Clientes> listClientes = new ArrayList();
    
    private ObservableList<Clientes> observableListClientes;
    
    private TableView tabela2;
    
    private ImageView imagem(String caminho_imagem, int tamanho){
        ImageView imageView = null;
        Image imagem = new Image(getClass().getResourceAsStream(caminho_imagem));
        imageView = new ImageView(imagem);
        imageView.setFitWidth(tamanho);
        imageView.setFitHeight(tamanho);
        return imageView;
    }
    
    @FXML
    private void cadastrar(ActionEvent event) throws Exception{
        Connection conexao = null;
        PreparedStatement sql = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("INSERT INTO clientes (nome, cpf, data_de_nascimento, endereco, bairro, cidade, contato, email, data_agendamento, hora, observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sql.setString(1, nome.getText());
            sql.setString(2, cpf.getText());
            sql.setString(3, data_de_nascimento.getText());
            sql.setString(4, endereco.getText());
            sql.setString(5, bairro.getText());
            sql.setString(6, cidade.getText());
            sql.setString(7, contato.getText());
            sql.setString(8, email.getText());
            sql.setString(9, data_agendamento.getText());
            sql.setString(10, hora.getText());
            sql.setString(11, observacao.getText());
            
            int resultado = sql.executeUpdate();
            if(resultado > 0){
                cadastrado.setText("Usuário cadastrado com sucesso!");
                List<Clientes> clientes = carregarTableViewClientes();
                tabela.getItems().clear();
                tabela.getItems().addAll(clientes);
                
                List<Clientes> pacientes = carregarTableViewClientesAgendamento();
                tabela_agendamento.getItems().clear();
                tabela_agendamento.getItems().addAll(pacientes);
            }
        }catch(SQLException e){
            e.printStackTrace();
            cadastrado.setText("Usuário não cadastrado.");
            verificar.setText("Verifique se os campos estão corretos.");
        }
    }
    
    public static List<Clientes> carregarTableViewClientes(){
        List<Clientes> clientes = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement sql = null;
        ResultSet resultado = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("SELECT * FROM clientes");
            resultado = sql.executeQuery();
            while(resultado.next()){
                Clientes cliente = new Clientes(0,"","","","","");
                cliente.setId(resultado.getInt("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCPF(resultado.getString("cpf"));
                //cliente.setDataAgendamento(resultado.getString("data_agendamento"));
                //cliente.setHora(resultado.getString("hora"));
                clientes.add(cliente);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return clientes;
    }
    
    public static List<Clientes> carregarTableViewClientesAgendamento(){
        List<Clientes> clientes = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement sql = null;
        ResultSet resultado = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("SELECT * FROM clientes WHERE data_agendamento IS NOT NULL AND hora IS NOT NULL");
            resultado = sql.executeQuery();
            while(resultado.next()){
                Clientes cliente = new Clientes(0,"","","","","");
                cliente.setId(resultado.getInt("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCPF(resultado.getString("cpf"));
                cliente.setDataAgendamento(resultado.getString("data_agendamento"));
                cliente.setHora(resultado.getString("hora"));
                clientes.add(cliente);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return clientes;
    }
    
    @FXML
    private void deletar(ActionEvent event) throws Exception{
        Label label = new Label("Você realmente deseja excluir este paciente?");
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não");
        Alert alert = new Alert(AlertType.CONFIRMATION,"", sim, nao);
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(label);

        alert.setTitle("Confirmação de Exclusão");
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(response -> {
            if (response == sim) {
                Connection conexao = null;
                PreparedStatement sql = null;
                try{
                    conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
                    sql = conexao.prepareStatement("DELETE FROM clientes WHERE id = ?");
                    sql.setInt(1, selecionado.getId());
                    int resultado = sql.executeUpdate();
                    if(resultado > 0){
                        List<Clientes> clientes = carregarTableViewClientes();
                        tabela.getItems().clear();
                        tabela.getItems().addAll(clientes);

                        List<Clientes> pacientes = carregarTableViewClientesAgendamento();
                        tabela_agendamento.getItems().clear();
                        tabela_agendamento.getItems().addAll(pacientes);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    private void editar(ActionEvent event) throws Exception{
        Stage janelaEditar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Editar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        janelaEditar.setScene(scene);
        janelaEditar.setTitle("FisioPro - Editar Paciente");
        janelaEditar.show();
        janelaEditar.setOnHidden(e -> {
            List<Clientes> clientes = carregarTableViewClientes();
            tabela.getItems().clear();
            tabela.getItems().addAll(clientes);
            
            List<Clientes> pacientes = carregarTableViewClientesAgendamento();
            tabela_agendamento.getItems().clear();
            tabela_agendamento.getItems().addAll(pacientes);
        });
    }
    
    @FXML
    private void buscar(ActionEvent event) throws Exception{
        radio_selecionado = (RadioButton) toggleGroup2.getSelectedToggle();
        /*if (radio_selecionado != null) {
            System.out.println("RadioButton selecionado: " + radio_selecionado.getText());
        } else {
            System.out.println("Nenhum RadioButton selecionado.");
        }*/
        List<Clientes> clientes = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement sql = null;
        ResultSet resultado = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("SELECT * FROM clientes WHERE " + radio_selecionado.getText() + " LIKE ?");
            sql.setString(1, "%" + tf_buscar.getText() + "%");
            resultado = sql.executeQuery();
            while(resultado.next()){
                Clientes cliente = new Clientes(0,"","","","","");
                cliente.setId(resultado.getInt("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCPF(resultado.getString("cpf"));
                clientes.add(cliente);
            }
            tabela.getItems().clear();
            tabela.getItems().addAll(clientes);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void finalizado(ActionEvent event) throws Exception{
        Connection conexao = null;
        PreparedStatement sql = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiopro", "root", "admin");
            sql = conexao.prepareStatement("UPDATE clientes SET data_agendamento = NULL, hora = NULL WHERE id = ?;");
            sql.setInt(1, selecionado_agendamento.getId());
            int resultado = sql.executeUpdate();
            if(resultado > 0){
                List<Clientes> pacientes = carregarTableViewClientesAgendamento();
                tabela_agendamento.getItems().clear();
                tabela_agendamento.getItems().addAll(pacientes);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView imagem1 = imagem("imagens/clientes.png", 55);
        tab_clientes.setGraphic(imagem1);
        ImageView imagem2 = imagem("imagens/novo_cliente.png", 35);
        tab_novo_cliente.setGraphic(imagem2);
        ImageView imagem3 = imagem("imagens/procurar_cliente.png", 30);
        tab_procurar_cliente.setGraphic(imagem3);
        ImageView imagem4 = imagem("imagens/agenda.png", 20);
        tab_agendamentos.setGraphic(imagem4);
        tabpane_primaria.setStyle(
                "-fx-tab-min-width: 150;" + // Largura mínima da guia
                "-fx-tab-max-width: 150;" + // Largura máxima da guia
                "-fx-tab-min-height: 40;" + // Altura mínima da guia
                "-fx-tab-max-height: 40;"   // Altura máxima da guia
        );
        //coluna_id.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        coluna_nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        coluna_cpf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCPF()));
        //tabela.getColumns().addAll(coluna_id, coluna_nome, coluna_cpf);
        tabela.getColumns().addAll(coluna_nome, coluna_cpf);
        List<Clientes> clientes = carregarTableViewClientes();
        tabela.getItems().addAll(clientes);
        tabela.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selecionado = (Clientes) newValue;
            }
        });
        ToggleGroup toggleGroup = new ToggleGroup();
        radio_nome.setToggleGroup(toggleGroup);
        radio_cpf.setToggleGroup(toggleGroup);
        radio_nome.setSelected(true);
        radio_selecionado = (RadioButton) toggleGroup.getSelectedToggle();
        if (radio_selecionado != null) {
            System.out.println("RadioButton selecionado: " + radio_selecionado.getText());
        } else {
            System.out.println("Nenhum RadioButton selecionado.");
        }
        toggleGroup2 = toggleGroup;
        
        //Tabela agendamentos:
        coluna_cpf_agendamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCPF()));
        coluna_nome_agendamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        coluna_data_agendamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataAgendamento()));
        coluna_hora_agendamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHora()));
        tabela_agendamento.getColumns().addAll(coluna_cpf_agendamento, coluna_nome_agendamento, coluna_data_agendamento, coluna_hora_agendamento);
        List<Clientes> pacientes = carregarTableViewClientesAgendamento();
        tabela_agendamento.getItems().addAll(pacientes);
        tabela_agendamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selecionado_agendamento = (Clientes) newValue;
            }
        });
    }
}
