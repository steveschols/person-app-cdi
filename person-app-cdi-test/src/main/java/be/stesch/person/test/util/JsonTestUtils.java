package be.stesch.person.test.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Steve Schols on 11/10/2016.
 */
public final class JsonTestUtils {

    private JsonTestUtils() {
    }

    public static byte[] convert(Object obj) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsBytes(obj);
    }

}
