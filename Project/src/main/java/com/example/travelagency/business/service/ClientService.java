package com.example.travelagency.business.service;

import com.example.travelagency.business.Encrypt;
import com.example.travelagency.model.Client;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final Encrypt encrypt;

    public ClientService(ClientRepository clientRepository, Encrypt encrypt) {
        this.clientRepository = clientRepository;
        this.encrypt = encrypt;
    }


    public boolean validateClient(String email, String password) {
        Client client = clientRepository.findClientByEmail(email);
        if (client != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(client.getPassword())) {
                return true;
            }
            else{
                //passwords don't match
                return false;
            }
        } else
        {
            //email not found
            System.out.println(password+"\n"+ client.getPassword());
            return false;
        }

    }

    public boolean findClient(Client client){
        if (clientRepository.findById(client.getId()).isPresent()) {
            return true;
        }
        return false;
    }

    // add/modify client info
    public ResponseEntity<String> addClient(Client client) {
        if (clientRepository.findClientByEmail(client.getEmail()) != null) {
            return new ResponseEntity<>(ResponseMessage.CLIENT_ALREADY_EXISTS.getMessage(), HttpStatus.BAD_REQUEST);
        }

        clientRepository.save(client);

        return new ResponseEntity<>(ResponseMessage.CLIENT_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
    }


    public ResponseEntity<String> modifyClient(Client client) {
        Client oldClient = clientRepository.findById(client.getId()).orElse(null);
        if (oldClient == null) {
            return new ResponseEntity<>(ResponseMessage.CLIENT_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        } else {
            // Update the client fields with the new values
            oldClient.setFullName(client.getFullName());
            oldClient.setEmail(client.getEmail());
            oldClient.setPassword(client.getPassword());
            clientRepository.save(oldClient);
            return new ResponseEntity<>(ResponseMessage.CLIENT_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
        }
    }


}
