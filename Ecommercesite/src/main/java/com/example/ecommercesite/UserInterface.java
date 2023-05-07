package com.example.ecommercesite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;

    HBox footerBar;

    Button signInButton;

    Label welcomeLabel;

    VBox body ;
    Customer loggedInCustomer;
    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    BorderPane createContent(){
        BorderPane root=new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage);// method to add nodes as child to pane
        root.setTop(headerBar);
        // root.setCenter(loginPage);
        body=new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage=productList.getALLProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);
        return root;
    }

    private void createLoginPage(){
        Text usernameText = new Text("Username");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("bhushank@gmail.com");
        userName.setPromptText("Enter username");
        PasswordField password = new PasswordField();
        password.setText("123456");
        password.setPromptText("Enter Password");
        Label messageLabel = new Label();

        Button loginButton = new Button("Login");

        loginPage=new GridPane();
        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(usernameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(loginButton,1,2);
        loginPage.add(messageLabel,0,2);

        loginButton.setOnAction(actionEvent -> {
            String uname = userName.getText();
            String pass = password.getText();
            Login login = new Login();
            loggedInCustomer = login.customerLogin(uname,pass);
            if (loggedInCustomer != null){
                welcomeLabel.setText("Welcome "+loggedInCustomer.getName());
                headerBar.getChildren().add(welcomeLabel);
                body.getChildren().clear();
                body.getChildren().add(productPage);

            }else {
                messageLabel.setText("Error: wrong username or password");
            }

        });

    }
    private void createHeaderBar(){
        Button homeButton = new Button();
        Image logo = new Image("C:\\Users\\Bhushan\\IdeaProjects\\Ecommercesite\\src\\main\\img_1.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setFitHeight(20);
        homeButton.setGraphic(logoView);
        TextField search = new TextField();
        search.setPrefWidth(250);
        search.setPromptText("Type to search");
       welcomeLabel=new Label();
        Button searchButton = new Button("Search");
        signInButton = new Button("Sign in");
        Button cartButton = new Button("Cart");

         Button orderButton=new Button("Orders");

        headerBar = new HBox();
        headerBar.setSpacing(10);
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,search,searchButton,signInButton,cartButton,orderButton);

        signInButton.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(loginPage);
            headerBar.getChildren().remove(signInButton);
        });
        cartButton.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox cartPage = productList.getProductsInCart(itemsInCart);
            cartPage.setAlignment(Pos.CENTER);
            cartPage.setSpacing(10);
            cartPage.getChildren().add(placeOrderButton);

            body.getChildren().add(cartPage);
            footerBar.setVisible(false);
        });
        placeOrderButton.setOnAction(actionEvent -> {
            if(itemsInCart == null){
                showDialog("Please add items in cart to place order");
                return;
            }
            if (loggedInCustomer == null){
                showDialog("Please login first to place order");
                return;
            }
            int count=Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
            if(count!=0){
                showDialog("Order for"+count+" products placed successfully!!");
            }
            else{
                showDialog("Order failed!!");
            }
        });

        homeButton.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(productPage);
            footerBar.setVisible(true);
            if (loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton)==-1)
                headerBar.getChildren().add(signInButton);
        });
    }
    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add To Cart");

        footerBar = new HBox();
        footerBar.setSpacing(10);
        footerBar.setPadding(new Insets(10));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(actionEvent -> {
            Product product = productList.getSelectedProduct();
            if(product == null){
                showDialog("Please select product to place order");
                return;
            }
            if (loggedInCustomer == null){
                showDialog("Please login first to place order");
                return;
            }

            boolean status = Order.placeOrder(loggedInCustomer,product);
            if (status==true){
                showDialog("Order placed successfully");
            }else {
                showDialog("Order failed");
            }
        });

        addToCartButton.setOnAction(actionEvent -> {
            Product product = productList.getSelectedProduct();
            if(product == null){
                showDialog("Please select product to add in cart");
                return;
            }
            itemsInCart.add(product);
            showDialog("Selected Item has been added in cart");
        });

    }

    void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}













