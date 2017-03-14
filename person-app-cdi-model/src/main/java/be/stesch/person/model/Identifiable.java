package be.stesch.person.model;

import java.io.Serializable;

/**
 * Created by u420643 on 3/1/2017.
 */
public interface Identifiable<PK extends Serializable> extends Serializable {

    PK getId();

}
