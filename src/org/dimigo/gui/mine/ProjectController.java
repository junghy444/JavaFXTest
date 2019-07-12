package org.dimigo.gui.mine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import kr.go.neis.api.School;
import kr.go.neis.api.SchoolException;
import kr.go.neis.api.SchoolMenu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Calendar;

import java.net.URL;
import java.util.*;

public class ProjectController implements Initializable {

    @FXML private CheckBox ckbEgg;
    @FXML private CheckBox ckbMilk;
    @FXML private CheckBox ckbBWheat;
    @FXML private CheckBox ckbPeanut;
    @FXML private CheckBox ckbSoybean;
    @FXML private CheckBox ckbWheat;
    @FXML private CheckBox ckbMackerel;
    @FXML private CheckBox ckbCrab;
    @FXML private CheckBox ckbShrimp;
    @FXML private CheckBox ckbPork;
    @FXML private CheckBox ckbPeach;
    @FXML private CheckBox ckbTomato;
    @FXML private CheckBox ckbSulfite;
    @FXML private CheckBox ckbWalnut;
    @FXML private CheckBox ckbChicken;
    @FXML private CheckBox ckbBeaf;
    @FXML private CheckBox ckbSquid;
    @FXML private CheckBox ckbAbalone;
    @FXML private CheckBox ckbMussel;


    @FXML private ListView<String> mondayMenu;
    @FXML private ListView<String> tuesdayMenu;
    @FXML private ListView<String> wednesdayMenu;
    @FXML private ListView<String> thursdayMenu;
    @FXML private ListView<String> fridayMenu;

    @FXML private ListView<String> cautionList;


    // 현재 년, 월, 일, 요일 받기
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int date = cal.get(Calendar.DATE);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    // 현재 월요일 날짜 계산
    int standardDate = (date - dayOfWeek + 1);


    @FXML private WebView wbvChaMe;
    @FXML private WebView wbvEvent;

    @FXML private Button btnSearch;


    // 알레르기 정보를 부르기 위한 Map
    Map<String, List<String>> chkAllergy = new HashMap<>();

    // 일주일 메뉴
    Map<Integer, List<String>> weekMenu = new HashMap<>();

    // 알레르기 메뉴
    List<String> cautionSave = new ArrayList<>();

    @FXML
    void handleSearchAction(ActionEvent event) {
        List<String> list = new ArrayList<>();
        if (ckbEgg.isSelected()) list.add("1");
        if (ckbMilk.isSelected()) list.add("2");
        if (ckbBWheat.isSelected()) list.add("3");
        if (ckbPeanut.isSelected()) list.add("4");
        if (ckbSoybean.isSelected()) list.add("5");
        if (ckbWheat.isSelected()) list.add("6");
        if (ckbMackerel.isSelected()) list.add("7");
        if (ckbCrab.isSelected()) list.add("8");
        if (ckbShrimp.isSelected()) list.add("9");
        if (ckbPork.isSelected()) list.add("10");
        if (ckbPeach.isSelected()) list.add("11");
        if (ckbTomato.isSelected()) list.add("12");
        if (ckbSulfite.isSelected()) list.add("13");
        if (ckbWalnut.isSelected()) list.add("14");
        if (ckbChicken.isSelected()) list.add("15");
        if (ckbBeaf.isSelected()) list.add("16");
        if (ckbSquid.isSelected()) list.add("17");
        if (ckbAbalone.isSelected()) list.add("18");
        if (ckbMussel.isSelected()) list.add("19");

        //System.out.println(list);
        //System.out.println("menu:" + chkAllergy);


        cautionList.getItems().removeAll();
        cautionSave.clear();
        for(String key : chkAllergy.keySet()) {

            //System.out.println(chkAllergy.get(key));
            List<String> value = chkAllergy.get(key);       // [1,2,5,6,13]
            for(String item2 : value) {
                //System.out.println("item:" + item);
                if(list.contains(item2)) {
                    cautionSave.add(key);
                    break;
                }
            }
        }

        ObservableList<String> cautionMenu = FXCollections.observableArrayList(cautionSave);
        cautionList.setItems(cautionMenu);

        /*
        mondayMenu.setCellFactory(param -> new ListCell<String>() {
            static final String ACTIVE_CLASS = "active";
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    getStyleClass().remove(ACTIVE_CLASS);
                } else {
                    setText(item);

                    for(String key : chkAllergy.keySet()) {
                        //System.out.println(chkAllergy.get(key));
                        List<String> value = chkAllergy.get(key);       // [1,2,5,6,13]
                        for(String item2 : value) {
                            //System.out.println("item:" + item);
                            if(list.contains(item2)) {
                                getStyleClass().add(ACTIVE_CLASS);
                                System.out.println("active:" + item2);
                                break;
                            }
                        }
                    }

                }
            }
        });*/
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String body1 = null;
        try {
            body1 = sendGet("https://www.dimigo.hs.kr/index.php?mid=school_notice&category_srl=8169");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String[] wMenus = body1.split("주간식단표")[0].split("href=\"");
//        String wMenu = wMenus[wMenus.length - 1].split("\">")[0];
//        System.out.println(wMenu);

    /*
    String body2 = sendGet(wMenu);
    String menuDown = body2.split("fileList")[1].split("href=\"")[1].split("\">")[0];
    menuDown = menuDown.replace("amp;", "");
        System.out.println(menuDown);
    */

        String[] events = body1.split("이벤트")[0].split("href=\"");
        String event = events[events.length - 1].split("\">")[0];
//        System.out.println(event);

        String[] chaMenus = body1.split("메뉴 변경안내")[0].split("href=\"");
        String chaMenu = chaMenus[chaMenus.length - 1].split("\">")[0];
//        System.out.println(chaMenu);

        WebEngine webEngine = wbvChaMe.getEngine();
        webEngine.load(chaMenu);
        WebEngine webEngine2 = wbvEvent.getEngine();
        webEngine2.load(event);

        SetMenu();
    }


    private void SetMenu() {
        try {
            // 급식 받아오기
            School school = School.find(School.Region.GYEONGGI, "한국디지털미디어고등학교");
            // 한달 급식 받아오기
            List<SchoolMenu> menu = school.getMonthlyMenu(year, month);


            // 점심만 담을 String배열
            String[] lunchMenu = new String[menu.size()];

            // 점심메뉴만 남기고 다른 문자들 삭제
            for(int i = standardDate; i < standardDate + 5; i++) {
                lunchMenu[i] = menu.get(i).toString().
                        replace("[아침]","").replace("[점심]","").replace("[저녁]","")
                        .replaceFirst("\n","").replaceAll("\n\n","");
            }



            for(int i = standardDate; i < standardDate + 5; i++) {
                List<String> dayMenu = new ArrayList<>();
                // 급식 정보가 없을 때
                if (lunchMenu[i].equals("")){
                    dayMenu.add("급식 정보가 없습니다.");
                    weekMenu.put(i+1, dayMenu);
                }
                // 급식 메뉴랑 알레르기 정보랑 분리
                else{
                    String[] arr = lunchMenu[i].split("\n");
                    String arr2;
                    List<String> listAl;
                    String temp;

                    for (int j = 0; j < arr.length; j++){
                        // 알레르기 정보 표시된 메뉴
                        if (arr[j].contains(".")){
                            // 알레르기 정보와 메뉴이름 분리
                            arr2 = arr[j].substring(arr[j].indexOf(".")-1);

                            if(arr[j].contains("(")){
                                arr[j] = arr[j].substring(0, arr[j].indexOf("("));
                            }
                            if(arr[j].contains(".")){
                                arr[j] = arr[j].substring(0, arr[j].indexOf(".")-1);
                            }
                            temp = arr[j];

                            // 숫자를 따로 분리
                            String[] arr3 = arr2.split("\\.");
                            listAl = Arrays.asList(arr3);
                            chkAllergy.put(temp, listAl);
                        }

                        // 알레르기 정보가 없는 메뉴
                        else{
                            if(arr[j].contains("(")){
                                arr[j] = arr[j].substring(0, arr[j].indexOf("("));
                            }
                            temp = arr[j];

                            // 알레르기와 상관없는 값 저장, NullPointerException을 피하기 위해
                            String[] arr3 = {"0","0"};
                            listAl = Arrays.asList(arr3);
                            chkAllergy.put(temp, listAl);
                        }
                        dayMenu.add(temp);
                    }
                    weekMenu.put(i+1, dayMenu);
                }
            }
            BringList();


        } catch (SchoolException e) {
            e.printStackTrace();
        }
    }

    private void BringList() {
        ObservableList<String> mondayList = FXCollections.observableArrayList(weekMenu.get(standardDate +1));
        mondayMenu.setItems(mondayList);
        ObservableList<String> tuesdayList = FXCollections.observableArrayList(weekMenu.get(standardDate +2));
        tuesdayMenu.setItems(tuesdayList);
        ObservableList<String> wednesdayList = FXCollections.observableArrayList(weekMenu.get(standardDate +3));
        wednesdayMenu.setItems(wednesdayList);
        ObservableList<String> thursdayList = FXCollections.observableArrayList(weekMenu.get(standardDate +4));
        thursdayMenu.setItems(thursdayList);
        ObservableList<String> fridayList = FXCollections.observableArrayList(weekMenu.get(standardDate +5));
        fridayMenu.setItems(fridayList);
    }

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

//    @FXML
//    public void handleChangeMenu(ActionEvent event){
//        String webView = "https://www.dimigo.hs.kr/index.php?mid=school_notice&document_srl=5593518";
//        WebEngine webEngine = webView.
//    }

}