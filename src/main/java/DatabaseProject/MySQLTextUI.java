package main.java.DatabaseProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class MySQLTextUI {
    public static void main(String[] args) throws Exception {
        String scriptName = "sample.sql"; // script to run DDL and populate database
        String query = "SELECT * FROM testtable1"; // sample query

        // At the start of the session, enter the username and password for the connection to log in
        // default username for MySQL connection is "root"
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = myObj.nextLine();

        System.out.println("Enter password:");
        String password = myObj.nextLine();

        MySQL mysql = new MySQL(username, password);

        try {
            mysql.runScript(scriptName); // run script to initialize database

            // run sample query
            Pair<List<String>, List<List<String>>> results = mysql.runQuery(query);
            List<String> attributes = results.left;
            List<List<String>> tuples = results.right;

            // print result of query to console
            System.out.println("Results for query [" + query + "]:");
            for (String a : attributes) {
                System.out.print(a + ", ");
            }
            System.out.println();

            for (List<String> tuple : tuples) {
                for (String val : tuple) {
                    System.out.print(val + ", ");
                }
                System.out.println();
            }

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            mysql.close();
        }

        Parent root = FXMLLoader.load(getClass().getResource("databaseUI.fxml"));
        primaryStage.setTitle("Movie Database");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}