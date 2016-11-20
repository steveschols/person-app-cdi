package be.stesch.person.web.rs.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by u420643 on 11/16/2016.
 */
public class JacksonConfigTest {

    private JacksonConfig jacksonConfig = new JacksonConfig();

    @Test
    public void testJacksonConfig() throws Exception {
        ObjectMapper objectMapper = jacksonConfig.getContext(Void.class);

        assertThat(objectMapper.getSerializationConfig().isEnabled(WRITE_DATES_AS_TIMESTAMPS), is(false));
    }

}