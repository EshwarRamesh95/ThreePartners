package com.rideestimate;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class JersyGetClient {
	String olaAvailableUrl = "https://book.olacabs.com/data-api/category-availability/p2p";
	String olaFareUrl = "https://book.olacabs.com/data-api/category-fare/p2p";
	String UberUrl = "https://www.uber.com/api/fare-estimate-beta";
	String FastTrackUrl = "https://www.60006000.in/ftib/Service1.svc/getFareop";
	Client client = ClientBuilder.newClient();
	public static void main(String[] args) {
		String olaAvailableUrl = "https://book.olacabs.com/data-api/category-availability/p2p";
		String olaFareUrl = "https://book.olacabs.com/data-api/category-fare/p2p";
		Client client = ClientBuilder.newClient();
		WebTarget targetAvailable = client.target(getURI(olaAvailableUrl));
		String availableResponse = targetAvailable.queryParam("pickupLat", "13.069165")
				.queryParam("pickupLng", "80.1913883").queryParam("pickupMode", "NOW")
				.queryParam("dropLat", "13.0381896").queryParam("dropLng", "80.1565461").queryParam("silent", "true")
				.request(MediaType.APPLICATION_JSON).header("x-fingerprint-id", "442666847").get(String.class);
		System.out.println("availableResponse" + availableResponse);
		WebTarget targetFare = client.target(getURI(olaFareUrl));
		String fareResponse = targetFare.queryParam("pickupLat", "13.069165").queryParam("pickupLng", "80.1913883")
				.queryParam("pickupMode", "NOW").queryParam("dropLat", "13.0381896").queryParam("dropLng", "80.1565461")
				.queryParam("silent", "true").queryParam("suggestPickup", "true").queryParam("shareCategories", "share")
				.request(MediaType.APPLICATION_JSON).header("x-fingerprint-id", "442666847").get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> availableMap = new HashMap<String, Object>();
		try {
			availableMap = mapper.readValue(availableResponse, new TypeReference<Map<String, Object>>() {
			});
			Map<String, Object> fareMap = new HashMap<String, Object>();
			fareMap = mapper.readValue(fareResponse, new TypeReference<Map<String, Object>>() {
			});
			Map availableDataMap = (HashMap) availableMap.get("data");
			Map availablep2pMap = (HashMap) availableDataMap.get("p2p");
			Map fareDataMap = (HashMap) fareMap.get("data");
			Map farep2pMap = (HashMap) fareDataMap.get("p2p");
			Map<String, Object> fareCategoryMap = (HashMap) farep2pMap.get("categories");
			List<Map<String, Object>> availableCategoriesList = (List<Map<String, Object>>) availablep2pMap
					.get("categories");
			for (Map availableCategoryMap : availableCategoriesList) {
				for (String fareKey : fareCategoryMap.keySet()) {
					if (fareKey.equalsIgnoreCase((String) availableCategoryMap.get("id"))) {
						Map<String, String> priceMap = (HashMap<String, String>) fareCategoryMap.get(fareKey);
						availableCategoryMap.put("price", priceMap.get("price"));
					}
				}
			}
			String finalJson = mapper.writeValueAsString(availableCategoriesList);
			System.out.println("finalJson" + finalJson);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> searchOla(Map<String, Object> latLongMap) {
		WebTarget targetAvailable = client.target(getURI(olaAvailableUrl));
		/*String availableResponse = "{\"data\":{\"nextCallAfter\":5,\"p2p\":{\"categories\":[{\"id\":\"auto\",\"displayName\":\"Auto\",\"canRideNow\":true,\"canRideLater\":false,\"needDropLocation\":true,\"eta\":{\"value\":4,\"unit\":\"min\"}},{\"id\":\"micro\",\"displayName\":\"Micro\",\"canRideNow\":true,\"canRideLater\":true,\"needDropLocation\":true,\"eta\":{\"value\":7,\"unit\":\"min\"}},{\"id\":\"mini\",\"displayName\":\"Mini\",\"canRideNow\":true,\"canRideLater\":true,\"needDropLocation\":false,\"eta\":{\"value\":4,\"unit\":\"min\"}},{\"id\":\"share\",\"displayName\":\"Share\",\"canRideNow\":true,\"canRideLater\":false,\"needDropLocation\":true,\"eta\":{\"value\":4,\"unit\":\"min\"}},{\"id\":\"prime\",\"displayName\":\"Prime Sedan\",\"canRideNow\":true,\"canRideLater\":true,\"needDropLocation\":false,\"eta\":{\"value\":5,\"unit\":\"min\"}},{\"id\":\"prime_play\",\"displayName\":\"Prime Play\",\"canRideNow\":true,\"canRideLater\":false,\"needDropLocation\":false,\"eta\":{\"value\":6,\"unit\":\"min\"}},{\"id\":\"suv\",\"displayName\":\"Prime SUV\",\"canRideNow\":true,\"canRideLater\":true,\"needDropLocation\":false,\"eta\":{\"value\":4,\"unit\":\"min\"}},{\"id\":\"exec\",\"displayName\":\"Prime Exec\",\"canRideNow\":true,\"canRideLater\":true,\"needDropLocation\":false,\"eta\":{\"value\":14,\"unit\":\"min\"}}],\"rideLater\":{\"validDays\":6,\"validTime\":60}},\"fareAPIParams\":{\"shareCategories\":\"share\"}},\"error\":null}";
		String fareResponse = "{\"data\":{\"p2p\":{\"categories\":{\"auto\":{\"price\":\"₹177 - ₹195\"},\"exec\":{\"price\":\"₹205 - ₹225\"},\"micro\":{\"price\":\"₹115 - ₹127\"},\"mini\":{\"price\":\"₹148 - ₹162\"},\"prime_play\":{\"price\":\"₹181 - ₹199\"},\"prime\":{\"price\":\"₹166 - ₹182\"},\"suv\":{\"price\":\"₹265 - ₹291\"}}}},\"error\":null}";
*/
		
		  String availableResponse = targetAvailable.queryParam("pickupLat",
		  "13.069165").queryParam("pickupLng", "80.1913883").queryParam("pickupMode",
		  "NOW").queryParam("dropLat", "13.0381896").queryParam("dropLng",
		  "80.1565461").queryParam("silent", "true")
		  .request(MediaType.APPLICATION_JSON).header("x-fingerprint-id", "442666847")
		  .get(String.class);
		 
		System.out.println("availableResponse" + availableResponse);
		List<Map<String, Object>> availableCategoriesList = new ArrayList<Map<String, Object>>();
		WebTarget targetFare = client.target(getURI(olaFareUrl));
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();

		
		  String fareResponse = targetFare.queryParam("pickupLat",
		  "13.069165").queryParam("pickupLng", "80.1913883").queryParam("pickupMode",
		  "NOW").queryParam("dropLat", "13.0381896").queryParam("dropLng",
		  "80.1565461").queryParam("silent", "true").queryParam("suggestPickup",
		  "true").queryParam("shareCategories", "share")
		  .request(MediaType.APPLICATION_JSON).header("x-fingerprint-id", "442666847")
		  .get(String.class);
		 
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> availableMap = new HashMap<String, Object>();
		try {
			availableMap = mapper.readValue(availableResponse, new TypeReference<Map<String, Object>>() {
			});
			Map<String, Object> fareMap = new HashMap<String, Object>();
			fareMap = mapper.readValue(fareResponse, new TypeReference<Map<String, Object>>() {
			});
			Map availableDataMap = (HashMap) availableMap.get("data");
			Map availablep2pMap = (HashMap) availableDataMap.get("p2p");
			Map fareDataMap = (HashMap) fareMap.get("data");
			Map farep2pMap = (HashMap) fareDataMap.get("p2p");
			Map<String, Object> fareCategoryMap = (HashMap) farep2pMap.get("categories");
			availableCategoriesList = (ArrayList<Map<String, Object>>) availablep2pMap.get("categories");
			for (Map<String, Object> availableCategoryMap : availableCategoriesList) {
				for (String fareKey : fareCategoryMap.keySet()) {
					if (fareKey.equalsIgnoreCase((String) availableCategoryMap.get("id"))) {
						Map<String, String> priceMap = (HashMap<String, String>) fareCategoryMap.get(fareKey);
						availableCategoryMap.put("price", priceMap.get("price"));
						finalList.add(availableCategoryMap);
					}
				}
			}
			String finalJson = mapper.writeValueAsString(availableCategoriesList);
			System.out.println("finalJson" + finalJson);
			// availableCategoryMap = mapper.readValue(finalJson, new TypeReference
			// <Map<String,Object>>() {
			// return availableCategoriesList;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalList;
	}

	public Map<String, Object> searchUber(Map<String, Object> latLongMap) {
		WebTarget target = client.target(getURI(UberUrl));
		//String fareResponse = "{\"cityId\":209,\"cityName\":\"Chennai\",\"prices\":[{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"UberGo\",\"vehicleViewId\":2019,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":1000,\"minimum\":\"₹52.50\",\"base\":\"₹31.50\",\"perMinute\":\"₹1.58\",\"uberServicePercent\":0.2,\"cancellation\":\"₹30.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"₹3.15\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Affordable, compact rides\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹8.40\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹30.00\",\"cancellationCap\":\"₹30.00\",\"minDriverCancellation\":\"₹30.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":true,\"waitTimeThresholdSec\":300},\"isCoupledUFP\":true,\"total\":94,\"fareString\":\"₹75-94\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"Pool\",\"vehicleViewId\":10001180,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":10,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":800,\"minimum\":\"₹52.50\",\"base\":\"₹28.35\",\"perMinute\":\"₹1.42\",\"uberServicePercent\":0.2,\"cancellation\":\"₹30.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Shared rides, door to door\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹7.56\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":false,\"cancellationGracePeriodThresholdSec\":60,\"cancellationGracePeriodLimitingEnabled\":true,\"cancellationGracePeriodLimit\":1,\"driverCancelTimeThresholdSec\":120,\"minCancellation\":\"₹3.00\",\"cancellationCap\":\"₹20.00\",\"minDriverCancellation\":\"₹5.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":null,\"waitTimeThresholdSec\":120},\"isCoupledUFP\":true,\"total\":94,\"fareString\":\"₹75-94\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"LANTERN\",\"vehicleViewId\":20008715,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":1000,\"minimum\":\"₹51.00\",\"base\":\"₹0.00\",\"perMinute\":\"₹0.00\",\"uberServicePercent\":0.2,\"cancellation\":\"₹0.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"THE LOW-COST UBER\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹17.00\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹0.00\",\"cancellationCap\":\"₹0.00\",\"minDriverCancellation\":\"₹0.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":null,\"waitTimeThresholdSec\":120},\"isCoupledUFP\":true,\"total\":95,\"fareString\":\"Unavailable\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"Premier\",\"vehicleViewId\":20007175,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":1000,\"minimum\":\"₹63.00\",\"base\":\"₹42.00\",\"perMinute\":\"₹1.58\",\"uberServicePercent\":0.2,\"cancellation\":\"₹30.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"₹3.60\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Comfortable sedans with top-rated drivers\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹10.50\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹30.00\",\"cancellationCap\":\"₹30.00\",\"minDriverCancellation\":\"₹30.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":true,\"waitTimeThresholdSec\":300},\"isCoupledUFP\":true,\"total\":115,\"fareString\":\"₹93-115\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"UberXL\",\"vehicleViewId\":6685,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":1100,\"minimum\":\"₹157.50\",\"base\":\"₹80.00\",\"perMinute\":\"₹2.00\",\"uberServicePercent\":0.2,\"cancellation\":\"₹150.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"₹4.20\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Affordable rides for groups up to 6\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹17.00\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹150.00\",\"cancellationCap\":\"₹150.00\",\"minDriverCancellation\":\"₹150.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":true,\"waitTimeThresholdSec\":300},\"isCoupledUFP\":true,\"total\":207,\"fareString\":\"₹175-207\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"Hire Premier\",\"vehicleViewId\":20008639,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":2000,\"minimum\":\"₹240.50\",\"base\":\"₹83.00\",\"perMinute\":\"₹2.63\",\"uberServicePercent\":0.2,\"cancellation\":\"₹75.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Rentals for local city travel\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹0.00\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":false,\"cancellationGracePeriodThresholdSec\":120,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹5.00\",\"cancellationCap\":\"₹75.00\",\"minDriverCancellation\":\"₹75.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":null,\"waitTimeThresholdSec\":120},\"isCoupledUFP\":true,\"total\":241,\"fareString\":\"₹240-241\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"Hire Go\",\"vehicleViewId\":20008637,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":2000,\"minimum\":\"₹210.00\",\"base\":\"₹52.50\",\"perMinute\":\"₹2.63\",\"uberServicePercent\":0.2,\"cancellation\":\"₹75.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Rentals for local city travel\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹0.00\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹75.00\",\"cancellationCap\":\"₹75.00\",\"minDriverCancellation\":\"₹75.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":null,\"waitTimeThresholdSec\":120},\"isCoupledUFP\":true,\"total\":252,\"fareString\":\"₹210-252\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null},{\"iso2\":\"IN\",\"vehicleViewDisplayName\":\"Premier Intercity\",\"vehicleViewId\":20007973,\"taxiFareInfo\":null,\"fare\":{\"autoOrManual\":\"auto\",\"calculationType\":\"time_plus_distance\",\"speedThresholdMps\":0,\"allowFareEstimate\":true,\"tollsApply\":true,\"gratuityAccepted\":false,\"roundDown\":false,\"flagThreshold\":3500,\"minimum\":\"₹1,050.00\",\"base\":\"₹0.00\",\"perMinute\":\"₹1.60\",\"uberServicePercent\":0.2,\"cancellation\":\"₹50.00\",\"safeRideFee\":0,\"bookingFee\":0,\"serviceFeePercent\":0,\"perWaitMinute\":\"\",\"riderCancellation\":2,\"riderCancellationEnabled\":false,\"perAdditionalPoolChainPickup\":null,\"fareType\":\"time_plus_distance\",\"description\":\"Affordable outstation rides\",\"isDistanceUnitMetric\":true,\"perDistanceUnit\":\"₹11.55\",\"additionalFees\":[]},\"advancedFare\":{\"enrouteFareEnabled\":false,\"enrouteReverseFallbackScalar\":null,\"enroutePerMinute\":\"₹0.00\",\"enroutePerMile\":null,\"remotePickupFareKickoffTimeThresholdSec\":null,\"enrouteFareCap\":\"₹0.00\",\"cancellationV2Enabled\":true,\"cancellationGracePeriodThresholdSec\":300,\"cancellationGracePeriodLimitingEnabled\":false,\"cancellationGracePeriodLimit\":2,\"driverCancelTimeThresholdSec\":310,\"minCancellation\":\"₹50.00\",\"cancellationCap\":\"₹50.00\",\"minDriverCancellation\":\"₹50.00\",\"riderRemotePickupFareKickoffTimeThresholdSec\":null,\"timeAndDistanceBasedCancelEnabled\":false,\"enroutePerDistanceUnit\":\"₹0.00\",\"kickoffThresholdMins\":\"0.00\"},\"tripTime\":{\"chargeForWaitTimeEnabled\":null,\"waitTimeThresholdSec\":120},\"isCoupledUFP\":true,\"total\":1260,\"fareString\":\"₹1,050-1,260\",\"fareType\":\"time_plus_distance\",\"legalDisclaimer\":null}]}";
		
		String fareResponse = target.queryParam("pickupRef",
				latLongMap.get("placeIdPickup")).queryParam("pickupRefType", "google_places")
		  .queryParam("pickupLat", latLongMap.get("latPickUp")).queryParam("pickupLng",
				  latLongMap.get("lngPickUp")).queryParam("destinationRef", latLongMap.get("placeIdDestination"))
		  .queryParam("destinationRefType",
		  "google_places").request(MediaType.APPLICATION_JSON).get(String.class);
		 
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> fareMap = new HashMap<String, Object>();
		try {
			fareMap = mapper.readValue(fareResponse, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		fareMap.remove("cityId");
		fareMap.remove("cityName");
		return fareMap;

	}

	public List<Map<String, Object>> searchFasttrack() {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 15);

		WebTarget target = client.target(getURI(FastTrackUrl));
		//String fareResponse = "{\"Success\":true,  \"Message\":\"Fetched successfully\",  \"data\":{\"value\":[{\"PickupPlace\":\"KARAMBAKKAM.. PORUR..\",\"ViaPlace\":\"\",\"DropPlace\":\"AMBIKA EMPIRE...VADAPALANI\",\"VehType\":\"UNDEFINED\",\"TotalKm\":\"\",\"Fare\":\"\",\"Gst\":\"\",\"Farewidoutgst\":\"\",\"OnlineFare\":\"\",\"Estimatedtime\":\"\",\"VehConfirmimage\":\"UNDEFINEDConfirm\",\"Branch\":\"CHENNAI\",\"FareView\":\"\",\"SplitFare\":\"\",\"VehicleId\":null,\"SchemeStatus\":\"\",\"SchemePlace\":\"\",\"SchemeAmount\":\"|OFF\",\"SchemeDescription\":\"\"},{\"PickupPlace\":\"KARAMBAKKAM.. PORUR..\",\"ViaPlace\":\"\",\"DropPlace\":\"AMBIKA EMPIRE...VADAPALANI\",\"VehType\":\"SEDAN\",\"TotalKm\":\"6\",\"Fare\":\"168\",\"Gst\":\"8\",\"Farewidoutgst\":\"160\",\"OnlineFare\":\"159\",\"Estimatedtime\":\"24\",\"VehConfirmimage\":\"SEDANConfirm\",\"Branch\":\"CHENNAI\",\"FareView\":\"1/n min\",\"SplitFare\":\"Base:100|Ride:10|KM:36|Surge:14|Area:0|Empty:0|Availablity:0|Discount:0|GST:8|Total:168\",\"VehicleId\":\"3380\",\"SchemeStatus\":\"\",\"SchemePlace\":\"\",\"SchemeAmount\":\"|OFF\",\"SchemeDescription\":\"\"},{\"PickupPlace\":\"KARAMBAKKAM.. PORUR..\",\"ViaPlace\":\"\",\"DropPlace\":\"AMBIKA EMPIRE...VADAPALANI\",\"VehType\":\"SUV\",\"TotalKm\":\"6\",\"Fare\":\"237\",\"Gst\":\"11\",\"Farewidoutgst\":\"226\",\"OnlineFare\":\"231\",\"Estimatedtime\":\"24\",\"VehConfirmimage\":\"SUVConfirm\",\"Branch\":\"CHENNAI\",\"FareView\":\"1/n min\",\"SplitFare\":\"Base:150|Ride:14|KM:42|Surge:20|Area:0|Empty:0|Availablity:0|Discount:0|GST:11|Total:237\",\"VehicleId\":\"12491\",\"SchemeStatus\":\"NO\",\"SchemePlace\":\"\",\"SchemeAmount\":\"|OFF\",\"SchemeDescription\":\"\"},{\"PickupPlace\":\"KARAMBAKKAM.. PORUR..\",\"ViaPlace\":\"\",\"DropPlace\":\"AMBIKA EMPIRE...VADAPALANI\",\"VehType\":\"INNOVA\",\"TotalKm\":\"6\",\"Fare\":\"275\",\"Gst\":\"13\",\"Farewidoutgst\":\"262\",\"OnlineFare\":\"272\",\"Estimatedtime\":\"24\",\"VehConfirmimage\":\"INNOVAConfirm\",\"Branch\":\"CHENNAI\",\"FareView\":\"1/n min\",\"SplitFare\":\"Base:175|Ride:18|KM:46|Surge:23|Area:0|Empty:0|Availablity:0|Discount:0|GST:13|Total:275\",\"VehicleId\":\"12491\",\"SchemeStatus\":\"NO\",\"SchemePlace\":\"\",\"SchemeAmount\":\"|OFF\",\"SchemeDescription\":\"\"},{\"PickupPlace\":\"KARAMBAKKAM.. PORUR..\",\"ViaPlace\":\"\",\"DropPlace\":\"AMBIKA EMPIRE...VADAPALANI\",\"VehType\":\"HATCH\",\"TotalKm\":\"6\",\"Fare\":\"159\",\"Gst\":\"8\",\"Farewidoutgst\":\"151\",\"OnlineFare\":\"147\",\"Estimatedtime\":\"24\",\"VehConfirmimage\":\"HATCHBACKConfirm\",\"Branch\":\"CHENNAI\",\"FareView\":\"1/n min\",\"SplitFare\":\"Base:99|Ride:7|KM:32|Surge:13|Area:0|Empty:0|Availablity:0|Discount:0|GST:8|Total:159\",\"VehicleId\":\"5037\",\"SchemeStatus\":\"NO\",\"SchemePlace\":\"\",\"SchemeAmount\":\"|OFF\",\"SchemeDescription\":\"\"}]}}";
		String fareResponse = target.queryParam("PickupLocationLat", "13.0381896")
				.queryParam("PickupLocationLon", "80.15654610000001").queryParam("ViaLocationLat", "0")
				.queryParam("pickupLng", "80.1565461").queryParam("destinationRef", "ChIJmQqEB0lhUjoRJoornnUIh6g")
				.queryParam("ViaLocationLon", "0").queryParam("DropLocationLon", "80.21565099999998")
				.queryParam("Mode", "Premium").queryParam("VehicleType", "tt")
				.queryParam("FareView",
						"undefined|Sedan:1/n%20min:3380|Suv:1/n%20min:12491|Innova:1/n%20min:12491|Hatch:1/n%20min:5037|Maxi%20Cab:1/n%20min:5501")
				.queryParam("phno", "9788855397").queryParam("OtherTrip", "").queryParam("CrGrade", "")
				.queryParam("CrCode", "").queryParam("pickupdate", dt.format(cal.getTime()))
				.queryParam("pickuptime", dt1.format(cal.getTime())).queryParam("DropLocationLat", "13.0102357")
				.queryParam("TripType", "").queryParam("%22%22", "").request(MediaType.APPLICATION_JSON)
				.get(String.class);
		System.out.println("Fast Track Response"+fareResponse);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> fareMap = new HashMap<String, Object>();
		try {
			fareMap = mapper.readValue(fareResponse, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> dataMap = (Map<String, Object>) fareMap.get("data");
		return (List<Map<String, Object>>) dataMap.get("value");
	}

	private static URI getURI(String url) {
		return UriBuilder.fromUri(url).build();
	}
}