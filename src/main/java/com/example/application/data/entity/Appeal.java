package com.example.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "appeal")
public class Appeal {

    @Id
    @Column(name = "appeal_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appealId;

    @Column(name = "appealText", columnDefinition = "text")
    private String appealText;

    @Column(name = "category")
    private String category;

    @Column(name = "emotion")
    private boolean emotion;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "appeal_category",
//            joinColumns =
//                    { @JoinColumn(name = "appeal_id", referencedColumnName = "appeal_id") },
//            inverseJoinColumns =
//                    { @JoinColumn(name = "category_id", referencedColumnName = "category_id") })
//    private Category category;

    public Appeal() {
    }

    public Appeal(Long appealId, String appealText) {
        this.appealId = appealId;
        this.appealText = appealText;
    }

    public Appeal(String appealText) {
        this.appealText = appealText;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + appealId + ", title=" + appealText + "]";
    }
}
