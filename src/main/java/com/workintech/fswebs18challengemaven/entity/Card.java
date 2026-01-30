package com.workintech.fswebs18challengemaven.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "card", schema = "public")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Color color;

    @PrePersist
    @PreUpdate
    private void normalizeRules() {

        if (type == Type.JOKER) {
            this.value = null;
            this.color = null;
            return;
        }


        if (type != null && value != null) {
            throw new IllegalStateException("Card cannot have both type and value");
        }


        if (type == null && value == null) {
            throw new IllegalStateException("Card must have either type or value");
        }


        if (color == null) {
            throw new IllegalStateException("Color is required for non-JOKER cards");
        }
    }
}
