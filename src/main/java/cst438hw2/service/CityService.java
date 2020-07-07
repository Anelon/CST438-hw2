package cst438hw2.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private WeatherService weatherService;

	final private SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");

	public CityInfo getCityInfo(String cityName) {

		// TODO your code goes here
		System.out.println(cityName);
		List<City> cities = cityRepository.findByName(cityName);
		if(cities.size() == 0) {
			//no city found
			return null;
		} else {
			City city = cities.get(0);
			TempAndTime tempAndTime = weatherService.getTempAndTime(city.getName());
			Country country = countryRepository.findByCode(city.getCountryCode());

			//Convert to F and round
			double temp = Math.round((tempAndTime.getTemp() - 273.15) * 9.0 / 5.0 + 32.0);
			System.out.println("Temp: " + temp);
			//long time = (tempAndTime.getTime() + (long)tempAndTime.getTimezone()) * 1000;
			String timeStr = dateFormat.format(new Date(tempAndTime.getTime() * 1000));
			return new CityInfo(city, country.getName(), temp, timeStr);
		}
	}

}
