package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.repository.CardRepositoryImpl;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech")
public class CardController {

    private final CardRepositoryImpl cardRepository;
    private final CardValidation cardValidation;

    public CardController(CardRepositoryImpl cardRepository, CardValidation cardValidation) {
        this.cardRepository = cardRepository;
        this.cardValidation = cardValidation;
    }

    @GetMapping("/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @GetMapping("/cards/byColor/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getCardsByColor(@PathVariable String color) {
        cardValidation.checkColor(color);
        return cardRepository.findByColor(color);
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@RequestBody Card card) {
        return cardRepository.save(card);
    }

    @PutMapping("/cards")
    @ResponseStatus(HttpStatus.OK)
    public Card updateCard(@RequestBody Card card) {
        return cardRepository.update(card);
    }

    @DeleteMapping("/card/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Card deleteCard(@PathVariable Long id) {
        return cardRepository.remove(id);
    }

    @GetMapping("/cards/byValue/{value}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getCardsByValue(@PathVariable Integer value) {
        return cardRepository.findByValue(value);
    }

    @GetMapping("/cards/byType/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getCardsByType(@PathVariable String type) {
        return cardRepository.findByType(type);
    }
}
