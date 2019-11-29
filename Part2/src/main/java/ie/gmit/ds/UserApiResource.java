package ie.gmit.ds;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource {

    private final Validator validator;

    public UserApiResource(Validator validator) {
        this.validator = validator;
    }

    private HashMap<Integer, User> usersMap = new HashMap<>();


    @GET
    public Response getUsers() {
        return Response.ok(UserDB.getUsers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        User user = UserDB.getUser(id);
        if (user != null)
            return Response.ok(user).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createUser(User user) throws URISyntaxException {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDB.getUser(user.getUserId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e == null) {
            UserDB.createUser(user.getUserId(), user);
            return Response.ok("User ID " + user.getUserId()+"  was sucesfully created")
                    .build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity("User id already exists").build();
    }

    @PUT
    @Path("/{userId}")
    public Response updateUserById(@PathParam("userId") Integer userId, User user) {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDB.getUser(user.getUserId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            user.setUserId(userId);
            UserDB.updateUser(userId, user);
            return Response.ok(user).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}")
    public Response removeEmployeeById(@PathParam("userId") Integer id) {
        User user = UserDB.getUser(id);
        if (user != null) {
            UserDB.removeUser(id);
            return Response.ok().build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity(id + " does not exist ").build();
    }
}