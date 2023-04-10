package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.rest.request.UserCreateRequest;

/**
 * User service interface
 */
public interface UserService {

    /**
     * Creates a user - used for registering the users to the system
     *
     * @param request
     */
    void create(UserCreateRequest request);

//    void update();
//    void delete();
}
