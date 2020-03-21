package com.example.COVID19tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.COVID19tracker.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private final RestTemplate restTemplate;

	public CoronaVirusDataService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private static String VIRUS_CONFIRMED_CASE_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private static String VIRUS_DEATH_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	private static String VIRUS_RECOVERED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

	private ArrayList<LocationStats> allStats = new ArrayList<>();

	public ArrayList<LocationStats> getAllStats() {
		return allStats;
	}

	private ArrayList<LocationStats> allconfirmedcases = new ArrayList<>();
	private ArrayList<LocationStats> allDeathcases = new ArrayList<>();
	private ArrayList<LocationStats> allrecorvedcases = new ArrayList<>();

	@PostConstruct
	@Scheduled(cron = "* 30 * * * * ")
	public void fetchVirusData() throws IOException, InterruptedException {
		ArrayList<LocationStats> newStats = new ArrayList<>();
		String response = this.restTemplate.getForObject(URI.create(VIRUS_DATA_URL), String.class);
		StringReader in = new StringReader(response);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationState = new LocationStats();
			locationState.setState(record.get("Province/State"));
			locationState.setCountry(record.get("Country/Region"));
			int totalcasetoday = Integer.parseInt(record.get(record.size() - 1));
			int predaycases = Integer.parseInt(record.get(record.size() - 2));
			locationState.setLatestTotalCases(totalcasetoday);
			locationState.setDiffcases(totalcasetoday - predaycases);
			locationState.setLan(record.get("Long"));
			locationState.setLat(record.get("Lat"));

			newStats.add(locationState);
		}
		this.allStats = newStats;
	}

	@PostConstruct
	@Scheduled(cron = "* 39 * * * * ")
	public void fetchVirusConfirmcaseData() throws IOException, InterruptedException {
		ArrayList<LocationStats> newStats = new ArrayList<>();
		String response = this.restTemplate.getForObject(URI.create(VIRUS_CONFIRMED_CASE_URL), String.class);
		StringReader in = new StringReader(response);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationState = new LocationStats();
			locationState.setState(record.get("Province/State"));
			locationState.setCountry(record.get("Country/Region"));
			int totalcasetoday = Integer.parseInt(record.get(record.size() - 1));
			int predaycases = Integer.parseInt(record.get(record.size() - 2));
			locationState.setLatestTotalCases(totalcasetoday);
			locationState.setDiffcases(totalcasetoday - predaycases);
			locationState.setLan(record.get("Long"));
			locationState.setLat(record.get("Lat"));

			newStats.add(locationState);
		}
		this.allconfirmedcases = newStats;
	}

	public ArrayList<LocationStats> getAllconfirmedcases() {
		return allconfirmedcases;
	}

	@PostConstruct
	@Scheduled(cron = "* 33 * * * * ")
	public void fetchVirusRecoverdcaseData() throws IOException, InterruptedException {
		ArrayList<LocationStats> newStats = new ArrayList<>();
		String response = this.restTemplate.getForObject(URI.create(VIRUS_RECOVERED_CASES_URL), String.class);

		StringReader in = new StringReader(response);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationState = new LocationStats();
			locationState.setState(record.get("Province/State"));
			locationState.setCountry(record.get("Country/Region"));
			int totalcasetoday = Integer.parseInt(record.get(record.size() - 1));
			int predaycases = Integer.parseInt(record.get(record.size() - 2));
			locationState.setLatestTotalCases(totalcasetoday);
			locationState.setDiffcases(totalcasetoday - predaycases);
			locationState.setLan(record.get("Long"));
			locationState.setLat(record.get("Lat"));

			newStats.add(locationState);
		}
		this.allrecorvedcases = newStats;
	}

	@PostConstruct
	@Scheduled(cron = "* 36 * * * * ")
	public void fetchVirusDeathscaseData() throws IOException, InterruptedException {
		ArrayList<LocationStats> newStats = new ArrayList<>();
		String response = this.restTemplate.getForObject(URI.create(VIRUS_DEATH_CASES_URL), String.class);

		StringReader in = new StringReader(response);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationState = new LocationStats();
			locationState.setState(record.get("Province/State"));
			locationState.setCountry(record.get("Country/Region"));
			int totalcasetoday = Integer.parseInt(record.get(record.size() - 1));
			int predaycases = Integer.parseInt(record.get(record.size() - 2));
			locationState.setLatestTotalCases(totalcasetoday);
			locationState.setDiffcases(totalcasetoday - predaycases);
			locationState.setLan(record.get("Long"));
			locationState.setLat(record.get("Lat"));

			newStats.add(locationState);
		}
		this.allDeathcases = newStats;
	}

	public ArrayList<LocationStats> getAllDeathcases() {
		return allDeathcases;
	}

	public ArrayList<LocationStats> getAllrecorvedcases() {
		return allrecorvedcases;
	}

}
