package com.rideestimate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	List<Map<String, Object>> jsonOla ;
	Map<String, Object> jsonUber ;
	List<Map<String, Object>> jsonFasttrack ;


	@RequestMapping("/hello")
	public String hello() {
		return "Hello!";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView save() {
		LatLong latLong = new LatLong();
		Map<String, Object> latLongMap = latLong.getLatLong();
		JersyGetClient jersyGetClient = new JersyGetClient();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("result");
		
		new Thread(() -> {
			System.out.println("Running Uber!!!");
			jsonUber = jersyGetClient.searchUber(latLongMap);
			modelAndView.addObject("jsonUber", jsonUber);		
		}).start();
		//jsonUber = "dummy";
		new Thread(() -> {
			System.out.println("Running Ola!!!");
		    jsonOla = jersyGetClient.searchOla(latLongMap);
			modelAndView.addObject("jsonOla", jsonOla);
		}).start();
		
		new Thread(() -> {
			System.out.println("Running Fasttrack!!!");
			jsonFasttrack = jersyGetClient.searchFasttrack();
			modelAndView.addObject("jsonFasttrack", jsonFasttrack);
		}).start();
		
		try {
			for (int retryCount = 1; retryCount <= 5; retryCount++) {
				Thread.sleep(3000);
				System.out.println("RetryCount"+retryCount+ " \n jsonUber "+jsonUber+"\n jsonOla "+jsonOla+"\n jsonFasttrack "+jsonFasttrack);
				if(!jsonUber.isEmpty() && !jsonOla.isEmpty() && !jsonFasttrack.isEmpty()) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	/*@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String save() {
		JersyGetClient jersyGetClient = new JersyGetClient();
		modelAndView.setViewName("user-data");
		return jersyGetClient.searchOla();
	}*/
}
