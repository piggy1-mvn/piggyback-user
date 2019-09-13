package com.incentives.piggyback.user.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.model.UserCredential;
import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.publisher.UserEventPublisher;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.service.JwtUserDetailsService;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.CommonUtility;
import com.incentives.piggyback.user.util.constants.Constant;
import com.incentives.piggyback.user.util.constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserServiceRepository userServiceRepo;

    @Autowired
    private UserEventPublisher.PubsubOutboundGateway messagingGateway;


    @Autowired
    private JwtUserDetailsService userDetailsService;

    public ResponseEntity<User> createUser(User user) {
        if((user.getUser_password()!=null && user.getUser_type().equals(Roles.USER_TYPE_FB.toString())) || (user.getUser_type()==null && user.getUser_password()==null)){

            return ResponseEntity.badRequest().build();
        }
        Users newUser = userDetailsService.save(user);
        //PUSHING MESSAGES TO GCP
        messagingGateway.sendToPubsub(
                CommonUtility.stringifyEventForPublish(
                        newUser.toString(),
                        Constant.USER_CREATED_EVENT,
                        Calendar.getInstance().getTime().toString(),
                        "",
                        Constant.USER_SOURCE_ID
                ));
        return ResponseEntity.ok(newUser);
    }


    public Iterable<Users> getAllUser() {
        return userServiceRepo.findAll();
    }

    public ResponseEntity<Users> getUserById(Long id) {
        return ResponseEntity.ok(userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id)));
    }

    public ResponseEntity<Users> updateUser(Long id, Users user) {
        userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
            Users updatedUser = userDetailsService.updateUser(user);
            //PUSHING MESSAGES TO GCP
            messagingGateway.sendToPubsub(
                    CommonUtility.stringifyEventForPublish(
                            updatedUser.toString(),
                            Constant.USER_UPDATED_EVENT,
                            Calendar.getInstance().getTime().toString(),
                            "",
                            Constant.USER_SOURCE_ID
                    ));
            return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<Users> deleteUser(Long id) {

        userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
            userServiceRepo.deleteById(id);
            //PUSHING MESSAGES TO GCP
            messagingGateway.sendToPubsub(
                    CommonUtility.stringifyEventForPublish(
                            id.toString(),
                            Constant.USER_DEACTIVATED_EVENT,
                            Calendar.getInstance().getTime().toString(),
                            "",
                            Constant.USER_SOURCE_ID
                    ));
            return ResponseEntity.ok().build();
    }

    public ResponseEntity getAllUserRoles() {
        HashMap map = new HashMap();
        map.put("user_role", Roles.getAllRoles());
        return  ResponseEntity.ok(new Gson().toJson(map));
    }

    public ResponseEntity updateUserInterest(UserInterest userInterest, Long id) {
        Users updatedUser =partialUpdate(userInterest,id);
        //PUSHING MESSAGES TO GCP
        messagingGateway.sendToPubsub(
                CommonUtility.stringifyEventForPublish(
                        id.toString(),
                        Constant.USER_UPDATED_EVENT,
                        Calendar.getInstance().getTime().toString(),
                        "",
                        Constant.USER_SOURCE_ID
                ));
        return ResponseEntity.ok(updatedUser);
    }

    public Users partialUpdate(UserInterest userInterest, Long id) {
        Users user= userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
        user.setUser_interests(userInterest.getUser_interests());
        return userServiceRepo.save(user);
    }

    public ResponseEntity userLogin(UserCredential userCredentials) {
        Users user = userServiceRepo.findByEmail(userCredentials.getEmail());
        if (user.getUser_password().equals(userCredentials.getUser_password())) {
            JsonObject result = new JsonObject();
            result.add("user_role", new JsonPrimitive(user.getUser_role()));
            return ResponseEntity.ok(new Gson().toJson(result));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
