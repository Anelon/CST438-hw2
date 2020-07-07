package cst438hw2.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	@Test
	public void contextLoads() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testCityFound() throws Exception {
		// TODO 
		Country oneCountry = new Country("AUS", "Australia");
		City oneCity1 = new City(1, "OneCity", "AUS", "OneDistrict", 100);
		List<City> oneList = new ArrayList<City>();
		oneList.add(oneCity1);
		given(cityRepository.findByName("OneCity")).willReturn(oneList);
		given(countryRepository.findByCode("AUS")).willReturn(oneCountry);

		//temp should end up 82.0 and timeStr should be "11:08 AM"
		TempAndTime tempAndTime = new TempAndTime(301.09, 1594058917,-25200);
		double expectedTemp = Math.round((tempAndTime.getTemp() - 273.15) * 9.0 / 5.0 + 32.0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
		String expectedTime = dateFormat.format(new Date(tempAndTime.getTime() * 1000));

		given(weatherService.getTempAndTime("OneCity")).willReturn(tempAndTime);

		//actually call
		CityInfo res = cityService.getCityInfo("OneCity");

		//check results
		assertEquals(res.getId(), oneCity1.getId());
		assertEquals(res.getName(), oneCity1.getName());
		assertEquals(res.getCountryCode(), oneCity1.getCountryCode());
		assertEquals(res.getCountryName(), "Australia");
		assertEquals(res.getDistrict(), oneCity1.getDistrict());
		assertEquals(res.getPopulation(), oneCity1.getPopulation());
		assertEquals(res.getTemp(), expectedTemp);
		assertEquals(res.getTime(), expectedTime);
	}
	
	@Test 
	public void  testCityNotFound() {
		// TODO
		Country oneCountry = new Country("AUS", "Australia");
		City oneCity1 = new City(1, "OneCity", "AUS", "OneDistrict", 100);
		List<City> oneList = new ArrayList<City>();
		oneList.add(oneCity1);
		given(cityRepository.findByName("OneCity")).willReturn(oneList);
		given(countryRepository.findByCode("AUS")).willReturn(oneCountry);

		//temp should end up 82.0 and timeStr should be "11:08 AM"
		TempAndTime tempAndTime = new TempAndTime(301.09, 1594058917,-25200);
		double expectedTemp = Math.round((tempAndTime.getTemp() - 273.15) * 9.0 / 5.0 + 32.0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
		String expectedTime = dateFormat.format(new Date(tempAndTime.getTime() * 1000));

		given(weatherService.getTempAndTime("OneCity")).willReturn(tempAndTime);

		//actually call
		CityInfo res = cityService.getCityInfo("NonCity");

		//check results
    assertEquals(res, null);
	}
	
	@Test 
	public void  testCityMultiple() {
		// TODO
		Country twoCountry = new Country("AUS", "Australia");
		City twoCity1 = new City(1, "TwoCity", "AUS", "TwoDistrict", 100);
		City twoCity2 = new City(2, "TwoCity", "USA", "TwoDistrict", 200);
		List<City> twoList = new ArrayList<City>();
		twoList.add(twoCity1);
		twoList.add(twoCity2);
		given(cityRepository.findByName("TwoCity")).willReturn(twoList);
		given(countryRepository.findByCode("AUS")).willReturn(twoCountry);

		//temp should end up 82.0 and timeStr should be "11:08 AM"
		TempAndTime tempAndTime = new TempAndTime(301.09, 1594058917,-25200);
		double expectedTemp = Math.round((tempAndTime.getTemp() - 273.15) * 9.0 / 5.0 + 32.0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
		String expectedTime = dateFormat.format(new Date(tempAndTime.getTime() * 1000));

		given(weatherService.getTempAndTime("TwoCity")).willReturn(tempAndTime);

		CityInfo res = cityService.getCityInfo("TwoCity");
		assertEquals(res.getId(), twoCity1.getId());
		assertEquals(res.getName(), twoCity1.getName());
		assertEquals(res.getCountryCode(), twoCity1.getCountryCode());
		assertEquals(res.getCountryName(), "Australia");
		assertEquals(res.getDistrict(), twoCity1.getDistrict());
		assertEquals(res.getPopulation(), twoCity1.getPopulation());
		assertEquals(res.getTemp(), expectedTemp);
		assertEquals(res.getTime(), expectedTime);
	}

}
