package org.dimigo.gui.mine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*
        String body1 = sendGet("https://www.dimigo.hs.kr/index.php?mid=school_notice&category_srl=8169");
        String[] wMenus = body1.split("주간식단표")[0].split("href=\"");
        String wMenu = wMenus[wMenus.length - 1].split("\">")[0];
        System.out.println(wMenu);

        String body2 = sendGet(wMenu);
        String menuDown = body2.split("fileList")[1].split("href=\"")[1].split("\">")[0];
        menuDown = menuDown.replace("amp;", "");
        System.out.println(menuDown);

        String[] events = body1.split("이벤트")[0].split("href=\"");
        String event = events[events.length - 1].split("\">")[0];
        System.out.println(event);

        String[] chaMenus = body1.split("메뉴 변경안내")[0].split("href=\"");
        String chaMenu = chaMenus[chaMenus.length - 1].split("\">")[0];
        System.out.println(chaMenu);
        */


        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("menu.css").toString());

        stage.setScene(scene);
        stage.setTitle("Dimibob");
        stage.setResizable(false);

        stage.show();


//        Platform.exit();

    }




    /*
    private String sendGet(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
    }
    */
}

