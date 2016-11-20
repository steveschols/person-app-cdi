package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;

/**
 * Created by Steve Schols on 11/9/2016.
 */
public interface BusinessObject<T> {

    default T execute() {
        return onExecute();
    }

    T onExecute() throws BusinessException;

}
