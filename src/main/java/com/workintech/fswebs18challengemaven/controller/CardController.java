package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech")
public class CardController {

    private final CardRepository cardRepository;
    private final CardValidation cardValidation;

    public CardController(CardRepository cardRepository, CardValidation cardValidation) {
        this.cardRepository = cardRepository;
        this.cardValidation = cardValidation;
    }

    @GetMapping("/cards")
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @GetMapping("/cards/byColor/{color}")
    public List<Card> getCardsByColor(@PathVariable String color) {
        cardValidation.checkColor(color);
        return cardRepository.findByColor(Color.valueOf(color.toUpperCase()));
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@RequestBody Card card) {
        cardValidation.validateCard(card);
        return cardRepository.save(card);
    }

    @PutMapping("/cards")
    public Card updateCard(@RequestBody Card card) {
        cardValidation.validateCard(card);
        return cardRepository.update(card);
    }

    @DeleteMapping("/card/{id}")
    public Card deleteCard(@PathVariable Long id) {
        return cardRepository.remove(id);
    }

    @GetMapping("/cards/byValue/{value}")
    public List<Card> getCardsByValue(@PathVariable Integer value) {
        return cardRepository.findByValue(value);
    }

    @GetMapping("/cards/byType/{type}")
    public List<Card> getCardsByType(@PathVariable String type) {
        cardValidation.checkType(type);
        return cardRepository.findByType(Type.valueOf(type.toUpperCase()));
    }
}
