package com.egs.bankservice.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egs.bankservice.controller.model.card.AddCardRequest;
import com.egs.bankservice.controller.model.card.AddCardResponse;
import com.egs.bankservice.controller.model.card.CardResponse;
import com.egs.bankservice.controller.model.card.SetAuthMethodRequest;
import com.egs.bankservice.service.cards.CardsService;
import com.egs.bankservice.service.cards.CardsServiceImpl;

@RestController
@RequestMapping("api/cards")
public class CardsController {

    private final CardsService cardsService;

    @Autowired
    public CardsController(CardsServiceImpl cardsService) {
        this.cardsService = cardsService;
    }

    @PostMapping("add")
    public AddCardResponse addCard(@RequestBody AddCardRequest addCardRequest) {
        return cardsService.addCard(addCardRequest);
    }

    @GetMapping("get/{id}")
    public CardResponse getCardById(@PathVariable(value = "id") long id) {
        return cardsService.getCardById(id);
    }

    @GetMapping("getByCardNumber")
    public CardResponse getCardByNumber(@RequestParam(value = "cardNumber") String cardNumber) {
        return cardsService.getCardByNumber(cardNumber);
    }

    @DeleteMapping("delete/{id}")
    public void deleteCard(@PathVariable(value = "id") long id) {
        cardsService.deleteCard(id);
    }

    @PostMapping("setAuthMethod")
    public void setAuthMethodByCardNumber(@RequestBody SetAuthMethodRequest setAuthMethodRequest) {
        cardsService.setAuthMethodByCardNumber(setAuthMethodRequest);
    }

    @PostMapping("unblock")
    public void unblockCard(@RequestParam(value = "cardNumber") String cardNumber) {
        cardsService.unblockCard(cardNumber);
    }
}
