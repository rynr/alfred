package org.rjung.alfred.repositories;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class PeopleRepository {

	private LdapTemplate ldapTemplate;

	@Autowired
	public PeopleRepository(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public Map<String, String> getAllPersonNames() {
		HashMap<String, String> result = new HashMap<String, String>();
		for (Entry<String, String> entry : ldapTemplate.search(
				query().where("objectclass").is("person"),
				new AttributesMapper<Entry<String, String>>() {

					@Override
					public Entry<String, String> mapFromAttributes(
							final Attributes attributes) throws NamingException {
						return new Entry<String, String>() {

							@Override
							public String getKey() {
								return attributes.get("uidNumber").toString();
							}

							@Override
							public String getValue() {
								return attributes.get("cn").toString();
							}

							@Override
							public String setValue(String value) {
								return null;
							}
						};
					}
				})) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
