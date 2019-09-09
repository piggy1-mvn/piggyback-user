package com.incentives.piggyback.user.ServiceImpl;

import com.google.gson.Gson;
import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.publisher.UserEventPublisher;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.CommonUtility;
import com.incentives.piggyback.user.util.constants.Constant;
import com.incentives.piggyback.user.util.constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserServiceRepository userServiceRepo;

    @Autowired
    private UserEventPublisher.PubsubOutboundGateway messagingGateway;

    public ResponseEntity<User> createUser(User user) {
        if((user.getUser_password()!=null && user.getUser_type()== Roles.USER_TYPE_FB.toString())||(user.getUser_type()==null && user.getUser_password()==null)){
            return ResponseEntity.badRequest().build();
        }
        User newUser = userServiceRepo.save(user);
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

    public Iterable<User> getAllUser() {
        return userServiceRepo.findAll();
    }

    public ResponseEntity<User> getUserById(Long id) {
        return ResponseEntity.ok(userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id)));
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
            User updatedUser = userServiceRepo.save(user);
            //PUSHING MESSAGES TO GCP
            messagingGateway.sendToPubsub(
                    CommonUtility.stringifyEventForPublish(
                            updatedUser.toString(),
                            Constant.USER_UPDATED_EVENT,
                            Calendar.getInstance().getTime().toString(),
                            "",
                            Constant.USER_SOURCE_ID
                    ));
            return ResponseEntity.ok(userServiceRepo.save(user));
    }


    public ResponseEntity<User> deleteUser(Long id) {
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
        List<String> roles = Roles.getAllRoles();
        return  ResponseEntity.ok(new Gson().toJson(roles));
    }

    public ResponseEntity updateUserInterest(UserInterest userInterest, Long id) {
        User updatedUser =partialUpdate(userInterest,id);
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

    public User partialUpdate(UserInterest userInterest, Long id) {
        User user= userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
        user.setUser_interests(userInterest.getUser_interests());
        return userServiceRepo.save(user);
    }


}
