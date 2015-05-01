package org.rjung.alfred.resources;

import org.rjung.alfred.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PeopleResource {

	private PeopleRepository peopleRepository;

	@Autowired
	public PeopleResource(PeopleRepository visitRepository) {
		this.peopleRepository = visitRepository;
	}

	@RequestMapping(value = "/people", method = RequestMethod.GET)
	public Map<String, String> getPeople() {
		return peopleRepository.getAllPersonNames();
	}

}
