package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Repository
@Transactional
public class CardRepositoryImpl implements CardRepository {

    private final EntityManager entityManager;

    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAll() {
        return entityManager.createQuery(
            "SELECT c FROM Card c", Card.class
        ).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findByColor(String color) {
        Color parsedColor;

        try {
            parsedColor = Color.valueOf(
                color.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CardException(
                "Invalid card color", HttpStatus.BAD_REQUEST
            );
        }

        TypedQuery<Card> query = entityManager.createQuery(
            "SELECT c FROM Card c WHERE c.color = :color",
            Card.class
        );

        query.setParameter("color", parsedColor);

        List<Card> cards = query.getResultList();

        if (cards.isEmpty()) {
            throw new CardException(
                "Card not found", HttpStatus.NOT_FOUND
            );
        }

        return cards;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery(
            "SELECT c FROM Card c WHERE c.value = :value",
            Card.class
        );

        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findByType(String type) {
        Type parsedType;

        try {
            parsedType = Type.valueOf(
                type.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CardException(
                "Invalid card type", HttpStatus.BAD_REQUEST
            );
        }

        TypedQuery<Card> query = entityManager.createQuery(
            "SELECT c FROM Card c WHERE c.type = :type",
            Card.class
        );

        query.setParameter("type", parsedType);
        return query.getResultList();
    }

    @Override
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    public Card remove(Long id) {
        Card card = entityManager.find(Card.class, id);

        if (card == null) {
            throw new CardException(
                "Card not found", HttpStatus.NOT_FOUND
            );
        }

        entityManager.remove(card);
        return card;
    }
}
