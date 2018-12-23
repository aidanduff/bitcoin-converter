package xyz.eala.bitcoinconverter.currencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CurrencyTypeController {
	private List<CurrencyType> currencyTypes = new ArrayList<>();
	private URL url;
    private URLConnection urlConnection;
    private String timeUpdated;
    private DecimalFormat numberFormat = new DecimalFormat("#.00");
    
	

	@RequestMapping(value = "/currentrates", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {				
        try {
        	url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
	        urlConnection = url.openConnection();	
		 
	        try(BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){         
	            StringBuilder temp = new StringBuilder();
	            String inputLine;
	            while ((inputLine = in.readLine()) != null)
	                temp.append(inputLine);
	
	            JSONObject obj = new JSONObject(temp.toString());
	            JSONObject bpi = obj.getJSONObject("bpi");
	            
	            //currencyTypes.clear();
	
	            timeUpdated = obj.getJSONObject("time").getString("updated");
	            String USDRate = numberFormat.format(bpi.getJSONObject("USD").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType("US Dollar", 1.0, USDRate));
	            String GBPRate = numberFormat.format(bpi.getJSONObject("GBP").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType("British Pound", 1.0, GBPRate));
	            String EURRate = numberFormat.format(bpi.getJSONObject("EUR").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType( "Euro", 1.0, EURRate));
	        }

        } catch(IOException e){
            e.printStackTrace();
        }
		
        model.addAttribute("timeUpdated", timeUpdated);
		model.addAttribute("currencyTypes", currencyTypes);
	
		return "index";
	}
	
	@RequestMapping(value = "/currentrates", method = RequestMethod.POST)
	public String getCurrency(HttpServletRequest request, Model model) {
		String unitString = request.getParameter("unit");
		double unit = new Double(unitString);
			
        try {
        	url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
	        urlConnection = url.openConnection();	
		 
	        try(BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){      
	            StringBuilder temp = new StringBuilder();
	            String inputLine;
	            while ((inputLine = in.readLine()) != null)
	                temp.append(inputLine);
	
	            JSONObject obj = new JSONObject(temp.toString());
	            JSONObject bpi = obj.getJSONObject("bpi");
	            
	            currencyTypes.clear();
	
	            timeUpdated = obj.getJSONObject("time").getString("updated");
	            String USDRate = numberFormat.format(bpi.getJSONObject("USD").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType("US Dollar", unit, USDRate));
	            String GBPRate = numberFormat.format(bpi.getJSONObject("GBP").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType("British Pound", unit, GBPRate));
	            String EURRate = numberFormat.format(bpi.getJSONObject("EUR").getDouble("rate_float"));
	            currencyTypes.add(new CurrencyType( "Euro", unit, EURRate));
	        }

        } catch(IOException e){
            e.printStackTrace();
        }
				
        model.addAttribute("timeUpdated", timeUpdated);
		model.addAttribute("currencyTypes", currencyTypes);
	
		return "index";
	}
}
