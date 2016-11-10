package be.stesch.person.business;

/**
 * Created by Steve Schols on 11/9/2016.
 */
public interface BusinessObject<T> {

    default T execute() {
        try {
            return onExecute();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    T onExecute() throws Exception;

}
