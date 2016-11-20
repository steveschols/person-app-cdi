package be.stesch.person.test.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.lang.reflect.Method;

import static javax.xml.bind.JAXBContext.newInstance;

/**
 * Created by Steve Schols on 11/10/2016.
 */
public final class JaxbTestUtils {

    private JaxbTestUtils() {
    }

    public static <T> byte[] convert(Object obj, Class<T> type) throws Exception {
        JAXBElement<T> jaxbElement = invokeObjectFactoryMethod(obj, type);
        StringWriter writer = marshalJaxbElement(type, jaxbElement);

        return writer.toString().getBytes();
    }

    private static <T> Class<?> getObjectFactoryClass(Class<T> type) throws ClassNotFoundException {
        return Class.forName(type.getPackage().getName() + ".ObjectFactory");
    }

    private static Object getObjectFactoryInstance(Class<?> objectFactoryClass) throws InstantiationException, IllegalAccessException {
        return objectFactoryClass.newInstance();
    }

    @SuppressWarnings("unchecked")
    private static <T> JAXBElement<T> invokeObjectFactoryMethod(Object obj, Class<T> type) throws Exception {
        Class<?> objectFactoryClass = getObjectFactoryClass(type);
        Method objectFactoryMethod = objectFactoryClass.getMethod("create" + type.getSimpleName(), type);

        return (JAXBElement<T>) objectFactoryMethod.invoke(getObjectFactoryInstance(objectFactoryClass), obj);
    }

    private static <T> StringWriter marshalJaxbElement(Class<T> type, JAXBElement<T> jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = newInstance(type);
        Marshaller marshaller = jaxbContext.createMarshaller();

        StringWriter writer = new StringWriter();
        marshaller.marshal(jaxbElement, writer);
        return writer;
    }

}
