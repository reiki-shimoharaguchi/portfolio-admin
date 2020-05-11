package com.seattleacademy.team20;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Controller
public class SkillController {

  private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

  @RequestMapping(value = "/skillUpload", method = RequestMethod.GET)
  public String skillUpload(Locale locale, Model model) {
    logger.info("Welcome skill! The client locale is {}.", locale);
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }
    ;
    List<SkillCategory> categories = selectSkillCategories();
    uploadSkill(categories);
    return "skillUpload";
  }

  @Autowired
  // 多分database使えるようになるもの
  private JdbcTemplate jdbcTemplate;

  // Listの宣言
  public List<SkillCategory> selectSkillCategories() {
    // sequel proで作ったテーブルからデータを取得する文字列をsqlという変数に入れている
    //    final String sql = "select *,\n" +
    //        "  case category\n" +
    //        "    when \"Front-end\" then 0\n" +
    //        "    when \"Back-end\" then 1\n" +
    //        "    when \"DevOps\" then 2 end as order_column\n" +
    //        "from skill\n" +
    //        "order by order_column asc;";
    // おそらくjdbaTemplateでsqlを実行している
    final String sql = "select * from skill";
    return jdbcTemplate.query(sql, new RowMapper<SkillCategory>() {
      public SkillCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        // SkillCategoryの中にそれぞれのデータを入れている？そのあとRowMapper<Skillcategory>に返却している？
        return new SkillCategory(rs.getInt("id"), rs.getString("name"), rs.getInt("score"),
            rs.getString("label"), rs.getString("category"));
      }
    });
  }

  private FirebaseApp app;

  // SDKの初期化
  public void initialize() throws IOException {
    FileInputStream refreshToken = new FileInputStream(
        "/Users/shimohara/KEY/dev-portfolio-b65ca-firebase-adminsdk-qgja9-ed2d83d4ab.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(refreshToken))
        .setDatabaseUrl("https://dev-portfolio-b65ca.firebaseio.com")
        .build();
    app = FirebaseApp.initializeApp(options, "other");
  }

  public void uploadSkill(List<SkillCategory> categories) {
    // データの保存
    final FirebaseDatabase database = FirebaseDatabase.getInstance(app);
    DatabaseReference ref = database.getReference("skills");

    // Map型のリストを作る。MapはStringで聞かれたものに対し、Object型で返すようにしている

    Map<String, Object> dataMap;
    Map<String, List<SkillCategory>> skillMap = categories.stream()
        .collect(Collectors.groupingBy(SkillCategory::getCategory));
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(skillMap.size());
    //				for (SkillCategory skills : categories) {
    //    for (Map.Entry<String, List<SkillCategory>> entry : skillMap.entrySet()) {
    //					dataMap = new HashMap<>();
    //					dataMap.put("id" , skills.getId());
    //					dataMap.put("category" , skills.getCategory());
    //					dataMap.put("name" , skills.getName());
    //					dataMap.put("label" , skills.getLabel());
    //					dataMap.put("score" , skills.getScore());
    //					dataList.add(dataMap);
    String[] categories2 = { "Front-end", "Back-end", "DevOps" };
    for (String categ : categories2) {
      dataMap = new HashMap<>();
      dataMap.put("category", categ);
      dataMap.put("SKILL", skillMap.get(categ));
      dataList.add(dataMap);
      //      switch (entry.getKey()) {
      //      case "Front-end":
      //        dataList.add(0, dataMap);
      //        break;
      //      case "Back-end":
      //        dataList.add(1, dataMap);
      //        break;
      //      case "DevOps":
      //        dataList.add(2, dataMap);
      //        break;
    }
    //      dataList.add(0, dataMap);
    //      System.out.print("Test");

    ref.setValue(dataList, new DatabaseReference.CompletionListener() {
      @Override
      public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (databaseError != null) {
          System.out.println("Data could be saved" + databaseError.getMessage());
        } else {
          System.out.println("Data save successfully.");
        }
      }
    });
  }
}
