package com.inomind.modelo.springmongo;

import static com.inomind.modelo.springmongo.compose.Compose.user;
import static com.inomind.modelo.springmongo.security.auth.AuthenticationHeaders.JWT_HEADER;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.security.UserCredencial;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(value = "server.port=10001", classes = { Application.class }, webEnvironment = DEFINED_PORT)
public abstract class ApplicationTest {

	private String server;

	private String authentication;

	private static List<Object> toPersist = new ArrayList<Object>();

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public ApplicationTest() {
	    this.server = "http://localhost:10001";
	}
	
	@Before
	public void setUp() throws SQLException {
		toPersist.clear();
		mongoTemplate.getDb().dropDatabase();
	}

	protected void signIn(UserCredencial user) {
	    
		ResponseEntity<Object> response = post("/login")
		    .formParam("email", user.getLogin())
		    .formParam("password", user.getPassword())
		    .expectedStatus(HttpStatus.OK)
		    .getResponse(Object.class);

		authentication = response.getHeaders().getFirst(JWT_HEADER);
	}
	
	protected RequestBuilder getPage(String uri) {
        return new RequestBuilder(server, uri, HttpMethod.GET).header(JWT_HEADER, authentication)
                .queryParam("page", 0)
                .queryParam("size", 50);
    }

	protected void add(Object... objects) {
		for (Object object : objects) {
			toPersist.add(object);
		}
	}

	protected void saveAll() {
	    toPersist.forEach(obj -> mongoTemplate.save(obj));
	    toPersist.clear();
	}

	protected void saveAll(Object... objects) {
	    this.add(objects);
	    this.saveAll();
	}

	protected <T> T findOne(String id, Class<T> clazz) {
	    return mongoTemplate.findOne(query(where("id").is(id)), clazz);
	}

	protected Boolean exists(String id, Class<?> clazz) {
	    return mongoTemplate.exists(query(where("id").is(id)), clazz);
	}
	
	protected void signOut() {
		this.authentication = null;
	}


    public User signIn() {
        signOut();
        return saveAndSignin(user("Gabriel").build());
    }

    private User saveAndSignin(User user) {

        saveAll(user);
        signIn(user);

        return user;
    }
    
	protected RequestBuilder get(String uri) {
		return new RequestBuilder(server, uri, HttpMethod.GET).header(JWT_HEADER, authentication);
	}

	protected RequestBuilder put(String uri) {
		return new RequestBuilder(server, uri, HttpMethod.PUT).header(JWT_HEADER, authentication);
	}

	protected RequestBuilder post(String uri) {
		return new RequestBuilder(server, uri, HttpMethod.POST).header(JWT_HEADER, authentication);
	}

	protected RequestBuilder delete(String uri) {
		return new RequestBuilder(server, uri, HttpMethod.DELETE).header(JWT_HEADER, authentication);
	}

	// With path variables
	protected RequestBuilder get(String uri, Object... path) {
		return get(String.format(uri, path));
	}

	protected RequestBuilder post(String uri, Object... path) {
		return post(String.format(uri, path));
	}

	protected RequestBuilder put(String uri, Object... path) {
		return put(String.format(uri, path));
	}

	protected RequestBuilder delete(String uri, Object... path) {
		return delete(String.format(uri, path));
	}

	protected TestRestTemplate template() {
		return new TestRestTemplate();
	}
}
