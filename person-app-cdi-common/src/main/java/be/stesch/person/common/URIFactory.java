package be.stesch.person.common;

import java.net.URI;

import static javax.ws.rs.core.UriBuilder.fromPath;

/**
 * Created by Steve Schols on 11/10/2016.
 */
public final class URIFactory {

    public static final String PERSON_ID_PATH_PARAM = "personId";

    public static final String PERSON_SERVICES_PATH = "/person-services/persons";
    public static final String PERSON_ID_PATH = "{" + PERSON_ID_PATH_PARAM + "}";

    private URIFactory() {
    }

    public static URI getPersonUri(Long personId) {
        return fromPath(PERSON_SERVICES_PATH)
                .path(PERSON_ID_PATH)
                .build(personId);
    }

}
