package fisiopro1;

//Application fornece a estrutura básica
import javafx.application.Application;
//FXMLLoader é usado para carregar arquivos FXML
import javafx.fxml.FXMLLoader;
//Parent serve para representar o nó pai
import javafx.scene.Parent;
//Para usar Scene
import javafx.scene.Scene;
//Para usar Stage
import javafx.stage.Stage;

public class FisioPro1 extends Application {
    
    //Para sobrescrever em um método já existente do Application
    @Override
    //O método start pode lançar a exceção Exception
    public void start(Stage stage) throws Exception {
        //O nó pai (tela principal) é armazenada em root
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        //Para explicar as 3 próximas linhas de código deve-se entender a diferença entre Scene e Stage:
        //Em resumo, o Stage é a janela principal da aplicação, enquanto a Scene é o conteúdo gráfico exibido dentro dessa janela.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("FisioPro - Login");
        stage.show();
    }
    
    public static void main(String[] args) {
        //Launch é responsável por inicializar a aplicação chamando o método start
        launch(args);
    }
    
}