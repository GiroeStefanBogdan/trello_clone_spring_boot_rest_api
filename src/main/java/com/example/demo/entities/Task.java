//package com.example.demo.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import jakarta.persistence.Id;
//
//
//import java.util.UUID;
//
//@Setter
//@Getter
//@Entity
//public class Task {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(length = 36, columnDefinition = "binary(36)", updatable = false, nullable = false)
//    private UUID id;
//
//    private String title;
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "list_column_id", nullable = false)
//    private ListColumn column;
//
//}
