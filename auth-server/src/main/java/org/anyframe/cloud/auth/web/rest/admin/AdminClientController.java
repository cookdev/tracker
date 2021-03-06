package org.anyframe.cloud.auth.web.rest.admin;

import com.codahale.metrics.annotation.Timed;
import org.anyframe.cloud.auth.domain.Client;
import org.anyframe.cloud.auth.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminClientController {

	private final Logger log = LoggerFactory.getLogger(AdminClientController.class);

	@Autowired
	private ClientRepository clientRepository;

	/**
	 * POST /clients -> create or update clients.
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	public ResponseEntity<?> createClients(@RequestBody List<Client> clients) {
		for (Client client : clients) {
			Client clietFromDB = clientRepository.findOne(client.getClientId());
			if (null != clietFromDB) {
				client.setClientGrantDefaultUserAuthorities(clietFromDB
						.getClientGrantDefaultUserAuthorities());
			}
		}
		clientRepository.save(clients);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * DELETE /clients -> delete clients.
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<String> deleteClients(
			@RequestParam Map<String, String> params) {
		String[] clientIds = params.get("clientIds").split(",");
		for (String clientId : clientIds) {
			if (null != clientId && clientId.length() > 0) {
				clientRepository.delete(clientId);
			}
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * GET /clients -> get all clients.
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Client>> getClients() {
		List<Client> clients = clientRepository.findAll();
		if (clients.size() == 0) {
			clients.add(new Client());
		}
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
	}
}
