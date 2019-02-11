package com.deltabook.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friendFromId")
    private User friendFromId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friendToId")
    private User friendToId;
    private boolean isAccepted;
    private String requestMessage;
}
