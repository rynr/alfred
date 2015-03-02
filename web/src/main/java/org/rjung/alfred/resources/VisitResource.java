package org.rjung.alfred.resources;

import org.rjung.alfred.objects.Visit;
import org.rjung.alfred.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import java.awt.print.PrinterException;

@RestController
public class VisitResource {

	private VisitRepository visitRepository;

	@Autowired
	public VisitResource(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	@RequestMapping(value = "/visits", method = RequestMethod.POST)
	public Visit createVisit(@RequestBody Visit visit) throws PrinterException {
		return visitRepository.save(visit);
	}

	@RequestMapping(value = "/visits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Visit> getVisits(Pageable page) {
		return visitRepository.findAll(page);
	}

	@RequestMapping(value = "/visits/:id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Visit getVisit(@PathParam("id") Long id) {
		return visitRepository.findOne(id);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Visit> getVisit(@RequestParam("q") String search, Pageable page) {
		return visitRepository.findUsersByEmail(search, page);
	}

}
