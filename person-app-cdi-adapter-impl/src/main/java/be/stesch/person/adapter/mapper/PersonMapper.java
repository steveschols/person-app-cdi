package be.stesch.person.adapter.mapper;

import be.stesch.person.common.web.PersonAppURIFactory;
import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by u420643 on 2/9/2017.
 */
@Mapper
public abstract class PersonMapper {

    public abstract Person mapToDomain(PersonType personType);

    @Mapping(source = "id", target = "uri", qualifiedBy = {UriMapper.class})
    public abstract PersonType mapToResource(Person person);

    @UriMapper
    String getPersonUri(Long id) {
        return PersonAppURIFactory.getPersonUri(id).toString();
    }

}
