package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.util.CardValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CardController {

    private final CardRepository cardRepository;

    @GetMapping({"/cards", "/workintech/cards"})
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @GetMapping({
        "/cards/byColor/{color}",
        "/workintech/cards/byColor/{color}"
    })
    public List<Card> findByColor(
            @PathVariable String color) {

        return cardRepository.findByColor(color);
    }

    @GetMapping({
        "/cards/byValue/{value}",
        "/workintech/cards/byValue/{value}"
    })
    public List<Card> findByValue(
            @PathVariable Integer value) {

        return cardRepository.findByValue(value);
    }

    @GetMapping({
        "/cards/byType/{type}",
        "/workintech/cards/byType/{type}"
    })
    public List<Card> findByType(
            @PathVariable String type) {

        return cardRepository.findByType(type);
    }

    @PostMapping({"/cards", "/workintech/cards"})
    public Card save(@RequestBody Card card) {
        CardValidation.validateNew(card);
        return cardRepository.save(card);
    }

    @PutMapping({
        "/cards",
        "/cards/",
        "/workintech/cards",
        "/workintech/cards/"
    })
    public Card update(@RequestBody Card card) {
        CardValidation.validateUpdate(card);
        return cardRepository.update(card);
    }

    @PutMapping({
        "/cards/{id}",
        "/workintech/cards/{id}"
    })
    public Card updateById(
            @PathVariable Long id,
            @RequestBody Card card) {

        card.setId(id);
        return update(card);
    }

    @DeleteMapping({
        "/cards/{id}",
        "/workintech/cards/{id}",
        "/workintech/card/{id}"
    })
    public Card remove(@PathVariable Long id) {
        return cardRepository.remove(id);
    }
}
