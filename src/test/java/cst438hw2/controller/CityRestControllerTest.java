package cst438hw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@BeforeEach
	public void setUpEach() {
		MockitoAnnotations.initMocks(this);
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
		// TODO your code goes here
		City city = new City(1, "TestCity", "TST", "DistrctTest", 10000);
		CityInfo info = new CityInfo(city, "Test Country", 12.2, "12:00 am");
		//set up giving the info
		given(cityService.getCityInfo("TestCity")).willReturn(info);

		MockHttpServletResponse res = mvc.perform(get("/api/cities/TestCity"))
				.andReturn().getResponse();

		assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());

		ObjectContent<CityInfo> infoResult = json.parse(res.getContentAsString());

		CityInfo expected = new CityInfo(city, "Test Country", 12.2, "12:00 am");

		assertThat(infoResult).isEqualTo(expected);
	}

	@Test
	public void noCityInfo() throws Exception {
		// TODO your code goes here
		City city = new City(1, "TestCity", "TST", "DistrctTest", 10000);
		CityInfo info = new CityInfo(city, "Test Country", 12.2, "12:00 am");
		//set up giving the info
		given(cityService.getCityInfo("TestCity")).willReturn(info);

		MockHttpServletResponse res = mvc.perform(get("/api/cities/NotACity"))
				.andReturn().getResponse();

		assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

}
