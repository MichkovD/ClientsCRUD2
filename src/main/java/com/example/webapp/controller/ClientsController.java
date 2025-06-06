package com.example.webapp.controller;

import com.example.webapp.models.Client;
import com.example.webapp.models.ClientDto;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.webapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"","/"})
    public String getClients(Model model) {
        var clients = clientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        return "clients/index";
    }

    @GetMapping({"/create"})
    public String createClients(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(
        @Valid @ModelAttribute ClientDto clientDto,
        BindingResult result)
    {
        if (clientRepository.findByEmail(clientDto.getEmail()) != null){
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail(),
                            false, null, null, "Email already exists")
            );
        }
        if (result.hasErrors()) {
            return "clients/create";
        }
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        client.setCreatedAt(new Date());
        clientRepository.save(client);
        return   "redirect:/clients";
    }

    @GetMapping("/edit")
    public String editClients(Model model, @RequestParam int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null){
            return "redirect:/clients";
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setStatus(client.getStatus());

        model.addAttribute("client", client);
        model.addAttribute("clientDto", clientDto);
        return "clients/edit";
    }

    @PostMapping("/edit")
    public String editClient(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null){
            return "redirect:/clients";
        }
        model.addAttribute("client", client);

        if (result.hasErrors()) {
            return "clients/edit";
        }
        //update client in DB
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());

        try { // may be Exc if email is used
            clientRepository.save(client);
        } catch (Exception e) {
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail()
                    , false, null, null, "Email already exists"));
            return "clients/edit";
        }
        return "redirect:/clients";
    }
    @GetMapping("/delete")
    public String deleteClient(@RequestParam int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null){
            clientRepository.delete(client);
        }
        return   "redirect:/clients";
    }
}
