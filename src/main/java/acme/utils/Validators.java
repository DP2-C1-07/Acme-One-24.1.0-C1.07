package acme.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Validators {
	
	@Autowired
	private ValidatorRepository repository;
	
	public Boolean moneyValidator(String currency) {
		String acceptedCurrencies = this.repository.findAcceptedCurrencies();
		List<String> acceptedCurrencyList = Arrays.asList(acceptedCurrencies.split(",\\s*"));
		
		return acceptedCurrencyList.stream().anyMatch(currency::equals);
	}
}
