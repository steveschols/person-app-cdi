package be.stesch.person.web.servlet;

import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.UpdatePersonBO;
import be.stesch.person.model.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static java.lang.String.valueOf;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@WebServlet("person-app/PersonApp")
@RequestScoped
public class PersonAppServlet extends HttpServlet {

    @Inject
    private CreatePersonBO createPersonBO;
    @Inject
    private UpdatePersonBO updatePersonBO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createPersonBO.setPerson(new Person("Test", "Person", SINGLE));
        Person person = createPersonBO.execute();

        updatePersonBO.setId(valueOf(person.getId()));
        updatePersonBO.setPerson(new Person("John", "Doe", MARRIED));
        updatePersonBO.execute();

        resp.getWriter().print("Check the logs for the marital status change notification!");
    }

}
