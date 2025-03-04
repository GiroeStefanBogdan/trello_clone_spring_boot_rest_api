//package com.example.demo.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//import java.util.UUID;
//
//
//@Setter
//@Getter
////@Entity
//public class Board {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
//    private UUID id;
//
//    @OneToMany(mappedBy = "boards", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ListColumn>  columns;
//
//    @ManyToMany(mappedBy = "boards")
//    private List<User>  users;
//
//}
