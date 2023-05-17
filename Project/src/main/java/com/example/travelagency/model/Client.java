package com.example.travelagency.model;

import jakarta.persistence.Entity;

import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement(name = "client")
public class Client extends Person{


}
