/**
 * @author Elijah Bretbacher
 * @version v1.0 17.12.2020
 */
package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;


public class Controller implements Initializable{
    @FXML
    TextField txtf_input=new TextField();
    @FXML
    ListView<Double> ls_values=new ListView<>();

    Stack<Double> stack = new Stack<>();

    private Stage stage;

    public Controller(){

    }

    public static void show(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Controller.class.getResource("sample.fxml"));
            Parent root = loader.load();

            Controller c=loader.getController();

            c.setStage(stage);

            stage.setTitle("Moin");
            stage.setScene(new Scene(root));
            stage.show();

        }catch(Exception ignored){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public void handle_btnPressed(Event event){

        Object obj=event.getSource();
        Button btn=(Button)obj;
        int v=0;
        String op;
        boolean isNum=true;
        try{
            v=Integer.parseInt(btn.getText());
        }catch (Exception e){
            System.out.println("not a number");
            isNum=false;
        }
        if (isNum){
            txtf_input.appendText("" + v);
        }else{
            op=btn.getText();
            switch (btn.getText()){
                case ".":
                    txtf_input.appendText(op);
                    break;
                case "--":
                    if (txtf_input.getText().contains("-")){
                        txtf_input.setText(txtf_input.getText().replace("-", "+"));
                    }else{
                        if (txtf_input.getText().contains("+")){
                            txtf_input.setText(txtf_input.getText().replace("+", "-"));
                        }else{
                            txtf_input.setText("-" + txtf_input.getText());
                        }
                    }
                    break;
                case "ENTER":
                    addValueToList();
                    break;
                case "C":
                    clearAll();
                    break;
                case "CE":
                    txtf_input.clear();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    calc(op);
                    break;
                case "1/x":
                    txtf_input.setText(String.format("%f",1/Double.parseDouble(txtf_input.getText())).replace(",", "."));
                    break;
                case "x-y-z":
                    initStack();

                    Double help = stack.pop();
                    Double help2=stack.pop();

                    ls_values.getItems().remove(0, 2);

                    stack.push(help);
                    stack.push(help2);

                    ls_values.getItems().add(help);
                    ls_values.getItems().add(help2);
                    stack.clear();
                    break;
                default:
                    clearAll();
                    txtf_input.setText("ERROR");
            }
        }
    }

    void calc(String op){
        initStack();

        double v = stack.pop();

        switch (op){
            case "+":
                while(stack.size()!=0) {
                    v+=stack.pop();
                }
                break;
            case "-":
                while(stack.size()!=0) {
                    v-=stack.pop();
                }
                break;
            case "*":
                while(stack.size()!=0) {
                    v*=stack.pop();
                }
                break;
            case "/":
                while(stack.size()!=0) {
                    v/=stack.pop();
                }
                break;
        }
        ls_values.getItems().clear();
        ls_values.getItems().add(v);
    }

    void clearAll(){
        txtf_input.clear();
        ls_values.getItems().removeAll(ls_values.getItems());
    }

    void addValueToList(){
        ls_values.getItems().add(Double.parseDouble(txtf_input.getText()));
        txtf_input.clear();
    }

    void initStack(){
        try {
            addValueToList();
            System.out.println("success");
        }catch(Exception ignored){
            System.out.println("failed");
        }
        stack.clear();
        for (int i = ls_values.getItems().size()-1; i >= 0; i--) {
            stack.push(ls_values.getItems().get(i));
        }
    }
}
