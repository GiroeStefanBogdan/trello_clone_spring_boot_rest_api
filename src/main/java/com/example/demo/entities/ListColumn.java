//package com.example.demo.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.scheduling.config.Task;
//
//import java.util.List;
//import java.util.UUID;
//
//
//@Setter
//@Getter
////@Entity
//public class ListColumn {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
//    private UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board boards;
//
//    @OneToMany(mappedBy = "column",  cascade = CascadeType.ALL,  orphanRemoval = true)
//    private List<Task> tasks;
//}
