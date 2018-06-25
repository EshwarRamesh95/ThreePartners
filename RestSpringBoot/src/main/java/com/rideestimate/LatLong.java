package com.rideestimate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LatLong {
	Client client = ClientBuilder.newClient();
	String autoCompleteUrl = "https://bookingwidget.olacabs.com/widget-api/search/autocomplete";
	String placeUrl = "https://bookingwidget.olacabs.com/widget-api/search/place";

	public static void main(String[] args) {
		String autoCompleteUrl = "https://bookingwidget.olacabs.com/widget-api/search/autocomplete";
		String placeUrl = "https://bookingwidget.olacabs.com/widget-api/search/place";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getURI(autoCompleteUrl));
		String pickUpResponse = target.queryParam("query", "porur")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "PICKUP")
				.request(MediaType.APPLICATION_JSON).get(String.class);
		String destinationResponse = target.queryParam("query", "koyambedu")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "DROP")
				.request(MediaType.APPLICATION_JSON).get(String.class);
		target = client.target(getURI(placeUrl));
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> pickUpMap = new HashMap<String, Object>();
		Map<String, Object> destinationMap = new HashMap<String, Object>();
		Map<String, Object> placeMap = new HashMap<String, Object>();
		Map<String, Object> placeMap2 = new HashMap<String, Object>();
		Map<String,Object> finalMap = new HashMap<String,Object>();
		try {
			pickUpMap = mapper.readValue(pickUpResponse, new TypeReference<Map<String, Object>>() {
			});
			destinationMap = mapper.readValue(destinationResponse, new TypeReference<Map<String, Object>>() {
			});
		/*PlaceId PickUP*/			
		List<Map<String, Object>> dataMap = (List<Map<String, Object>>) pickUpMap.get("data");
		List<Map<String, Object>> listMap = (List<Map<String, Object>>) dataMap.get(0).get("list");
		finalMap.put("placeIdPickup", (String) listMap.get(0).get("placeId")) ;
		/*PlaceId Destination*/
		List<Map<String, Object>> dataDestMap = (List<Map<String, Object>>) destinationMap.get("data");
		List<Map<String, Object>> listDestMap = (List<Map<String, Object>>) dataDestMap.get(0).get("list");
		finalMap.put("placeIdDestination", (String) listDestMap.get(0).get("placeId")) ;
		/*Latitude Longitude PickUP*/
		String placePickupResponse = target.queryParam("placeId", finalMap.get("placeIdPickup"))
				.request(MediaType.APPLICATION_JSON).get(String.class);
		placeMap = mapper.readValue(placePickupResponse, new TypeReference<Map<String, Object>>() {
		});
		Map<String, Object> placePickupMap = (Map<String, Object>) placeMap.get("data");
		finalMap.put("latPickUp",  placePickupMap.get("lat"));
		finalMap.put("lngPickUp",  placePickupMap.get("lng"));
		/*Latitude Longitude Destination*/
		String placeDestResponse = target.queryParam("placeId", finalMap.get("placeIdDestination"))
				.request(MediaType.APPLICATION_JSON).get(String.class);
		placeMap2 = mapper.readValue(placeDestResponse, new TypeReference<Map<String, Object>>() {
		});
		Map<String, Object> placeDestMap = (Map<String, Object>) placeMap2.get("data");
		finalMap.put("latDest",  placeDestMap.get("lat"));
		finalMap.put("lngDest",  placeDestMap.get("lng"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		/*String destinationResponse = target.queryParam("query", "vadapalani")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "DROP")
				.request(MediaType.APPLICATION_JSON).get(String.class);*/
		System.out.println("pickUpMapRef" + finalMap);
	}
	public Map<String, Object> getLatLong() {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getURI(autoCompleteUrl));
		String pickUpResponse = target.queryParam("query", "maduravoyal")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "PICKUP")
				.request(MediaType.APPLICATION_JSON).get(String.class);
		String destinationResponse = target.queryParam("query", "valasaravakkam")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "DROP")
				.request(MediaType.APPLICATION_JSON).get(String.class);
		target = client.target(getURI(placeUrl));
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> pickUpMap = new HashMap<String, Object>();
		Map<String, Object> destinationMap = new HashMap<String, Object>();
		Map<String, Object> placeMap = new HashMap<String, Object>();
		Map<String, Object> placeMap2 = new HashMap<String, Object>();
		Map<String,Object> finalMap = new HashMap<String,Object>();
		try {
			pickUpMap = mapper.readValue(pickUpResponse, new TypeReference<Map<String, Object>>() {
			});
			destinationMap = mapper.readValue(destinationResponse, new TypeReference<Map<String, Object>>() {
			});
		/*PlaceId PickUP*/			
		List<Map<String, Object>> dataMap = (List<Map<String, Object>>) pickUpMap.get("data");
		List<Map<String, Object>> listMap = (List<Map<String, Object>>) dataMap.get(0).get("list");
		finalMap.put("placeIdPickup", (String) listMap.get(0).get("placeId")) ;
		/*PlaceId Destination*/
		List<Map<String, Object>> dataDestMap = (List<Map<String, Object>>) destinationMap.get("data");
		List<Map<String, Object>> listDestMap = (List<Map<String, Object>>) dataDestMap.get(0).get("list");
		finalMap.put("placeIdDestination", (String) listDestMap.get(0).get("placeId")) ;
		/*Latitude Longitude PickUP*/
		String placePickupResponse = target.queryParam("placeId", finalMap.get("placeIdPickup"))
				.request(MediaType.APPLICATION_JSON).get(String.class);
		placeMap = mapper.readValue(placePickupResponse, new TypeReference<Map<String, Object>>() {
		});
		Map<String, Object> placePickupMap = (Map<String, Object>) placeMap.get("data");
		finalMap.put("latPickUp",  placePickupMap.get("lat"));
		finalMap.put("lngPickUp",  placePickupMap.get("lng"));
		/*Latitude Longitude Destination*/
		String placeDestResponse = target.queryParam("placeId", finalMap.get("placeIdDestination"))
				.request(MediaType.APPLICATION_JSON).get(String.class);
		placeMap2 = mapper.readValue(placeDestResponse, new TypeReference<Map<String, Object>>() {
		});
		Map<String, Object> placeDestMap = (Map<String, Object>) placeMap2.get("data");
		finalMap.put("latDest",  placeDestMap.get("lat"));
		finalMap.put("lngDest",  placeDestMap.get("lng"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		/*String destinationResponse = target.queryParam("query", "vadapalani")
				.queryParam("lat", "").queryParam("lng", "")
				.queryParam("serviceType", "undefined").queryParam("tag", "DROP")
				.request(MediaType.APPLICATION_JSON).get(String.class);*/
		System.out.println("Latitude Longitude Map"+finalMap);
		return finalMap;
	}
	
	private static URI getURI(String url) {
		return UriBuilder.fromUri(url).build();
	}
}
