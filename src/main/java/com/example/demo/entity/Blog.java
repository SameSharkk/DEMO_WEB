package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "blog_content")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments;

    public Blog(String title, String content, User createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }
}