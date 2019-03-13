package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Country;
import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.CountriesRepository;
import com.storycom.repository.StoriesRepository;
import com.storycom.repository.UsersRepository;
import java.sql.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.jdbc.support.*;

@Slf4j
@Controller
@RequestMapping(value = "/test")
public class TestController extends Base
{

  @Autowired
  private StoriesRepository storiesRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private CountriesRepository countriesRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping(value = "/design")
  public String designTest()
  {
    log.debug("Design test");

    return "test/designTest";
  }

  @GetMapping(value = "/design/story")
  public String designStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    log.debug("Design story");

    Story story = storiesRepository.findByStoryId(storyId);

    model.addAttribute("story", story);

    return "test/designTest";
  }

  @ResponseBody
  @GetMapping(value = "/story/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Story storyById(@PathVariable("id") Integer storyId)
  {
    log.debug("storyById() begin --");
    return storiesRepository.findByStoryId(storyId);
  }

  @ResponseBody
  @GetMapping(value = "/story", produces = MediaType.APPLICATION_JSON_VALUE)
  public Story storyByIdParam(@RequestParam(name = "id") Integer storyId)
  {
    log.debug("storyByIdParam() begin --");
    return storiesRepository.findByStoryId(storyId);
  }

  @ResponseBody
  @GetMapping(value = "/encoding", produces = MediaType.APPLICATION_JSON_VALUE)
  public String testEncoding()
  {
    log.debug("testEncoding() begin --");

    String word = "Здравейте, приятели";

    log.debug("word: " + word);

    return word;
  }

  @ResponseBody
  @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getUser()
  {
    return usersRepository.findByUserId(getStoryUser().getUserId());
  }

  @ResponseBody
  @GetMapping(value = "/encodePass/{pass}")
  public String encodePass(@PathVariable(value = "pass") String password)
  {
    return passwordEncoder.encode(password);
  }

  @ResponseBody
  @GetMapping(value = "/password/{userId}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean isPasswordMatch(@PathVariable("userId") Integer userId, @PathVariable("password") String password)
  {
    log.debug("userId: " + userId);
    log.debug("password: " + password);

    String sql = "SELECT U.PASSWORD FROM USERS U WHERE U.USER_ID = ?";

    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);

    String dbPassword = "-1";

    while (rs.next())
    {
      dbPassword = rs.getString(1);
    }

    log.debug("dbPassword: " + dbPassword);

    return passwordEncoder.matches(password, dbPassword);
  }

  @GetMapping(value = "/countries")
  public String countries(Model model)
  {

    List<Country> countries = countriesRepository.findAll();

    model.addAttribute("countries", countries);

    return "test/selectTest";
  }

  @ResponseBody
  @GetMapping(value = "/tableTest2/{text}", produces = "application/json")
  public int tableTest3(@PathVariable("text") String text)
  {
    String sql = "INSERT INTO test(text) VALUES (?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    PreparedStatementCreator preparedStatementCreator = (Connection connection) ->
    {
      PreparedStatement ps = connection.prepareStatement(sql, new String[]
      {
        "test_id"
      });
      
      ps.setString(1, text);
      
      return ps;
    };
    jdbcTemplate.update(preparedStatementCreator, keyHolder);

    return keyHolder.getKey().intValue();
  }

  @ResponseBody
  @GetMapping(value = "/tableTest/{text}", produces = "application/json")
  public Integer tableTest2(@PathVariable("text") String text)
  {
    List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER));

    String sqlInsert = "INSERT INTO test(text) VALUES (?) RETURNING test_id INTO ?";

    Map<String, Object> call = jdbcTemplate.call(new CallableStatementCreator()
    {
      public CallableStatement createCallableStatement(Connection connection) throws SQLException
      {
        CallableStatement cs = connection.prepareCall(sqlInsert);
        cs.setString(1, text);
        cs.registerOutParameter(2, Types.INTEGER);
        return cs;
      }
    }, parameters);

    System.out.println(call);

    return 1;
  }

  @ResponseBody
  @GetMapping(value = "/kkk/{text}", produces = "application/json")
  public String tableTest(@PathVariable("text") String text)
  {
    String sqlInsert = "INSERT INTO test(text) VALUES (?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator()
    {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException
      {
        PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, text);
        return ps;
      }
    }, keyHolder);

    //Понеже се връща rowid на новия запис ще го използваме, за да вземем стойността на customer_id
    String testId = keyHolder.getKeys().get("test_id").toString();
    log.debug("testId = " + testId);
//    String newRowid = keyHolder.getKeys().get("test_id").toString();
//    log.debug("newRowid = " + newRowid);
//    SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT test_id FROM test WHERE rowid = ?", newRowid);
//    rs.next();
//    log.debug("testId = " + rs.getInt("test_id"));
    return testId;
  }
}
