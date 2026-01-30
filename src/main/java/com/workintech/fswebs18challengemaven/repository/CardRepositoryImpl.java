package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private final EntityManager entityManager;

    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        final Color enumColor;
        try {
            enumColor = Color.valueOf(color.toUpperCase());
        } catch (Exception e) {
            throw new CardException("Color not found", HttpStatus.NOT_FOUND);
        }

        TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.color = :color", Card.class
        );
        query.setParameter("color", enumColor);

        List<Card> list = query.getResultList();
        if (list.isEmpty()) {
            throw new CardException("Color not found", HttpStatus.NOT_FOUND);
        }
        return list;
    }

    @Override
    public List<Card> findAll() {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c", Card.class);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.value = :value", Card.class
        );
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        final Type enumType;
        try {
            enumType = Type.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new CardException("Type not found", HttpStatus.NOT_FOUND);
        }

        TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.type = :type", Card.class
        );
        query.setParameter("type", enumType);

        return query.getResultList();
    }

    @Override
    public Card findById(Long id) {
        return entityManager.find(Card.class, id);
    }

    @Override
    @Transactional
    public Card update(Card card) {
        if (card.getId() == null) {
            throw new CardException("Id is required for update", HttpStatus.BAD_REQUEST);
        }
        return entityManager.merge(card);
    }

    @Override
    @Transactional
    public Card remove(Long id) {
        Card removeCard = findById(id);
        if (removeCard == null) {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }
        entityManager.remove(removeCard);
        return removeCard;
    }
}
