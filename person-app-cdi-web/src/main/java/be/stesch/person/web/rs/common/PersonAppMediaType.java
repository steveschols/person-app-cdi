package be.stesch.person.web.rs.common;

/**
 * Created by u420643 on 11/14/2016.
 */
public final class PersonAppMediaType {

    private PersonAppMediaType() {
    }

    private static final String APPLICATION_PREFIX = "application/";
    private static final String PERSONAL_FACET = "prs.";

    private static final String V1_SUFFIX = ".v1";

    private static final String XML_SUFFIX = "+xml";
    private static final String JSON_SUFFIX = "+json";

    private static final String PERSON_V1 = APPLICATION_PREFIX + PERSONAL_FACET + "person" + V1_SUFFIX;
    private static final String BUSINESS_EXCEPTION_V1 = APPLICATION_PREFIX + PERSONAL_FACET + "business-exception" + V1_SUFFIX;

    public static final String PERSON_V1_XML = PERSON_V1 + XML_SUFFIX;
    public static final String PERSON_V1_JSON = PERSON_V1 + JSON_SUFFIX;
    public static final String BUSINESS_EXCEPTION_V1_XML = BUSINESS_EXCEPTION_V1 + XML_SUFFIX;
    public static final String BUSINESS_EXCEPTION_V1_JSON = BUSINESS_EXCEPTION_V1 + JSON_SUFFIX;

}
